package org.kata.service;

import org.kata.exception.MaskingException;

public interface MaskingService {
    <T> T maskPersonalDataGeneric(T obj);
}
