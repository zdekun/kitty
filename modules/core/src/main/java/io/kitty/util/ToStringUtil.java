package io.kitty.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ToStringUtil {

    /*
    1、Map对象处理
    2、List对象处理
    3、数组对象处理
    4、pojo对象处理，对象内部只处理简单类型
     */
    public static String toString(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        if (obj instanceof Map) {

        } else if (obj instanceof Collection) {

        } else if (clazz.isArray()) {

        }
        List<Field> fields = sortedInstanceFields(clazz);
        StringBuilder buff = new StringBuilder();
        for (Field f : fields) {
            String name = f.getName();
            String value = toString(obj, f);
            if (Objects.nonNull(value)) {
                buff.append(name).append("=").append(value);
            }
            buff.append(", ");
        }
        return buff.toString();
    }

    private static List<Field> sortedInstanceFields(Class<?> clazz) {
        return ClassUtil.getAllInstanceFields(clazz);
    }

    private static boolean isFrequentlyClass(Class<?> clazz) {
        if (clazz == Boolean.class || clazz == boolean.class) {
            return true;
        } else if (clazz == Character.class || clazz == char.class) {
            return true;
        } else if (clazz == Byte.class || clazz == byte.class) {
            return true;
        } else if (clazz == Short.class || clazz == short.class) {
            return true;
        } else if (clazz == Integer.class || clazz == int.class) {
            return true;
        } else if (clazz == Long.class || clazz == long.class) {
            return true;
        } else if (clazz == Float.class || clazz == float.class) {
            return true;
        } else if (clazz == Double.class || clazz == double.class) {
            return true;
        } else if (clazz == String.class) {
            return true;
        }
        return false;
    }

    private static String toString(Object obj, Field f) throws IllegalAccessException {
        if (!f.isAccessible()) {
            f.setAccessible(true);
        }
        Class<?> clazz = f.getType();
        if (clazz == Boolean.class || clazz == boolean.class) {
            return String.valueOf(f.get(obj));
        } else if (clazz == Character.class || clazz == char.class) {
            return String.valueOf(f.get(obj));
        } else if (clazz == Byte.class || clazz == byte.class) {
            return String.valueOf(f.get(obj));
        } else if (clazz == Short.class || clazz == short.class) {
            return String.valueOf(f.get(obj));
        } else if (clazz == Integer.class || clazz == int.class) {
            return String.valueOf(f.get(obj));
        } else if (clazz == Long.class || clazz == long.class) {
            return String.valueOf(f.get(obj));
        } else if (clazz == Float.class || clazz == float.class) {
            return String.valueOf(f.get(obj));
        } else if (clazz == Double.class || clazz == double.class) {
            return String.valueOf(f.get(obj));
        } else if (clazz == String.class) {
            return (String) f.get(obj);
        }
        if (clazz.isPrimitive()) {
            return String.valueOf(f.get(obj));
        } else if (clazz == String.class) {
            return (String) f.get(obj);
        } else if (clazz == Integer.class) {
            return String.valueOf(f.get(obj));
        }
        return null;
    }
}
