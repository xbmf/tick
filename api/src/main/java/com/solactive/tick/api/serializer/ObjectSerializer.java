package com.solactive.tick.api.serializer;

public interface ObjectSerializer {

    String toString(Object object);

    <T> T toObject(Class<T> clazz, String message);

}
