package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<Class<?>, Object> appComponentsByClass = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Object config = invokeConfigConstructor(configClass);
        Reflections reflections = new Reflections(configClass, new MethodAnnotationsScanner());
        reflections.getMethodsAnnotatedWith(AppComponent.class).stream().sorted((o1, o2) -> {
//            AppComponent appComponent1 = o1.getDeclaredAnnotation(AppComponent.class);
//            AppComponent appComponent2 = o2.getDeclaredAnnotation(AppComponent.class);
//            return appComponent1.order() - appComponent2.order();
            int parameterCount1 = o1.getParameterCount();
            int parameterCount2 = o2.getParameterCount();
            return parameterCount1 - parameterCount2;
        }).forEach(method -> {
            registerAppComponent(config, method);
        });
    }

    private Object invokeConfigConstructor(Class<?> configClass) {
        try {
            return configClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void registerAppComponent(Object config, Method method) {
        try {
            AppComponent appComponent = method.getDeclaredAnnotation(AppComponent.class);
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] args = new Object[method.getParameterCount()];
            for (int i = 0; i < parameterTypes.length; ++i) {
                args[i] = appComponentsByClass.get(parameterTypes[i]);
            }
            Object result = method.invoke(config, args);
            appComponentsByClass.put(method.getReturnType(), result);
            appComponentsByName.put(appComponent.name(), result);
            appComponents.add(result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return appComponents.stream()
                .filter(obj -> componentClass.isAssignableFrom(obj.getClass()))
                .findFirst()
                .map(obj -> (C)obj)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
