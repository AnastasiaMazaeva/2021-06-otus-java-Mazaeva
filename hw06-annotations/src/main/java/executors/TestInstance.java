package executors;

import exceptions.TestException;
import exceptions.TestExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

class TestInstance {
    List<Method> before;
    List<Method> after;
    Method currentMethod;
    Object instance;

    public TestInstance(List<Method> before, List<Method> after, Method currentMethod, Object instance) {
        this.before = before;
        this.after = after;
        this.currentMethod = currentMethod;
        this.instance = instance;
    }

    public void invoke() {
        for (Method method : before) {
            invokeWithRuntimeException(instance, method);
        }
        invokeTestMethod(instance, currentMethod);
        for (Method method : after) {
            invokeWithRuntimeException(instance, method);
        }
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
        }
    }
}
