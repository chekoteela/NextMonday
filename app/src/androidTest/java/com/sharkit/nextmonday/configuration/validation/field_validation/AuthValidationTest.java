package com.sharkit.nextmonday.configuration.validation.field_validation;

import static com.sharkit.nextmonday.configuration.validation.field_validation.AuthValidation.isValidAuthField;

import org.junit.Assert;
import org.junit.Test;

public class AuthValidationTest {

    @Test
    public void testValidAuthField() {
        Assert.assertTrue(isValidAuthField("someText"));
        Assert.assertFalse(isValidAuthField(""));
    }
}