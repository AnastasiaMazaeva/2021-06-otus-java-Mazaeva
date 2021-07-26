package executors;

import annotations.After;
import annotations.Before;
import annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodContainer {
    private final List<Method> before = new ArrayList<>();
    private final List<Method> after = new ArrayList<>();
    private final List<Method> test = new ArrayList<>();

    public MethodContainer(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(After.class) && method.getParameterCount() == 0) {
                    after.add(method);
                }
                if (annotation.annotationType().equals(Before.class) && method.getParameterCount() == 0) {
                    before.add(method);
                }
                if (annotation.annotationType().equals(Test.class) && method.getParameterCount() == 0) {
                    test.add(method);
                }
            }
        }
    }

    public List<Method> getBefore() {
        return before;
    }

    public List<Method> getAfter() {
        return after;
    }

    public List<Method> getTest() {
        return test;
    }
}
