package WhatLyric.Thread;

import WhatLyric.Resource.PlayerState;
import WhatLyric.Model.Music;
import java.util.List;

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
                    String lyric=getLyricAtTime(currMusic, currTime);
                    if(lyric!=null){
                        System.out.println("\r=> "+lyric);
                    }
                }
            }
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                break;
            }
        }
    }
    private String getLyricAtTime(Music music, int currSecs){
        List<String> lyrics=music.getLyrics();
        for(String line:lyrics){
            String timeFormat=line.substring(1,line.indexOf("]"));
            String[] timeParts=timeFormat.split(":");
            int secondTotal=Integer.parseInt(timeParts[0])*60+Integer.parseInt(timeParts[1]);

            if(secondTotal==currSecs){
                return line.substring(line.indexOf("] ")+2);
            }
        }
        return null;
    }
}
