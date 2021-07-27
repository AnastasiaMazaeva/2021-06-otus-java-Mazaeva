package executors;

import exceptions.TestException;
import exceptions.TestExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            throw new TestException("Ошибка определения класса ", e);
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
            return new ExecutionResult(TestExecutionResult.PASSED, method.getName());
        } catch (TestExecutionException e) {
            return new ExecutionResult(TestExecutionResult.FAILED, method.getName());
        }
    }
}
