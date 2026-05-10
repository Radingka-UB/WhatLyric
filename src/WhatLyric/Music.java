package WhatLyric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Music {
    private final String title;
    private final String artist;
    private final int durationSeconds;
    private final List<String> lyrics;

    public Music(String title, String artist, int durationSeconds) {
        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.artist = Objects.requireNonNull(artist, "artist cannot be null");
        if (durationSeconds < 0) {
            throw new IllegalArgumentException("duration cannot be negative");
        }
        this.durationSeconds = durationSeconds;
        this.lyrics = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public String getFormattedDuration() {
        return TimeFormatter.formatDuration(durationSeconds);
    }

    public void addLyricLine(String lyricLine) {
        if (lyricLine != null && !lyricLine.isBlank()) {
            lyrics.add(lyricLine);
        }
    }

    public List<String> getLyrics() {
        return Collections.unmodifiableList(lyrics);
    }

    public String getLyricsPreview() {
        if (lyrics.isEmpty()) {
            return "[No lyrics available]";
        }
        return String.join("\n", lyrics);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", title, artist, getFormattedDuration());
    }
}
