package org.kata.service.impl;

import org.kata.service.MaskingService;

import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaskingServiceImpl implements MaskingService {
    private static final Logger logger = LoggerFactory.getLogger(MaskingServiceImpl.class);
    @Override
    public <T> T maskPersonalDataGeneric(T obj) {
        if (obj == null) {
            return null;
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                logger.error("Error accessing field: " + field.getName(), e);
                continue;
            }

            if (value instanceof String && ((String) value).length() > 4) {
                try {
                    String firstTwoChars = ((String) value).substring(0, 2);
                    String lastTwoChars = ((String) value).substring(((String) value).length() - 2);
                    String maskedValue = firstTwoChars + "****" + lastTwoChars;
                    field.set(obj, maskedValue);
                } catch (IllegalAccessException e) {
                    logger.error("Error accessing field: " + field.getName(), e);
                }
            }
        }

        return obj;
    }
}
