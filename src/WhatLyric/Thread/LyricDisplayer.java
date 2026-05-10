package WhatLyric.Thread;

import WhatLyric.Resource.PlayerState;
import WhatLyric.Model.Music;

public class LyricDisplayer extends Thread{
    private PlayerState state;

    public LyricDisplayer(PlayerState state){
        this.state=state;
    }

    public void run(){

    }
}
