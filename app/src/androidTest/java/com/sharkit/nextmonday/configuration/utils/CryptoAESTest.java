package com.sharkit.nextmonday.configuration.utils;

import junit.framework.TestCase;

public class CryptoAESTest extends TestCase {


    public void testEncrypt() {
        final CryptoAES aes = CryptoAES.getInstance();

        assertEquals("T1FPn88kh2JGL4AzoaEBQA==", aes.encrypt("123456"));
        assertEquals("vkpnOOJcIAvYr9guLrYAvA==", aes.encrypt("qwerty"));
    }
}