package WhatLyric.Thread;

import WhatLyric.Resource.PlayerState;
<<<<<<< HEAD
import WhatLyric.Model.Music;
=======
>>>>>>> 40ac271c21b5ad2fd26e7b9db4ba80cad8c330b9

public class MusicPlayer extends Thread {
    private final PlayerState state;

    public MusicPlayer(PlayerState state) {
        this.state = state;
    }

    @Override
    public void run() {
        System.out.println("MusicPlayer started.");
        while (!isInterrupted()) {
            try {
                if (state.getState() == PlayerState.State.PLAYING) {
                    Thread.sleep(1000);
                    state.tick();
                    
                    Music current = state.getCurrentMusic();
                    if (current != null && state.getCurrentPositionSeconds() >= current.getDurationSeconds()) {
                        System.out.println("Track finished, moving to next");
                        state.nextTrack();
                    }
                } else {
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                System.out.println("MusicPlayer interrupted.");
                break;
            }
        }
    }
}