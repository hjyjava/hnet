package com.yuan.hnet.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * Created by  on 2016/12/14.
 */

public class ClassUtils {

    public static Class getClass(Type type) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            return getGenericClass((ParameterizedType) type);
        }  else {// class本身也是type，强制转型
            return (Class) type;
        }
    }

    public static Class getGenericClass(ParameterizedType parameterizedType) {
        Object genericClass = parameterizedType.getActualTypeArguments()[0];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class) ((ParameterizedType) genericClass).getRawType();
        }  else {
            return (Class) genericClass;
        }
    }

    public static Class getActualClass(Type type){
        Object genericClass = ((ParameterizedType)type).getActualTypeArguments()[0];
        return (Class) ((ParameterizedType) genericClass).getActualTypeArguments()[0];
    }
}
