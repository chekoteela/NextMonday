package com.sharkit.nextmonday.configuration.validation.field_validation;

import static com.sharkit.nextmonday.configuration.validation.field_validation.RegisterMenuValidator.isNameValid;
import static com.sharkit.nextmonday.configuration.validation.field_validation.RegisterMenuValidator.isPasswordValid;
import static com.sharkit.nextmonday.configuration.validation.field_validation.RegisterMenuValidator.isValidEmail;

import org.junit.Assert;
import org.junit.Test;

public class RegisterMenuValidatorTest {

    @Test
    public void testIsValidEmail() {
        Assert.assertTrue(isValidEmail("google@gmail.com"));
        Assert.assertFalse(isValidEmail("googleTestEmail"));
    }

    @Test
    public void testIsNameValid() {
        Assert.assertTrue(isNameValid("someName"));
        Assert.assertFalse(isNameValid("some Name"));
        Assert.assertFalse(isNameValid("someName1"));
        Assert.assertFalse(isNameValid("someName%"));
        Assert.assertFalse(isNameValid(""));
        Assert.assertFalse(isNameValid("f"));
        Assert.assertFalse(isNameValid(" "));
        Assert.assertFalse(isNameValid("someVeryBigName"));
    }

    @Test
    public void testIsPasswordValid() {
        Assert.assertTrue(isPasswordValid("password"));
        Assert.assertFalse(isPasswordValid("passw"));
        Assert.assertFalse(isPasswordValid("кирилиця"));
    }
}