package com.sharkit.nextmonday.configuration.validation.widget_validation;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TextValidationTest {

    private final TextValidation validEmail = new TextValidation("google@gmail.com");
    private final TextValidation someString = new TextValidation("someString");
    private final TextValidation emptyValue = new TextValidation("");
    private final TextValidation hasNumber = new TextValidation("123dgs");
    private final TextValidation hasSymbols = new TextValidation("klh.gfj&bk@");
    private final TextValidation hasSpace = new TextValidation("dfg ngjd");
    private final TextValidation hasCyrillic = new TextValidation("dfgафів");

    private final List<Boolean> hasFalse = Arrays.asList(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
    private final List<Boolean> allTrue = Arrays.asList(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(someString.notEmpty().build());
        Assert.assertFalse(emptyValue.notEmpty().build());
    }

    @Test
    public void testIsValidEmail() {
        Assert.assertTrue(validEmail.isValidEmail().build());
        Assert.assertFalse(someString.isValidEmail().build());
    }

    @Test
    public void testTooLongValue() {
        Assert.assertTrue(someString.tooLongValue(11).build());
        Assert.assertFalse(someString.tooLongValue(9).build());
    }

    @Test
    public void testTooSmallValue() {
        Assert.assertTrue(someString.tooSmallValue(9).build());
        Assert.assertFalse(someString.tooSmallValue(11).build());
    }

    @Test
    public void testHasNoSymbols() {
        Assert.assertTrue(someString.hasNoSymbols().build());
        Assert.assertFalse(hasSymbols.hasNoSymbols().build());
    }

    @Test
    public void testHasNoNumber() {
        Assert.assertTrue(someString.hasNoNumber().build());
        Assert.assertFalse(hasNumber.hasNoNumber().build());
    }

    @Test
    public void testHasNoSpace() {
        Assert.assertTrue(someString.hasNoSpace().build());
        Assert.assertFalse(hasSpace.hasNoSpace().build());
    }

    @Test
    public void testBuild() {
        Assert.assertFalse(allTrue.contains(Boolean.FALSE));
        Assert.assertTrue(hasFalse.contains(Boolean.FALSE));
    }

    @Test
    public void testHasNotCyrillic() {
        Assert.assertTrue(someString.hasNotCyrillic().build());
        Assert.assertFalse(hasCyrillic.hasNotCyrillic().build());
    }

}