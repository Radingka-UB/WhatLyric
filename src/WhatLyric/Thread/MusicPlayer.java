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
        
    }
}