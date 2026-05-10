package WhatLyric.Resource;

import java.util.Objects;

import WhatLyric.Model.Music;
import WhatLyric.Model.Playlist;
import WhatLyric.Utils.TimeFormatter;

public class PlayerState {
    public enum State {
        STOPPED,
        PLAYING,
        PAUSED
    }

    private State state;
    private Music currentMusic;
    private int currentPositionSeconds;
    private Playlist playlist;
    private volatile boolean running=true;

    public synchronized boolean isRunning(){
        return running;
    }

    public synchronized void selectTrack(int index){
        if(playlist ==null||playlist.getTrackCount()==0){
            throw new IllegalStateException("No Playlist Loaded!");
        }
        if(index<0 || index >= playlist.getTrackCount()){
            throw new IllegalArgumentException("Track Index Out of Range: "+index);
        }
        this.currentMusic=playlist.getTracks().get(index);
        this.currentPositionSeconds=0;
        this.state=State.STOPPED;
    }

    public synchronized void shutdown(){
        this.running=false;
    }

    public PlayerState() {
        this.state = State.STOPPED;
        this.currentPositionSeconds = 0;
    }

    public synchronized State getState() {
        return state;
    }

    public synchronized Music getCurrentMusic() {
        return currentMusic;
    }

    public synchronized int getCurrentPositionSeconds() {
        return currentPositionSeconds;
    }

    public synchronized String getCurrentPositionFormatted() {
        return TimeFormatter.formatDuration(currentPositionSeconds);
    }

    public synchronized Playlist getPlaylist() {
        return playlist;
    }

    public synchronized void loadPlaylist(Playlist playlist) {
        this.playlist = Objects.requireNonNull(playlist, "playlist cannot be null");
        if (playlist.getTrackCount() > 0) {
            this.currentMusic = playlist.getCurrentTrack();
            this.currentPositionSeconds = 0;
        } else {
            this.currentMusic = null;
            this.currentPositionSeconds = 0;
        }
        this.state = State.STOPPED;
    }

    public synchronized void play() {
        if (currentMusic == null) {
            if (playlist != null && playlist.getTrackCount() > 0) {
                currentMusic = playlist.getCurrentTrack();
            } else {
                throw new IllegalStateException("No musik loaded to play");
            }
        }
        this.state = State.PLAYING;
    }

    public synchronized void pause() {
        if (state == State.PLAYING) {
            state = State.PAUSED;
        }
    }

    public synchronized void stop() {
        state = State.STOPPED;
        currentPositionSeconds = 0;
    }

    public synchronized void seek(int seconds) {
        if (currentMusic == null) {
            throw new IllegalStateException("No musik loaded to seek");
        }
        if (seconds < 0 || seconds > currentMusic.getDurationSeconds()) {
            throw new IllegalArgumentException("Seek position out of range");
        }
        this.currentPositionSeconds = seconds;
    }

    public synchronized void nextTrack() {
        if (playlist == null || playlist.getTrackCount() == 0) {
            throw new IllegalStateException("No playlist loaded");
        }
        currentMusic = playlist.nextTrack();
        currentPositionSeconds = 0;
        state = State.STOPPED;
    }

    public synchronized void previousTrack() {
        if (playlist == null || playlist.getTrackCount() == 0) {
            throw new IllegalStateException("No playlist loaded");
        }
        currentMusic = playlist.previousTrack();
        currentPositionSeconds = 0;
        state = State.STOPPED;
    }

    public synchronized void tick() {
        if (state != State.PLAYING || currentMusic == null) {
            return;
        }
        if (currentPositionSeconds < currentMusic.getDurationSeconds()) {
            currentPositionSeconds++;
        }
        if (currentPositionSeconds >= currentMusic.getDurationSeconds()) {
            if (playlist != null && playlist.getTrackCount() > 0) {
                currentMusic = playlist.nextTrack();
                currentPositionSeconds = 0;
            } else {
                state = State.STOPPED;
                currentPositionSeconds = 0;
            }
        }
    }

    public synchronized String getStatusSummary() {
        String current = currentMusic == null ? "No track" : currentMusic.toString();
        return String.format("[%s] %s @ %s", state, current, getCurrentPositionFormatted());
    }

    @Override
    public synchronized String toString() {
        String current = currentMusic == null ? "No track" : currentMusic.toString();
        return String.format("State=%s, Track=%s, Position=%s", state, current, getCurrentPositionFormatted());
    }
}
