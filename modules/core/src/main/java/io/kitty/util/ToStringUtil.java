package io.kitty.util;

import io.kitty.KittyException;
import io.kitty.annotation.Sensitizer;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

public class ToStringUtil {
    private static final ConcurrentMap<Class<?>, List<FieldWrapper>> CLASS_FIELDS_CACHE =
            new ConcurrentReferenceHashMap<Class<?>, List<FieldWrapper>>(256);

    public static String toString(Object obj) {
        StringBuilder buffer = new StringBuilder();
        toString(obj, buffer);
        return buffer.toString();
    }

    private static void toString(Object obj, StringBuilder buffer) {
        if (Objects.isNull(obj)) {
            return;
        }
        Class<?> clazz = obj.getClass();
        if (obj instanceof Map) {
            Map data = (Map) obj;
            toStringMap(data, buffer);
        } else if (obj instanceof Collection) {
            Collection<?> data = (Collection) obj;
            toStringCollection(data, buffer);
        } else if (clazz.isArray()) {
            List data = array2List(obj);
            toStringCollection(data, buffer);
        } else if (isFrequentlyClass(clazz)) {
            buffer.append(String.valueOf(obj));
        } else {
            toStringDomain(obj, buffer);
        }
    }

    // Map的key必须实现Comparable接口
    private static void toStringMap(Map<?, ?> data, StringBuilder buffer) {
        List sortedKeys = sortedCollection(data.keySet());

        Iterator<?> i = sortedKeys.iterator();
        if (!i.hasNext()) {
            buffer.append("{}");
            return;
        }

        buffer.append('{');
        while (true) {
            Object key = i.next();
            Object value = data.get(key);
            if (Objects.isNull(value)) {
                continue;
            }
            buffer.append(key);
            buffer.append('=');
            toString(value, buffer);
            if (!i.hasNext()) {
                buffer.append('}');
                return;
            }
            buffer.append(',').append(' ');
        }
    }

    @SuppressWarnings("unchecked")
    private static List sortedCollection(Collection<?> data) {
        List sorted = new ArrayList();
        for (Object o : data) {
            if (Objects.nonNull(o)) {
                sorted.add(o);
            }
        }
        Collections.sort(sorted);
        return sorted;
    }

    // Collection中的对象必须实现Comparable接口
    @SuppressWarnings("unchecked")
    private static void toStringCollection(Collection<?> data, StringBuilder buffer) {
        List sortedData = sortedCollection(data);

        Iterator<Object> it = sortedData.iterator();
        if (!it.hasNext()) {
            buffer.append("[]");
            return;
        }

        buffer.append('[');
        while (true) {
            Object value = it.next();
            if (Objects.isNull(value)) {
                continue;
            }
            toString(value, buffer);
            if (!it.hasNext()) {
                buffer.append(']');
                return;
            }
            buffer.append(',').append(' ');
        }
    }

    @SuppressWarnings("unchecked")
    private static List array2List(Object array) {
        int length = Array.getLength(array);
        if (length == 0) {
            return Collections.emptyList();
        }
        List data = new ArrayList();
        for (int i = 0; i < length; i++) {
            data.add(Array.get(array, i));
        }
        return data;
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
        } else {
            return clazz == String.class;
        }
    }

    private static void toStringDomain(Object obj, StringBuilder buffer) {
        Class<?> clazz = obj.getClass();
        List<FieldWrapper> fields = sortedInstanceFields(clazz);
        for (FieldWrapper fieldWrapper : fields) {
            String name = fieldWrapper.getFieldName();
            Object value = fieldWrapper.getFieldValue(obj);
            if (Objects.nonNull(value)) {
                buffer.append(name).append("=");
                toString(value, buffer);
            }
            buffer.append(", ");
        }
    }

    private static List<FieldWrapper> sortedInstanceFields(Class<?> clazz) {
        if (CLASS_FIELDS_CACHE.containsKey(clazz)) {
            return CLASS_FIELDS_CACHE.get(clazz);
        }

        List<FieldWrapper> desensitizerFieldWrappers = processInstanceFields(clazz);

        Collections.sort(desensitizerFieldWrappers);

        CLASS_FIELDS_CACHE.putIfAbsent(clazz, desensitizerFieldWrappers);

        return CLASS_FIELDS_CACHE.get(clazz);
    }

    private static List<FieldWrapper> processInstanceFields(Class<?> clazz) {
        List<FieldWrapper> desensitizerFieldWrappers = new ArrayList<>();
        List<Field> allInstanceFields = ClassUtil.getAllInstanceFields(clazz);
        for (Field instanceField : allInstanceFields) {
            if (instanceField.isAnnotationPresent(Sensitizer.class)) {
                Sensitizer sensitizer = instanceField.getAnnotation(Sensitizer.class);
                if (sensitizer.value()) {
                    continue;
                }
            }
            FieldWrapper desensitizerFieldWrapper = new FieldWrapper(instanceField);
            desensitizerFieldWrappers.add(desensitizerFieldWrapper);
        }
        return desensitizerFieldWrappers;
    }

    private static class FieldWrapper implements Comparable<FieldWrapper> {
        private final Field field;

        public FieldWrapper(Field field) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            this.field = field;
        }

        public String getFieldName() {
            return field.getName();
        }

        public Object getFieldValue(Object obj) {
            try {
                return field.get(obj);
            } catch (IllegalAccessException e) {
                throw new ToStringException("field:" + field.getName() + " illegal access.", e);
            }
        }

        @Override
        public int compareTo(FieldWrapper other) {
            return getFieldName().compareTo(other.getFieldName());
        }
    }

    public static class ToStringException extends KittyException {

        public ToStringException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
