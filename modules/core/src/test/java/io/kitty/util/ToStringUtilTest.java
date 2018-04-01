package io.kitty.util;

import io.kitty.domain.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ToStringUtilTest {

    @Test
    public void testListToString() throws Exception {
        List<Integer> lists = new ArrayList<>();
        lists.add(10);
        lists.add(5);
        lists.add(null);
        lists.add(100);
        lists.add(50);

        System.out.println(lists);
        System.out.println(ToStringUtil.toString(lists));
    }

    @Test
    public void testSetToString() throws Exception {
        Set<Integer> sets = new HashSet<>();
        sets.add(10);
        sets.add(5);
        sets.add(null);
        sets.add(100);
        sets.add(50);

        System.out.println(sets);
        System.out.println(ToStringUtil.toString(sets));
    }

    @Test
    public void testArrayToString() throws Exception {
        int[] data = new int[4];
        data[0] = 10;
        data[1] = 5;
        data[2] = 100;
        data[3] = 50;

        System.out.println(Arrays.toString(data));
        System.out.println(ToStringUtil.toString(data));
    }

    @Test
    public void testMapToString() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "zhangdekun");
        data.put(null, "zhangdekun");
        data.put("age", Integer.valueOf(35));
        System.out.println(data);
        System.out.println(ToStringUtil.toString(data));
    }

    @Test
    public void testToStringPerson() throws Exception {
        Person person = new Person();
        person.setId(15L);
        person.setName("zhangdekun");
        person.setAge(35);
        person.setInte(21);

        System.out.println(ToStringUtil.toString(person));
    }
}
