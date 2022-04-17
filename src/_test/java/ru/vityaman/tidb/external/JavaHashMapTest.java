package ru.vityaman.tidb.external;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

public final class JavaHashMapTest {
    @Test
    public void testCompute() {
        HashMap<Integer, Integer> integers = new HashMap<>();
        integers.put(1, 1);
        int res = integers.compute(1, (k, v) -> {
            return v + 1;
        });
        assertEquals(2, res);
        assertEquals(2, (int) integers.get(1));
    }
}
