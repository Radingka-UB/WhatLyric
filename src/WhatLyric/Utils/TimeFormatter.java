package WhatLyric.Utils;

public class TimeFormatter {
    public static String formatDuration(int totalSeconds) {
        if (totalSeconds < 0) {
            throw new IllegalArgumentException("Duration seconds must not be negative");
        }
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static int parseDuration(String formatted) {
        if (formatted == null) {
            throw new IllegalArgumentException("Formatted duration cannot be null");
        }
        String[] parts = formatted.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Duration must have format mm:ss");
        }
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        if (minutes < 0 || seconds < 0 || seconds >= 60) {
            throw new IllegalArgumentException("Duration values out of range");
        }
        return minutes * 60 + seconds;
    }
}
