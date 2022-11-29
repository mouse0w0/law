package com.github.mouse0w0.law.util;

import java.util.HashSet;
import java.util.Set;

public class EnumUtils {
    public static <E extends Enum<E>> E oneOf(Class<E> enumType, String... names) {
        for (String name : names) {
            try {
                return Enum.valueOf(enumType, name);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return null;
    }

    public static <E extends Enum<E>> Set<E> allOf(Class<E> enumType, String... names) {
        Set<E> result = new HashSet<>();
        for (String name : names) {
            try {
                result.add(Enum.valueOf(enumType, name));
            } catch (IllegalArgumentException ignored) {
            }
        }
        return result;
    }
}
