package WhatLyric.Thread;

import WhatLyric.Resource.PlayerState;
import WhatLyric.Model.Music;
import WhatLyric.Utils.TimeFormatter;

public class MusicPlayer extends Thread{
    private PlayerState state;
    public MusicPlayer(PlayerState state){
        this.state=state;
    }
    public void run(){
        while(state.isRunning()){
            if(state.isPlaying()){
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    break;
                }

                int newTime=state.getCurrentTime()+1;
                state.setCurrentTime(newTime);

                Music currMusic=state.getCurrentTime();
                if(currMusic != null && newTime >= currMusic.getDuration()){
                    state.getPlaylist().next();
                    state.setCurrentTime(0);
                    state.setCurrentMusic(state.getPlaylist().getCurrent());
                }
            }else{
                try{
                    Thread.sleep(200);
                }catch(InterruptedException e){
                    break;
                }
            }
        }
    }
}