package WhatLyric.Thread;

import WhatLyric.Resource.PlayerState;
import WhatLyric.Model.Music;;

public class NotificationDisplayer extends Thread{
    private PlayerState state;

    public NotificationDisplayer(PlayerState state){
        this.state=state;
    }

    public void run(){
        while(state.isRunning()){
            try{
                Thread.sleep(60*1000);
            }catch(InterruptedException e){
                break;
            }

            Music currMusic =state.getCurrentMusic();
            String title=(currMusic!=null)?currMusic.getTitle():"No Music!";
            String status;
            switch(state.getState()){
                case PLAYING:
                    status="/> Playing";
                    break;
                case PAUSED:
                    status="|| Paused";
                    break;
                case STOPPED:
                    status="[] Stopped";
                    break;
                default:
                    status="<? Unknown";
            }
            String timeFormatted=state.getCurrentPositionFormatted();
            System.out.println("\r\n|/| Notification");
            System.out.println("Lagu: "+title);
            System.out.println("Status: "+status);
            System.out.println("Waktu: "+timeFormatted);
            System.out.println("|/|____________");
            System.out.print("Cmd> ");
        }
    }
}
