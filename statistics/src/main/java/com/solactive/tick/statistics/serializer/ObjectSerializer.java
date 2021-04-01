package com.solactive.tick.statistics.serializer;

public interface ObjectSerializer {

    String toString(Object object);

    <T> T toObject(Class<T> clazz, String message);

}
