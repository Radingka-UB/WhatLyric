package WhatLyric;

import WhatLyric.Thread.ControlDisplayer;
import WhatLyric.Thread.LyricDisplayer;
import WhatLyric.Thread.MusicPlayer;
import WhatLyric.Thread.NotificationDisplayer;

public class Main {
    public static void main(String[] args) {
        // Membuat musik dengan lirik
        Musik lagu1 = new Musik("Bohemian Rhapsody", "Queen", 355);
        lagu1.addLyricLine("[00:00] Is this the real life?");
        lagu1.addLyricLine("[00:15] Is this just fantasy?");
        lagu1.addLyricLine("[00:30] Caught in a landslide");
        lagu1.addLyricLine("[00:45] No escape from reality");
        // Tambahkan lebih banyak lirik sesuai durasi

        Musik lagu2 = new Musik("Stairway to Heaven", "Led Zeppelin", 482);
        lagu2.addLyricLine("[00:00] There's a lady who's sure");
        lagu2.addLyricLine("[00:20] All that glitters is gold");
        // Tambahkan lebih banyak

        Musik lagu3 = new Musik("Hotel California", "Eagles", 391);
        lagu3.addLyricLine("[00:00] On a dark desert highway");
        // Tambahkan lebih banyak

        // Buat playlist
        Playlist playlist = new Playlist("Classic Rock Hits");
        playlist.addTrack(lagu1);
        playlist.addTrack(lagu2);
        playlist.addTrack(lagu3);

        // Buat PlayerState dan load playlist
        PlayerState playerState = new PlayerState();
        playerState.loadPlaylist(playlist);

        // Buat thread-thread
        MusicPlayer musicPlayer = new MusicPlayer(playerState);
        LyricDisplayer lyricDisplayer = new LyricDisplayer(playerState);
        NotificationDisplayer notificationDisplayer = new NotificationDisplayer(playerState);
        ControlDisplayer controlDisplayer = new ControlDisplayer(playerState, musicPlayer, lyricDisplayer, notificationDisplayer);

        // Start semua thread
        musicPlayer.start();
        lyricDisplayer.start();
        notificationDisplayer.start();
        controlDisplayer.start();

        // Tunggu controlDisplayer selesai
        try {
            controlDisplayer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Program selesai.");
    }
}