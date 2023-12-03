package org.kata.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.kata.annotations.MaskedField;
import org.kata.exception.MaskingException;
import org.kata.service.MaskingService;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;

@Service
@Slf4j
public class MaskingServiceImpl implements MaskingService {
    @Override
    public <T> T maskPersonalDataGeneric(T obj) {
        if (obj == null) {
            return null;
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(MaskedField.class) && field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    String originalValue = (String) field.get(obj);
                    if (originalValue != null) {
                        field.set(obj, maskString(originalValue));
                    } else {
                        log.warn("Field '{}' is null and cannot be masked.", field.getName());
                    }
                    } catch (IllegalAccessException e) {
                    log.error("Error accessing field: " + field.getName(), e);
                    throw new MaskingException("Failed to mask data");
                }
            }
        }
        return obj;
    }
    private String maskString(String originalValue) {
        if (originalValue == null || originalValue.isEmpty()) {
            return "Received empty string";
        }
        int length = originalValue.length();
        if (length > 4) {
            return originalValue.substring(0, 2) + "****" + originalValue.substring(length - 2);
        } else if (length == 3 || length == 2) {
            return originalValue.charAt(0) + "****" + originalValue.charAt(1);
        } else {
            return originalValue;
        }
    }
}
