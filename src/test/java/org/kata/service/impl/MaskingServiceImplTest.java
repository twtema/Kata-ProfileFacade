package org.kata.service.impl;

import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kata.annotations.MaskedField;
import org.kata.exception.MaskingException;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MaskingServiceImplTest {

    @InjectMocks
    private MaskingServiceImpl maskingService;

    private TestClass testClass;
    private TestClassWithoutAnnotatedFields testClassWithoutAnnotatedFields;
    private TestClassWithMultipleFields testClassWithMultipleFields;
    private TestClassWithDifferentFieldTypes testClassWithDifferentFieldTypes;

    @Before
    public void setUp() {
        testClass = new TestClass();
        testClassWithoutAnnotatedFields = new TestClassWithoutAnnotatedFields();
        testClassWithMultipleFields = new TestClassWithMultipleFields();
        testClassWithDifferentFieldTypes = new TestClassWithDifferentFieldTypes();
    }

    @Test
    public void testShouldNotModifyObjectWithoutAnnotatedFields() {
        TestClassWithoutAnnotatedFields originalObject = new TestClassWithoutAnnotatedFields();
        TestClassWithoutAnnotatedFields returnedObject = maskingService.maskPersonalDataGeneric(originalObject);
        assertSame("The object should not be modified if it does not have any fields annotated with @MaskedField", originalObject, returnedObject);
    }

    @Test
    public void testShouldCorrectlyMaskMultipleAnnotatedFields() {
        testClassWithMultipleFields.setFieldOne("1234567890");
        testClassWithMultipleFields.setFieldTwo("0987654321");
        TestClassWithMultipleFields maskedClass = maskingService.maskPersonalDataGeneric(testClassWithMultipleFields);
        assertEquals("12****90", maskedClass.getFieldOne());
        assertEquals("09****21", maskedClass.getFieldTwo());
    }

    @Test
    public void testShouldMaskPersonalDataWhenDataIsNull() {
        testClass.setField(null);
        TestClass maskedClass = maskingService.maskPersonalDataGeneric(testClass);
        assertNull(maskedClass.getField());
    }

    @Test
    public void testShouldMaskPersonalDataWhenDataIsPresent() {
        testClass.setField("1234567890");
        TestClass maskedClass = maskingService.maskPersonalDataGeneric(testClass);
        assertEquals("12****90", maskedClass.getField());
    }

    @Test
    public void testShouldReturnNullWhenObjectIsNull() {
        assertNull(maskingService.maskPersonalDataGeneric(null));
    }

    @Test
    public void shouldThrowMaskingExceptionWhenFieldTypeIsNotString() {
        TestClassWithNonStringField testClassWithNonStringField = new TestClassWithNonStringField();
        testClassWithNonStringField.setField(123);
        assertThrows(MaskingException.class, () -> maskingService.maskPersonalDataGeneric(testClassWithNonStringField));
    }

    @Test
    public void testShouldHandleNullAnnotatedFieldCorrectly() {
        TestClass testClass = new TestClass();
        testClass.setField(null);
        TestClass maskedClass = maskingService.maskPersonalDataGeneric(testClass);
        assertNull("The annotated field should remain null after masking", maskedClass.getField());
    }

    @Test
    public void testShouldMaskPersonalDataWhenDataLengthIsOne() {
        testClass.setField("1");
        TestClass maskedClass = maskingService.maskPersonalDataGeneric(testClass);
        assertEquals("1", maskedClass.getField());
    }

    @Test
    public void testShouldMaskPersonalDataWhenDataLengthIsTwo() {
        testClass.setField("12");
        TestClass maskedClass = maskingService.maskPersonalDataGeneric(testClass);
        assertEquals("1****2", maskedClass.getField());
    }

    @Test
    public void testShouldMaskPersonalDataWhenDataLengthIsThree() {
        testClass.setField("123");
        TestClass maskedClass = maskingService.maskPersonalDataGeneric(testClass);
        assertEquals("1****3", maskedClass.getField());
    }

    @Test
    public void testShouldMaskPersonalDataWhenDataLengthIsFour() {
        testClass.setField("1234");
        TestClass maskedClass = maskingService.maskPersonalDataGeneric(testClass);
        assertEquals("12****34", maskedClass.getField());
    }

    @Test
    public void testShouldCorrectlyMaskStringFieldsAndThrowForOthers() {
        testClassWithDifferentFieldTypes.setStringField("1234567890");
        testClassWithDifferentFieldTypes.setIntegerField(123);
        assertThrows(
                MaskingException.class,
                () -> maskingService.maskPersonalDataGeneric(testClassWithDifferentFieldTypes)
        );
    }

    @Data
    private class TestClass {
        @MaskedField
        private String field;
    }

    @Data
    private class TestClassWithNonStringField {
        @MaskedField
        private Integer field;
    }

    @Data
    private class TestClassWithoutAnnotatedFields {
        private String field = "1234567890";
    }

    @Data
    private class TestClassWithMultipleFields {
        @MaskedField
        private String fieldOne;
        @MaskedField
        private String fieldTwo;
    }

    @Data
    private class TestClassWithDifferentFieldTypes {
        @MaskedField
        private String stringField;
        @MaskedField
        private Integer integerField;
    }
}