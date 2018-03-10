package io.kitty.client;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T> {

    private final Type rawType;

    protected TypeReference() {
        rawType = getSuperclassTypeParameter(getClass());
    }

    private Type getSuperclassTypeParameter(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            throw new IllegalArgumentException("'" + getClass() + "' misses the type parameter. ");
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] rawTypes = parameterizedType.getActualTypeArguments();
        if (rawTypes.length != 1) {
            throw new IllegalArgumentException("number of type arguments must be 1");
        }
        return rawTypes[0];
    }

    public final Type getRawType() {
        return rawType;
    }

    @Override
    public String toString() {
        return rawType.toString();
    }

}
