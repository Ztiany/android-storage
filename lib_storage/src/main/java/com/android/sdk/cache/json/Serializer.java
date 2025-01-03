package com.android.sdk.cache.json;

import java.lang.reflect.Type;

/**
 * @author Ztiany
 */
public interface Serializer {

    String toJson(Object entity);

    <T> T fromJson(String json, Type clazz);

}
