package com.foodchain.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class ReflectUtil {

    private ReflectUtil() {}

    private static final Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    public static Object getField(String clazzName, String fieldName) {
        Class<?> clazz = getClass(clazzName);
        if (null == clazz) {
            return null;
        }
        try {
            Field filed = clazz.getDeclaredField(fieldName);
            filed.setAccessible(true);
            return filed.get(clazz);
        } catch (Exception e) {
            logger.error("获取字段信息异常，类名：" + clazzName + "，字段名：" + fieldName, e);
        }
        return null;
    }

    /**
     * 根据类名称获取Class对象
     * @param clazzName -类完全名称
     * @return
     */
    public static Class<?> getClass(String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (Exception e) {
            logger.error("getClass method error, return null. " + e.getMessage(), e);
            return null;
        }
    }

}
