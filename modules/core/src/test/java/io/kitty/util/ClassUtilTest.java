package io.kitty.util;

import io.kitty.domain.Department;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

public class ClassUtilTest {
    @Test
    public void testGetAllDeclaredFields() {
        Class<Department> clazz = Department.class;
        List<Field> fields = ClassUtil.getAllInstanceFields(clazz);
        for (Field f : fields) {
            String name = f.getName();
            Class<?> type = f.getType();
            boolean accessible = f.isAccessible();
            System.out.println(f);
        }
    }
}
