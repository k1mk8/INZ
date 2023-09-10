package com.example.application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512 {
    public static String hash(String input) {
        System.out.println("==== Hash init ====");
        String hash = null;   
        try {
            // Create a SHA-512 MessageDigest instance
            MessageDigest sha512Digest = MessageDigest.getInstance("SHA-512");

            // Get the byte array of the input string
            byte[] inputBytes = input.getBytes();

            // Calculate the SHA-512 hash
            byte[] hashBytes = sha512Digest.digest(inputBytes);

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            hash = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        System.out.println("==== Hash done ====");
        return hash;
    }
}
