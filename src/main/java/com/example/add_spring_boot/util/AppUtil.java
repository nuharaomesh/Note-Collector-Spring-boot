package com.example.add_spring_boot.util;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {
    public static String generateNoteID() {
        return "NOTE-" + UUID.randomUUID();
    }
    public static String generateUserID() {
        return "USER-" + UUID.randomUUID();
    }
    public static String profilePicToBase64(byte[] profilePic) {
        return Base64.getEncoder().encodeToString(profilePic);
    }
}
