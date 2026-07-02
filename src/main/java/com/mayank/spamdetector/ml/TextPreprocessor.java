package com.mayank.spamdetector.ml;

public class TextPreprocessor {

    public static String cleanText(String text) {

        if (text == null || text.isEmpty()) {
            return "";
        }

        // Convert to lowercase
        text = text.toLowerCase();

        // Remove URLs
        text = text.replaceAll("https?://\\S+\\s?", " ");

        // Remove email addresses
        text = text.replaceAll("\\S+@\\S+", " ");

        // Remove mobile numbers
        text = text.replaceAll("\\d{10}", " ");

        // Remove punctuation & special characters
        text = text.replaceAll("[^a-zA-Z0-9 ]", " ");

        // Remove extra spaces
        text = text.replaceAll("\\s+", " ").trim();

        return text;
    }

}