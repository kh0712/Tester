import org.reflections8.Reflections;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

;

public class TestEngine {
    private Reflections reflection;
    private String methodName;

    public TestEngine(String testPackagePath){
        reflection = new Reflections(testPackagePath);
    }

    public TestEngine(String testPackagePath, String methodName){
        reflection = new Reflections(testPackagePath);
        run(methodName);
    }


    public void run(){

        Set<Class<?>> classes = reflection.getTypesAnnotatedWith(Tester.class);

        ArrayList<Object> testObjects = new ArrayList<Object>();

        try {
            loadTestObjects(classes, testObjects);
            runTestMethods(testObjects);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(String methodName){

        Set<Class<?>> classes = reflection.getTypesAnnotatedWith(Tester.class);

        ArrayList<Object> testObjects = new ArrayList<Object>();

        try {
            loadTestObjects(classes, testObjects);
            runTestMethods(testObjects,methodName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadTestObjects(Set<Class<?>> classes, ArrayList<Object> testObjects) throws Exception {
        if(classes.size() == 0){
            throw new IllegalStateException("Number of found test classes is ZERO");
        }
        for(Class<?> c : classes){
            testObjects.add(c.getDeclaredConstructor().newInstance());
        }
    }

    private static void runTestMethods(ArrayList<Object> testObjects) throws Exception {
        for(Object testObject : testObjects){
            Method[] declaredMethods = testObject.getClass().getDeclaredMethods();
            int i = 1;
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(Tester.class)) {
                    formatMetaData(testObject, i, method);
                    method.invoke(testObject);
                    i++;
                }
            }
        }
    }

    private static void runTestMethods(ArrayList<Object> testObjects, String methodName) throws Exception {
        for(Object testObject : testObjects){
            Method[] declaredMethods = testObject.getClass().getDeclaredMethods();
            int i = 1;
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(Tester.class) && method.getName().equals(methodName)) {
                    formatMetaData(testObject, i, method);
                    method.invoke(testObject);
                    i++;
                }
            }
        }
    }

    private static void formatMetaData(Object testObject, int i, Method method) {
        System.out.println("======Test"+ i +"======");
        System.out.println("Class: " + testObject.getClass().getName());
        System.out.println("Method: " + method.getName());
        System.out.println("=================");
    }
}
