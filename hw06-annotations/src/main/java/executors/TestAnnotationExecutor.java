package executors;

import exceptions.TestException;
import exceptions.TestExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class TestAnnotationExecutor {
    private final static Logger log = LoggerFactory.getLogger(TestAnnotationExecutor.class);

    public static void execute(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            MethodContainer container = new MethodContainer(clazz);

            List<ExecutionResult> list =  execute(container, clazz);

            log.info("{} tests:\n Succeed: {} \n Failed: {} \n {}", list.size(),
                    list.stream().filter(ExecutionResult::isPassed).count(),
                    list.stream().filter(e -> !e.isPassed()).count(),
                    list.toString());
        } catch (ClassNotFoundException e) {
            throw new TestException("Ошибка исполнения теста: ", e);
        }
    }

    private static List<ExecutionResult> execute(MethodContainer container, Class<?> clazz) {
        return container.getTest().stream()
                .map(method -> invokeTestMethod(container, clazz, method))
                .collect(Collectors.toList());
    }

    private static ExecutionResult invokeTestMethod(MethodContainer container, Class<?> clazz, Method method) {
        try {
            TestInvoker invoker = new TestInvoker(container.getBefore(), container.getAfter(), method, clazz);
            invoker.invoke();
            return new ExecutionResult(ExecutionResult.Result.PASSED, method.getName());
        } catch (TestExecutionException e) {
            return new ExecutionResult(ExecutionResult.Result.FAILED, method.getName());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new TestException("Ошибка исполнения теста: ", e);
        }
    }

    private static class ExecutionResult {

        private final Result result;
        private final String methodName;

        public ExecutionResult(Result result, String methodName) {
            this.result = result;
            this.methodName = methodName;
        }

        public boolean isPassed() {
            return this.result.equals(Result.PASSED);
        }

        @Override
        public String toString() {
            return this.methodName + " : " + this.result.name();
        }

        enum Result {
            FAILED,
            PASSED
        }
    }

}
