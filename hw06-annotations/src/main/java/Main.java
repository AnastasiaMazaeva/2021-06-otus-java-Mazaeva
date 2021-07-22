import executors.TestAnnotationExecutor;

public class Main {
    public static void main(String[] args) {
        TestAnnotationExecutor executor = new TestAnnotationExecutor();
        executor.execute("testing.TestClass");
    }
}
