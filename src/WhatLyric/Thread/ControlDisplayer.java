package WhatLyric.Thread;

import WhatLyric.Resource.PlayerState;
import WhatLyric.Model.Playlist;
import WhatLyric.Utils.TimeFormatter;
import java.util.Scanner;

public class ControlDisplayer {
    private PlayerState state;
    private Playlist playlist;
    private Scanner scanner;
    public ControlDisplayer(PlayerState state,Playlist playlist){
        this.state=state;
        this.playlist=playlist;
        this.scanner=new Scanner(System.in);
    }

    public void run(){
        
    }
}
