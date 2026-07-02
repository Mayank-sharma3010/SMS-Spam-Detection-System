package com.mayank.spamdetector.util;

public class TextPreprocessor {

    private TextPreprocessor() {
        // Prevent object creation
    }

    public static String preprocess(String message) {

        if (message == null || message.isBlank()) {
            return "";
        }

        return message
                .toLowerCase()
                .replaceAll("http\\S+|www\\S+", " ")
                .replaceAll("[^a-zA-Z\\s]", " ")
                .replaceAll("\\d+", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

}