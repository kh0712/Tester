<h2> Simply Self-made Test Lib using reflections </h2>

<body>

    add @Tester to Test Classes and methods
    
    and in main method,

    public static void main(String[] args) {
        TestEngine te = new TestEngine("test package path");
        te.run("method name"); //when only one method run
        te.run(); //all methods run in which @tester annotation is present.
    }

</body>