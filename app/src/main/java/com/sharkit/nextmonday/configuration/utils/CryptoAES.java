package com.sharkit.nextmonday.configuration.utils;

import static java.nio.charset.StandardCharsets.UTF_8;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CryptoAES {

    private static final String KEY = "X+YEs=ozagLzLgo6&!n&";
    private static final String CIPHER_CONFIG_TRANSFORMER = "AES/ECB/PKCS5Padding";
    private static final String SHA_1 = "SHA-1";
    private static final String ALGORITHM = "AES";
    private static final String TAG = CryptoAES.class.getCanonicalName();
    private static final CryptoAES aes = new CryptoAES();

    public static CryptoAES getInstance() {
        return aes;
    }

    @SuppressLint("GetInstance")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(final String strToEncrypt) {
        try {
            final SecretKeySpec secretKey = this.getSecretKeySpec(this.encodeSecret());
            final Cipher cipher = Cipher.getInstance(CIPHER_CONFIG_TRANSFORMER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(UTF_8)));
        } catch (final Exception e) {
            Log.e(TAG, e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private SecretKeySpec getSecretKeySpec(final String myKey) {
        try {
            final MessageDigest sha = MessageDigest.getInstance(SHA_1);
            final byte[] key = Arrays.copyOf(sha.digest(myKey.getBytes(UTF_8)), 16);
            return new SecretKeySpec(key, ALGORITHM);
        } catch (final Exception e) {
            Log.e(TAG, e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String encodeSecret() {
        return Base64.getEncoder()
                .encodeToString(CryptoAES.KEY.getBytes());
    }
}
