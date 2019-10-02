package com.ditra.ditraschool.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;

public class Utils {

    static public <T> T merge(T local, T remote)  {
        try {
            Class<?> myClass = local.getClass();
            Object mergedInstance = myClass.newInstance();

            for (Field field : myClass.getDeclaredFields()) {

                field.setAccessible(true);

                Object mergedValue;
                Object localValue = field.get(local);
                Object remoteValue = field.get(remote);

                Class<?> fieldClass = field.getType();
                String fieldClassName = fieldClass.getName();

                if(remoteValue != null && fieldClassName.startsWith("com.ditra"))
                    mergedValue = Utils.merge(localValue, remoteValue);
                else
                    mergedValue = remoteValue != null ? remoteValue : localValue;

                field.set(mergedInstance, mergedValue);
            }

            return (T) mergedInstance;

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return (T) null;
    }

    static public ResponseEntity<?> badRequestResponse (int code, String message) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel(HttpStatus.BAD_REQUEST.value(),code,message);
        return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }
}