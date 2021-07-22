package executors;

import annotations.After;
import annotations.Before;
import annotations.Test;
import exceptions.TestException;
import exceptions.TestExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestAnnotationExecutor {
    private final static Logger log = LoggerFactory.getLogger(TestAnnotationExecutor.class);

    public void execute(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Method[] methods = clazz.getDeclaredMethods();
            List<Method> before = new ArrayList<>();
            List<Method> after = new ArrayList<>();
            List<Method> test = new ArrayList<>();
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

            List<String> succeed = new ArrayList<>();
            List<String> failed = new ArrayList<>();

            for (Method method : test) {
                Object instance = clazz.getConstructor().newInstance();
                TestInstance inst = new TestInstance(before, after, method, instance);
                try {
                    inst.invoke();
                    succeed.add(method.getName());
                } catch (TestExecutionException e) {
                    failed.add(method.getName());
                }
            }

            log.info("Succeed {} : {}\n Failed {} : {}",
                    succeed.size(), String.join("; ", succeed),
                    failed.size(), String.join("; ", failed));
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new TestException("Ошибка исполнения теста: ", e);
        }
    }

}
