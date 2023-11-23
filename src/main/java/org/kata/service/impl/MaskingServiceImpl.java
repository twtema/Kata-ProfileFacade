package org.kata.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.kata.annotations.MaskedField;
import org.kata.exception.MaskingException;
import org.kata.service.MaskingService;

import java.lang.reflect.Field;

@Slf4j
public class MaskingServiceImpl implements MaskingService {
    @Override
    public <T> T maskPersonalDataGeneric(T obj) {
        if (obj == null) {
            return null;
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(MaskedField.class)) {
                try {
                    Object value = field.get(obj);
                    if (value instanceof String originalValue) {
                        String maskedValue = maskString(originalValue);
                        field.set(obj, maskedValue);
                    }
                } catch (IllegalAccessException e) {
                    log.error("Error accessing field: " + field.getName(), e);
                    try {
                        throw new MaskingException("Failed to mask data");
                    } catch (MaskingException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        return obj;
    }
    public String maskString(String orginalValue) {
        if (orginalValue.length() >= 4) {
            return maskLongString(orginalValue);
        } else if (orginalValue.length() == 3) {
            return maskShortString(orginalValue);
        }
        return orginalValue;
    }
    private String maskLongString(String originalValue) {
        String firstTwoChars = originalValue.substring(0, 2);
        String lastTwoChars = originalValue.substring(originalValue.length() - 2);
        return firstTwoChars + "****" + lastTwoChars;
    }

    private String maskShortString(String originalValue) {
        String firstChar = originalValue.substring(0, 1);
        String lastChar = originalValue.substring(originalValue.length() - 1);
        return firstChar + "**" + lastChar;
    }
}
