package WhatLyric.Thread;

import WhatLyric.Resource.PlayerState;
import WhatLyric.Model.Music;

public class LyricDisplayer extends Thread{
    private PlayerState state;

    public LyricDisplayer(PlayerState state){
        this.state=state;
    }

    public void run(){
        while(state.isRunning()){
            if(state.getState()==PlayerState.State.PLAYING){
                Music currMusic=state.getCurrentMusic();
                int currTime=state.getCurrentPositionSeconds();

                if(currMusic !=null){

                }
            }
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                break;
            }
        }
    }
}
