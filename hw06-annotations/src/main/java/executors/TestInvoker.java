package executors;

import exceptions.InitializationException;
import exceptions.TestException;
import exceptions.TestExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

class TestInvoker {
    private static Logger log = LoggerFactory.getLogger(TestInvoker.class);
    List<Method> before;
    List<Method> after;
    Method currentMethod;
    Object instance;

    public TestInvoker(List<Method> before, List<Method> after, Method currentMethod, Class<?> clazz) {
        try {
            this.before = before;
            this.after = after;
            this.currentMethod = currentMethod;
            this.instance = clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new InitializationException("Ошибка инициализации конструктора ", e);
        }
    }

    public void invoke() {
        try {
            for (Method method : before) {
                invokeWithIgnoredException(instance, method);
            }
            currentMethod.invoke(instance);
        } catch (Throwable e) {
            throw new TestExecutionException("Ошибка исполнения теста: ", e);
        } finally {
            for (Method afterMethod : after) {
                invokeWithRuntimeException(instance, afterMethod);
            }
        }
    }

    private void invokeWithRuntimeException(Object instance, Method method) {
        try {
            method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new TestException("Ошибка вызова метода: ", e);
        }
    }

    private void invokeWithIgnoredException(Object instance, Method method) {
        try {
            method.invoke(instance);
        } catch (RuntimeException | IllegalAccessException | InvocationTargetException e) {
            log.info("Ошибка вызова метода before: ", e.getCause());
        }
    }

}
