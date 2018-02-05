package com.my.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;


public class EncodeUtil {
    
    private static byte rawKeyData[] = null;

    static {
        rawKeyData = HEXStringToByte("fffff7ceb3e82016");
    }

    /**
     * 传入字符串，返回一个加密串
     * 
     * @param s
     * @return
     */
    public static String encode(String s) {
        try {
            byte[] encryptedData = encrypt(s);
            s = byteToHEXString(encryptedData);
        } catch (Exception e) {
        }
        return s;
    }

    /**
     * 传入数字，返回一个加密串
     * 
     * @param s
     * @return
     */
    public static String encode(long goodsId) {
        try {
            byte[] encryptedData = encrypt("" + goodsId);
            return byteToHEXString(encryptedData);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 传入加密串，返回解密串
     * 
     * @param s
     * @return
     */
    public static String decode(String s) {
        try {
            return decrypt(HEXStringToByte(s));
        } catch (Exception e) {
        }
        return s;
    }
    
    
    public static void main(String[] args) {
		System.out.println(encode(55));
		System.out.println(decode(encode(55)));
	}

    /**
     * 传入字符串，返回一个加密串
     * 
     * @param s
     * @return
     */
    public static String encodeBase64(String s) throws UnsupportedEncodingException {
        byte[] binaryData = null;
        try {
            binaryData = s.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
        // byte[] newbt = Base64.encodeBase64(binaryData);

        return encodeBase64(binaryData);// new String(newbt);
    }

    public static String encodeBase64(byte[] binaryData) throws UnsupportedEncodingException {
        byte[] newbt = Base64.encodeBase64(binaryData);

        return new String(newbt,"UTF-8");
    }

    public static byte[] enBase64(byte[] binaryData) {
        return Base64.encodeBase64(binaryData);
    }

    public static byte[] deBase64(byte[] bytes) throws IOException {
        return Base64.decodeBase64(bytes);
    }

    public static String stringEncode(String str) {
        try {
            return java.net.URLEncoder.encode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 传入加密串，返回解密串
     * 
     * @param s
     * @return
     */
    public static String decodeBase64(String s) {
        try {
            return decodeBase64(s.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    public static String decodeBase64(byte[] bytes) {
        byte[] oldbt = null;
        String t = null;
        try {
            oldbt = Base64.decodeBase64(bytes);
            t = new String(oldbt, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return t;
    }

    public static String byteToHEXString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(100);
        for (int i = 0; i < bArray.length; i++) {
            String hex = Integer.toHexString(bArray[i] & 0xff);
            if (hex.length() == 1) {
                sb.append("0").append(hex);
            } else {
                sb.append(hex);
            }
        }
        return sb.toString().toLowerCase();
    }

    public static byte[] HEXStringToByte(String strString) {
        byte[] ret = new byte[strString.length() / 2];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Integer.decode("#" + strString.substring(2 * i, 2 * i + 2)).byteValue();
        }
        return ret;
    }

    /**
     * 加密方法
     * 
     * @param rawKeyData
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException
     */
    public static byte[] encrypt(String str) throws InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            UnsupportedEncodingException {
        // DES算法要求有一个可信任的随机数源
        // SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 现在，获取数据并加密
        byte data[] = str.getBytes("utf-8");
        // 正式执行加密操作
        byte[] encryptedData = cipher.doFinal(data);

        return encryptedData;
    }

    /**
     * 解密方法
     * 
     * @param rawKeyData
     * @param encryptedData
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException
     */
    public static String decrypt(byte[] encryptedData) throws IllegalBlockSizeException, BadPaddingException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException,
            UnsupportedEncodingException {
        // DES算法要求有一个可信任的随机数源
        // SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 正式执行解密操作
        byte decryptedData[] = cipher.doFinal(encryptedData);
        return new String(decryptedData, "utf-8");
    }

    public String getCurrentMillyTime() {
        return Long.valueOf(System.currentTimeMillis()).toString();
    }
}