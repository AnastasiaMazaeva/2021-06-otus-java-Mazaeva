package executors;

import exceptions.TestException;
import exceptions.TestExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

class TestInvoker {
    List<Method> before;
    List<Method> after;
    Method currentMethod;
    Object instance;

    public TestInvoker(List<Method> before, List<Method> after, Method currentMethod, Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.before = before;
        this.after = after;
        this.currentMethod = currentMethod;
        this.instance = clazz.getConstructor().newInstance();
    }

    public void invoke() {
        for (Method method : before) {
            invokeWithRuntimeException(instance, method);
        }
        invokeTestMethod(instance, currentMethod);
    }

    private void invokeWithRuntimeException(Object instance, Method method) {
        try {
            method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new TestException("Ошибка вызова метода: ", e);
        }
    }

    private void invokeTestMethod(Object instance, Method method) {
        try {
            method.invoke(instance);
        } catch (Throwable e) {
            throw new TestExecutionException("Ошибка исполнения теста: ", e);
        } finally {
            for (Method afterMethod : after) {
                invokeWithRuntimeException(instance, afterMethod);
            }
        }
    }
}
