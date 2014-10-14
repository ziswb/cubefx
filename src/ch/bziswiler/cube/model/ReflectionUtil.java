package ch.bziswiler.cube.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class ReflectionUtil {

    private ReflectionUtil() {
        // nop
    }

    public static boolean areTypesEqual(Type type1, Type type2) {
        if (type1 != null && type2 != null) {
            return type1.equals(type2);
        }
        return false;
    }

    public static Type getParametrizedType(Class<?> cls) {
        final Type[] interfaces = cls.getGenericInterfaces();
        if (interfaces.length == 0) {
            return null;
        }
        final Type type = interfaces[0];
        final Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (typeArguments.length == 0) {
            return null;
        }
        return typeArguments[0];
    }

}
