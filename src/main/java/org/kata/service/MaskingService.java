package org.kata.service;

public interface MaskingService {
    <T> T maskPersonalDataGeneric(T obj);
}
