package WhatLyric.Thread;

import WhatLyric.Resource.PlayerState;
import WhatLyric.Model.Playlist;
import WhatLyric.Model.Music;
import java.util.Scanner;

public class ControlDisplayer {
    private PlayerState state;
    private Playlist playlist;
    private Scanner scanner;
    public ControlDisplayer(PlayerState state,Playlist playlist){
        this.state=state;
        this.playlist=playlist;
        this.scanner=new Scanner(System.in);
        setNotation();
    }

    private void setNotation(){
        System.out.println("\n|/| Music Controller\n"+
        "Available Command:\n"+
        "<play> (Start/Continue Music)\n"+
        "<pause> (Pause Music)\n"+
        "<stop> (Stop Music)\n"+
        "<next> (Go to Next Music)\n"+
        "<prev> (Go to Prev Music)\n"+
        "<list> (Show Music List)\n"+
        "<select N> (Select Music Number N)\n"+
        "<status> (Display Status)\n"+
        "<quit> (Stop Program)\n"+
        "|/|_______________\n");
    }

    public void run(){
        while(state.isRunning()){
            System.out.print("Cmd> ");
            String input=scanner.nextLine().trim().toLowerCase();

            if(input.isEmpty())continue;

            switch (input) {
                case "Play":
                    try{
                        state.play();
                        System.out.println("Music Played/Continued!");
                    }catch(IllegalStateException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "pause":
                    state.pause();
                    System.out.println("Music Paused!");
                    break;
                case "stop":
                    state.stop();
                    System.out.println("Musik Stopped and Resetted!");
                    break;
                case "next":
                    try {
                        state.nextTrack();
                        Music nextMusic =state.getCurrentMusic();
                        System.out.println("Starting Next Musk: ");
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    break;
                case "prev":

                    break;
                case "list":

                    break;
                case "status":

                    break;
                case "quit":

                    break;
                default:
                    break;
            }
        }
    }

}
