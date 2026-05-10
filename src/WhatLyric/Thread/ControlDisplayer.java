package WhatLyric.Thread;

import WhatLyric.Resource.PlayerState;
import WhatLyric.Model.Playlist;
import WhatLyric.Model.Music;
import java.util.Scanner;

public class ControlDisplayer extends Thread{
    private PlayerState state;
    private Playlist playlist;
    private Scanner scanner;
    public ControlDisplayer(PlayerState state,Playlist playlist){
        this.state=state;
        this.playlist=playlist;
        this.scanner=new Scanner(System.in);
    }

    private void showMenu(){
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
        showMenu();
        while(state.isRunning()){
            System.out.print("Cmd> ");
            String input=scanner.nextLine().trim().toLowerCase();

            if(input.isEmpty())continue;

            switch (input) {
                case "play":
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
                        System.out.println("Starting Next Music: "+(nextMusic!=null?nextMusic.getTitle():"No Music"));
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "prev":
                    try{
                        state.previousTrack();
                        Music prevMusic=state.getCurrentMusic();
                        System.out.println("Starting Prev Music: "+(prevMusic!=null?prevMusic.getTitle():"No Music"));
                    }catch(IllegalStateException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "list":
                    System.out.println("\n|/| Playlist");
                    if(playlist!=null&&playlist.getTrackCount()>0){
                        for(int i=0;i<playlist.getTrackCount();i++){
                            Music m=playlist.getTracks().get(i);
                            String labelNow=(m==state.getCurrentMusic()?"|| Now Playing ||":"");
                            System.out.println((i+1)+". "+m.getTitle()+labelNow);
                        }
                    }else{
                        System.out.println("[Playlist Empty]");
                    }
                    System.out.println();
                    break;
                case "status":
                    System.out.println("|/| "+state.getStatusSummary());
                    break;
                case "quit":
                    state.shutdown();
                    System.out.println("Program Stopped!");
                    break;
                default:
                    if(input.startsWith("select ")){
                        try{
                            int num=Integer.parseInt(input.substring(7).trim());
                            if(playlist!=null&&num>=1&&num<=playlist.getTrackCount()){
                                Music selMusic=playlist.getTracks().get(num-1);
                                System.out.println("Selected Music: "+selMusic.getTitle());
                            }else{
                                System.out.println("Music Number Not Valid (1-"+ (playlist!=null?playlist.getTrackCount():"-1")+").");
                            }
                        }catch(NumberFormatException e){
                            System.out.println("Command Format Not Valid. Use \"select <number>\"");
                        }
                    }
                    break;
            }
        }
        scanner.close();
    }

}
