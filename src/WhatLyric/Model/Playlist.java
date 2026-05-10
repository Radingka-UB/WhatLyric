package WhatLyric.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import WhatLyric.Utils.TimeFormatter;

public class Playlist {
    private final String name;
    private final List<Music> tracks;
    private int currentTrackIndex = 0;

    public Playlist(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.tracks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addTrack(Music music) {
        tracks.add(Objects.requireNonNull(music, "music cannot be null"));
    }

    public boolean removeTrack(Music music) {
        return tracks.remove(music);
    }

    public int getTrackCount() {
        return tracks.size();
    }

    public List<Music> getTracks() {
        return Collections.unmodifiableList(tracks);
    }

    public Music getCurrentTrack() {
        if (tracks.isEmpty()) {
            return null;
        }
        return tracks.get(currentTrackIndex);
    }

    public Music nextTrack() {
        if (tracks.isEmpty()) {
            return null;
        }
        currentTrackIndex = (currentTrackIndex + 1) % tracks.size();
        return getCurrentTrack();
    }

    public Music previousTrack() {
        if (tracks.isEmpty()) {
            return null;
        }
        currentTrackIndex = (currentTrackIndex - 1 + tracks.size()) % tracks.size();
        return getCurrentTrack();
    }

    public Music selectTrack(int index) {
        if (index < 0 || index >= tracks.size()) {
            throw new IndexOutOfBoundsException("track index out of range");
        }
        currentTrackIndex = index;
        return getCurrentTrack();
    }

    public int getTotalDurationSeconds() {
        return tracks.stream().mapToInt(Music::getDurationSeconds).sum();
    }

    public String getFormattedTotalDuration() {
        return TimeFormatter.formatDuration(getTotalDurationSeconds());
    }

    @Override
    public String toString() {
        return String.format("Playlist '%s' (%d tracks, total %s)", name, getTrackCount(), getFormattedTotalDuration());
    }
}
