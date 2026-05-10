package WhatLyric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Playlist {
    private final String name;
    private final List<Musik> tracks;
    private int currentTrackIndex = 0;

    public Playlist(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.tracks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addTrack(Musik musik) {
        tracks.add(Objects.requireNonNull(musik, "musik cannot be null"));
    }

    public boolean removeTrack(Musik musik) {
        return tracks.remove(musik);
    }

    public int getTrackCount() {
        return tracks.size();
    }

    public List<Musik> getTracks() {
        return Collections.unmodifiableList(tracks);
    }

    public Musik getCurrentTrack() {
        if (tracks.isEmpty()) {
            return null;
        }
        return tracks.get(currentTrackIndex);
    }

    public Musik nextTrack() {
        if (tracks.isEmpty()) {
            return null;
        }
        currentTrackIndex = (currentTrackIndex + 1) % tracks.size();
        return getCurrentTrack();
    }

    public Musik previousTrack() {
        if (tracks.isEmpty()) {
            return null;
        }
        currentTrackIndex = (currentTrackIndex - 1 + tracks.size()) % tracks.size();
        return getCurrentTrack();
    }

    public Musik selectTrack(int index) {
        if (index < 0 || index >= tracks.size()) {
            throw new IndexOutOfBoundsException("track index out of range");
        }
        currentTrackIndex = index;
        return getCurrentTrack();
    }

    public int getTotalDurationSeconds() {
        return tracks.stream().mapToInt(Musik::getDurationSeconds).sum();
    }

    public String getFormattedTotalDuration() {
        return TimeFormatter.formatDuration(getTotalDurationSeconds());
    }

    @Override
    public String toString() {
        return String.format("Playlist '%s' (%d tracks, total %s)", name, getTrackCount(), getFormattedTotalDuration());
    }
}
