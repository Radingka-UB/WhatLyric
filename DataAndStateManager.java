import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ==========================================
// 1. TIME FORMATTER (Utility)
// ==========================================
class TimeFormatter {
    public static String formatTime(int seconds) {
        if (seconds < 0) seconds = 0;
        int h = seconds / 3600;
        int m = (seconds % 3600) / 60;
        int s = seconds % 60;
        return h > 0 ? String.format("%d:%02d:%02d", h, m, s) 
                     : String.format("%d:%02d", m, s);
    }

    public static int parseTime(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) return 0;
        String[] parts = timeStr.split(":");
        try {
            if (parts.length == 2) return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            if (parts.length == 3) return Integer.parseInt(parts[0]) * 3600 + Integer.parseInt(parts[1]) * 60 + Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) { return 0; }
        return 0;
    }

    public static int getProgressPercent(int current, int total) {
        return total > 0 ? (int) ((current * 100.0) / total) : 0;
    }

    public static String getRemainingTime(int current, int total) {
        return "-" + formatTime(Math.max(0, total - current));
    }
}

// ==========================================
// 2. MUSIC (Data Entity)
// ==========================================
class Music {
    private final String title;
    private final String artist;
    private final String album;
    private final int durationSec;
    private final String lyrics;
    private final String genre;
    private final int releaseYear;

    public Music(String title, String artist, String album, int durationSec, String lyrics, String genre, int releaseYear) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.durationSec = durationSec;
        this.lyrics = lyrics;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    // Getters
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public int getDurationSec() { return durationSec; }
    public String getLyrics() { return lyrics; }
    public String getGenre() { return genre; }
    public int getReleaseYear() { return releaseYear; }

    @Override
    public String toString() {
        return String.format("%s - %s [%s]", title, artist, TimeFormatter.formatTime(durationSec));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Music)) return false;
        Music other = (Music) obj;
        return title.equals(other.title) && artist.equals(other.artist);
    }
}

// ==========================================
// 3. PLAYLIST (Collection & Navigation State)
// ==========================================
class Playlist {
    private final String name;
    private final List<Music> songs;
    private int currentIndex;
    private boolean isShuffle;
    private List<Integer> shuffleOrder;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
        this.currentIndex = -1;
        this.isShuffle = false;
        this.shuffleOrder = new ArrayList<>();
    }

    public void addSong(Music song) {
        if (song != null && !songs.contains(song)) {
            songs.add(song);
            if (shuffleOrder.isEmpty()) shuffleOrder.add(songs.size() - 1);
            else shuffleOrder.add(shuffleOrder.size()); // placeholder, will regenerate on shuffle toggle
        }
    }

    public void removeSong(Music song) {
        if (songs.remove(song)) {
            if (currentIndex >= songs.size()) currentIndex = Math.max(0, songs.size() - 1);
            regenerateShuffleOrder();
        }
    }

    public Music getCurrentSong() {
        return (currentIndex >= 0 && currentIndex < songs.size()) ? songs.get(currentIndex) : null;
    }

    public Music getNextSong() {
        if (songs.isEmpty()) return null;
        if (isShuffle && !shuffleOrder.isEmpty()) {
            int currentPosInShuffle = shuffleOrder.indexOf(currentIndex);
            int nextPos = (currentPosInShuffle + 1) % shuffleOrder.size();
            currentIndex = shuffleOrder.get(nextPos);
        } else {
            currentIndex = (currentIndex + 1) % songs.size();
        }
        return getCurrentSong();
    }

    public Music getPreviousSong() {
        if (songs.isEmpty()) return null;
        if (isShuffle && !shuffleOrder.isEmpty()) {
            int currentPosInShuffle = shuffleOrder.indexOf(currentIndex);
            int prevPos = (currentPosInShuffle - 1 + shuffleOrder.size()) % shuffleOrder.size();
            currentIndex = shuffleOrder.get(prevPos);
        } else {
            currentIndex = (currentIndex - 1 + songs.size()) % songs.size();
        }
        return getCurrentSong();
    }

    public void setShuffle(boolean shuffle) {
        this.isShuffle = shuffle;
        if (shuffle) regenerateShuffleOrder();
    }

    private void regenerateShuffleOrder() {
        shuffleOrder.clear();
        for (int i = 0; i < songs.size(); i++) shuffleOrder.add(i);
        if (isShuffle) Collections.shuffle(shuffleOrder);
        // Ensure current song stays accessible
        if (!shuffleOrder.contains(currentIndex) && currentIndex >= 0) {
            shuffleOrder.add(0, currentIndex);
        }
    }

    // Getters & Info
    public String getName() { return name; }
    public List<Music> getSongs() { return new ArrayList<>(songs); }
    public int getCurrentIndex() { return currentIndex; }
    public boolean isShuffle() { return isShuffle; }
    public int size() { return songs.size(); }

    public void setCurrentIndex(int index) {
        if (index >= 0 && index < songs.size()) this.currentIndex = index;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("🎵 Playlist: ").append(name).append(" (").append(songs.size()).append(" tracks)\n");
        for (int i = 0; i < songs.size(); i++) {
            String marker = (i == currentIndex) ? "▶ " : "  ";
            sb.append(marker).append(i + 1).append(". ").append(songs.get(i)).append("\n");
        }
        return sb.toString();
    }
}

// ==========================================
// 4. PLAYER STATE (Playback & Control State)
// ==========================================
class PlayerState {
    public enum Status { STOPPED, PLAYING, PAUSED }
    public enum RepeatMode { NONE, ONE, ALL }

    private Status status;
    private RepeatMode repeatMode;
    private int volume;
    private int currentPositionSec;
    private boolean isMuted;
    private int previousVolume;

