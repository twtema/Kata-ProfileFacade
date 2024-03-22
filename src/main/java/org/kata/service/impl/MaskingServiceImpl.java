package org.kata.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.kata.annotations.MaskedField;
import org.kata.exception.MaskingException;
import org.kata.service.MaskingService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;

@Service
@Slf4j
public class MaskingServiceImpl implements MaskingService {

    static final String ERR_ONLY_STRING_FIELDS_SUPPORTED = "Masking is supported only for String fields. Field: %s";
    static final String LOG_NULL_FIELD = "Field '{}' is null and cannot be masked.";
    static final String LOG_ERROR_ACCESSING_FIELD = "Error accessing field: {}. IllegalAccessException: {}";
    static final String ERR_FAILED_TO_ACCESS_FIELD_FOR_MASKING = "Failed to access field for masking: %s";

    static final String MASK = "%s****%s";

    @Override
    public <T> T maskPersonalDataGeneric(T obj) {
        if (obj == null) {
            return null;
        }

        Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(MaskedField.class))
                .forEach(field -> processField(obj, field));

        return obj;
    }

    private <T> void processField(T obj, Field field) {
        if (!field.getType().equals(String.class)) {
            throw new MaskingException(String.format(ERR_ONLY_STRING_FIELDS_SUPPORTED, field.getName()));
        }

        field.setAccessible(true);
        try {
            String originalValue = (String) field.get(obj);
            if (originalValue != null) {
                field.set(obj, maskString(originalValue));
            } else {
                log.warn(LOG_NULL_FIELD, field.getName());
            }
        } catch (IllegalAccessException e) {
            log.error(LOG_ERROR_ACCESSING_FIELD, field.getName(), e);
            throw new MaskingException(String.format(ERR_FAILED_TO_ACCESS_FIELD_FOR_MASKING, field.getName()));
        }
    }

    private String maskString(String originalValue) {
        if (originalValue == null || originalValue.isEmpty()) return originalValue;
        return switch (originalValue.length()) {
            case 1 -> originalValue;
            case 2, 3 -> String.format(MASK, originalValue.charAt(0), originalValue.charAt(originalValue.length() - 1));
            default -> String.format(
                    MASK, originalValue.substring(0, 2), originalValue.substring(originalValue.length() - 2)
            );
        };
    }
}
