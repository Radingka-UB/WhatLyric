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
            if(state.getState()==PlayerState.State.PLAYING){
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    break;
                }

                state.tick();

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