    public PlayerState() {
        this.status = Status.STOPPED;
        this.repeatMode = RepeatMode.NONE;
        this.volume = 70;
        this.currentPositionSec = 0;
        this.isMuted = false;
        this.previousVolume = 70;
    }

    // State Control
    public void play() { status = Status.PLAYING; }
    public void pause() { status = Status.PAUSED; }
    public void stop() { status = Status.STOPPED; currentPositionSec = 0; }

    // Volume Control
    public void setVolume(int vol) {
        volume = Math.max(0, Math.min(100, vol));
        isMuted = false;
    }

    public void toggleMute() {
        if (isMuted) {
            volume = previousVolume;
            isMuted = false;
        } else {
            previousVolume = volume;
            volume = 0;
            isMuted = true;
        }
    }

    // Position Control
    public void seek(int seconds) { currentPositionSec = Math.max(0, seconds); }
    public void incrementPosition(int seconds) { currentPositionSec += seconds; }

    // Repeat Control
    public void cycleRepeatMode() {
        switch (repeatMode) {
            case NONE -> repeatMode = RepeatMode.ONE;
            case ONE -> repeatMode = RepeatMode.ALL;
            case ALL -> repeatMode = RepeatMode.NONE;
        }
    }

    // Getters
    public Status getStatus() { return status; }
    public RepeatMode getRepeatMode() { return repeatMode; }
    public int getVolume() { return volume; }
    public int getCurrentPositionSec() { return currentPositionSec; }
    public boolean isMuted() { return isMuted; }
    public boolean isPlaying() { return status == Status.PLAYING; }

    @Override
    public String toString() {
        String icon = switch (status) {
            case PLAYING -> "▶";
            case PAUSED -> "";
            case STOPPED -> "";
        };
        String repeatIcon = switch (repeatMode) {
            case NONE -> "🔀";
            case ONE -> "";
            case ALL -> "🔁";
        };
        return String.format("[%s] %s / %s | Vol: %d%% %s", 
            icon, 
            TimeFormatter.formatTime(currentPositionSec), 
            repeatIcon,
            volume,
            isMuted ? "(MUTED)" : "");
    }
}

// ==========================================
// 5. MAIN (Demo & Test Data)
// ==========================================
public class DataAndStateManager {
    public static void main(String[] args) {
        System.out.println("=== MUSIC PLAYER: DATA & STATE MANAGEMENT DEMO ===\n");

        // 1. Membuat Data Lagu (Sesuai permintaan: banyak lagu & lirik)
        Music song1 = new Music("Bohemian Rhapsody", "Queen", "A Night at the Opera", 354, 
            "[Verse]\nIs this the real life? Is this just fantasy?\n[Chorus]\nMama, just killed a man...", "Rock", 1975);
        Music song2 = new Music("Imagine", "John Lennon", "Imagine", 183, 
            "[Verse]\nImagine there's no heaven\nIt's easy if you try...\n", "Pop", 1971);
        Music song3 = new Music("Hotel California", "Eagles", "Hotel California", 391, 
            "[Verse]\nOn a dark desert highway, cool wind in my hair...\n", "Classic Rock", 1976);
        Music song4 = new Music("Blinding Lights", "The Weeknd", "After Hours", 200, 
            "[Verse]\nI've been tryna call\nI've been on my own for long enough...\n", "Synth-pop", 2020);
        Music song5 = new Music("Lagu Indonesia", "Arsitektur", "Ruang Hampa", 245, 
            "[Verse]\nKu ingin engkau tahu\nDiriku selalu menantimu...\n", "Indie", 2019);

        // 2. Membuat Playlist & Mengisi Data
        Playlist myPlaylist = new Playlist("Favorites Mix");
        myPlaylist.addSong(song1);
        myPlaylist.addSong(song2);
        myPlaylist.addSong(song3);
        myPlaylist.addSong(song4);
        myPlaylist.addSong(song5);

        // 3. Inisialisasi Player State
        PlayerState player = new PlayerState();
        player.play();
        player.seek(45); // Simulasi sudah berjalan 45 detik

        // 4. Demontrasi Navigasi & State
        System.out.println(" " + myPlaylist);
        System.out.println("🎛️ Player State: " + player);
        System.out.println(" Now Playing: " + myPlaylist.getCurrentSong());
        System.out.println("⏱️ Progress: " + TimeFormatter.getProgressPercent(player.getCurrentPositionSec(), song1.getDurationSec()) + 
                           "% | Remaining: " + TimeFormatter.getRemainingTime(player.getCurrentPositionSec(), song1.getDurationSec()));
        
        System.out.println("\n--- Testing Controls ---");
        player.cycleRepeatMode(); // NONE -> ONE
        player.setVolume(85);
        System.out.println("🔁 Repeat: " + player.getRepeatMode() + " |  Volume: " + player.getVolume());
        
        player.toggleMute();
        System.out.println("🔇 Mute Toggled: " + (player.isMuted() ? "ON" : "OFF"));
        
        player.toggleMute(); // Unmute
        player.pause();
        System.out.println("⏸ State after pause: " + player.getStatus());
        
        System.out.println("\n--- Testing Shuffle & Navigation ---");
        myPlaylist.setShuffle(true);
        System.out.println(" Shuffle Activated");
        Music nextTrack = myPlaylist.getNextSong();
        System.out.println("⏭️ Next Track: " + nextTrack);
        
        Music prevTrack = myPlaylist.getPreviousSong();
        System.out.println("⏮️ Previous Track: " + prevTrack);
        
        System.out.println("\n Final Playlist State:");
        System.out.println(myPlaylist);
        System.out.println("✅ Data & State Management berhasil dijalankan.");
    }
}