package WhatLyric;

import WhatLyric.Model.Musik;
import WhatLyric.Model.Playlist;
import WhatLyric.Resource.PlayerState;
import WhatLyric.Thread.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("=   WhatLyric - Music Player v1.0       =");
        System.out.println("=   Multi-threading Music & Lyrics      =");
        System.out.println("=========================================\n");

        Playlist playlist = createPlaylist();
        
        PlayerState state = new PlayerState();
        state.setPlaylist(playlist);
        state.setCurrentTrackIndex(0);

        MusicPlayer musicPlayer = new MusicPlayer(state);
        LyricDisplayer lyricDisplayer = new LyricDisplayer(state);
        NotificationDisplayer notificationDisplayer = new NotificationDisplayer(state);
        ControlDisplayer controlDisplayer = new ControlDisplayer(state);

        System.out.println("Starting all threads...\n");
        musicPlayer.start();
        lyricDisplayer.start();
        notificationDisplayer.start();
        controlDisplayer.start();

        try {
            controlDisplayer.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted");
        }

        System.out.println("\nShutting down...");
        musicPlayer.interrupt();
        lyricDisplayer.interrupt();
        notificationDisplayer.interrupt();
        
        System.out.println("WhatLyric stopped. Thank you!");
    }

    //Membuat playlist dengan 3 lagu

    private static Playlist createPlaylist() {
        List<Musik> songs = new ArrayList<>();

        // Lagu 1: Terima Kasih Pak Jokowi
        Musik lagu1 = new Musik(
            "Terima Kasih Pak Jokowi",
            "Kang Lidan",
            "240",
            createLirikTerimaKasih()
        );
        songs.add(lagu1);

        // Lagu 2: Kicau Mania
        Musik lagu2 = new Musik(
            "Kicau Mania",
            "Ndarboy Genk, Banditoz Yaow 86, BoyCord",
            "300",
            createLirikKicauMania()
        );
        songs.add(lagu2);

        // Lagu 3: NO BATIDAO
        Musik lagu3 = new Musik(
            "NO BATIDÃO",
            "ZXKAI, slxughter",
            "180",
            createLirikNoBatidao()
        );
        songs.add(lagu3);

        return new Playlist(songs);
    }

    //Lirik: Terima Kasih Pak Jokowi

    private static List<String> createLirikTerimaKasih() {
        List<String> lirik = new ArrayList<>();
        lirik.add("[0:00] Terima Kasih Pak Jokowi");
        lirik.add("[0:05] ");
        lirik.add("[0:05] [Verse 1]");
        lirik.add("[0:05] Jerit payah yang telah kau curahkan");
        lirik.add("[0:10] Tak pernah berhenti bekerja");
        lirik.add("[0:15] Dan tak pernah berkeluh kesah");
        lirik.add("[0:20] Untuk tanah air tercinta");
        lirik.add("[0:25] ");
        lirik.add("[0:25] [Verse 2]");
        lirik.add("[0:25] Sembilan belas tahun sudah");
        lirik.add("[0:30] Kau mengabdi untuk negara");
        lirik.add("[0:35] Kau tak mengharap tanda jasa");
        lirik.add("[0:40] Dan kau ikhlaskan untuk Indonesia");
        lirik.add("[0:45] ");
        lirik.add("[0:45] [Chorus]");
        lirik.add("[0:45] Terima kasih Pak Jokowi");
        lirik.add("[0:50] Ribuan desa telah kau datangi");
        lirik.add("[0:55] Kau menyemangati, kau memberi arti");
        lirik.add("[1:00] Untuk negeri");
        lirik.add("[1:05] Terima kasih Pak Jokowi");
        lirik.add("[1:10] Hanya Tuhan yang mampu membalasi");
        lirik.add("[1:15] Kebaikan hati yang selalu kau beri");
        lirik.add("[1:20] Untuk negeri");
        lirik.add("[1:25] ");
        lirik.add("[1:25] [Instrumental Interlude]");
        lirik.add("[1:30] ");
        lirik.add("[2:00] [Chorus]");
        lirik.add("[2:00] Terima kasih Pak Jokowi");
        lirik.add("[2:05] Ribuan desa telah kau datangi");
        lirik.add("[2:10] Kau menyemangati, kau memberi arti");
        lirik.add("[2:15] Untuk negeri");
        lirik.add("[2:20] Terima kasih Pak Jokowi");
        lirik.add("[2:25] Hanya Tuhan yang mampu membalasi");
        lirik.add("[2:30] Kebaikan hati yang selalu kau beri");
        lirik.add("[2:35] Untuk negeri");
        lirik.add("[2:40] Terima kasih Pak Jokowi");
        lirik.add("[2:45] Ribuan desa telah kau datangi");
        lirik.add("[2:50] Kau menyemangati, kau memberi arti");
        lirik.add("[2:55] Untuk negeri");
        lirik.add("[3:00] Terima kasih Pak Jokowi");
        lirik.add("[3:05] Hanya Tuhan yang mampu membalasi");
        lirik.add("[3:10] Kebaikan hati yang selalu kau beri");
        lirik.add("[3:15] Untuk negeri");
        return lirik;
    }

    //Lirik: Kicau Mania

    private static List<String> createLirikKicauMania() {
        List<String> lirik = new ArrayList<>();
        lirik.add("[0:00] Kicau Mania");
        lirik.add("[0:05] ");
        lirik.add("[0:05] Tak rumat seka piyik");
        lirik.add("[0:10] Tak loloh nganggo jangkrik");
        lirik.add("[0:15] Aku pamit nggantang, ya, Dhik");
        lirik.add("[0:20] Muga rejekine apik");
        lirik.add("[0:25] Untuk para kicau mania");
        lirik.add("[0:30] Masih bersama Ndarboy Genk");
        lirik.add("[0:35] Banditoz Yaow 86");
        lirik.add("[0:40] All Yaow ready?");
        lirik.add("[0:45] Kicau, kicau, kicau mania (hm)");
        lirik.add("[0:50] Kicau, kicau, kicau mania (hm)");
        lirik.add("[0:55] Kicau, kicau, kicau mania (hm)");
        lirik.add("[1:00] Kicau, kicau, kicau mania (hm)");
        lirik.add("[1:05] ");
        lirik.add("[1:05] Ora nggantang, ora mangan, ra nduwe gaji bulanan");
        lirik.add("[1:10] Lah wong dudu cah kantoran, tangga-tangga padha isin");
        lirik.add("[1:15] Yen kepethuk isih esuk, aku uwis ngelus manuk");
        lirik.add("[1:20] Lungguh ndhodhok, nyumet rokok, manukku wis manthuk-manthuk");
        lirik.add("[1:25] Penak dadi cah gantangan, ditelateni, ya lumayan");
        lirik.add("[1:30] Start seka mung latberan, ra wedi ro sing bos-bosan");
        lirik.add("[1:35] Manuk sekti mandraguna, modhal nekat karo ndonga");
        lirik.add("[1:40] Kicau mania gawan bayi, manukku wani kemaki");
        lirik.add("[1:45] Kicau, kicau, kicau mania (hm)");
        lirik.add("[1:50] Kicau, kicau, kicau mania (hm)");
        lirik.add("[1:55] Kicau, kicau, kicau mania (hm)");
        lirik.add("[2:00] Kicau, kicau, kicau mania (hm)");
        lirik.add("[2:05] ");
        lirik.add("[2:05] Burungku datang, semua senang");
        lirik.add("[2:10] Burung berjuang, suara lantang");
        lirik.add("[2:15] Burung berdendang di atas tiang");
        lirik.add("[2:20] Ra kudu kondhang, sing penting menang");
        lirik.add("[2:25] Burungku gacor, wis mesthi skor");
        lirik.add("[2:30] Burungku merji, ra sah dha meri");
        lirik.add("[2:35] Burung menari, naikkan tensi");
        lirik.add("[2:40] Angkatlah topi, minat? Mangga japri");
        lirik.add("[2:45] Kicau, kicau, kicau mania (hm)");
        lirik.add("[2:50] Kicau, kicau, kicau mania (hm)");
        lirik.add("[2:55] Kicau, kicau, kicau mania (hm)");
        lirik.add("[3:00] Kicau, kicau, kicau mania (hm)");
        lirik.add("[3:05] ");
        lirik.add("[3:05] Gas pol ndangak, digas pol ndangak-ndangak");
        lirik.add("[3:10] Manukku siap nembak, poinku ra keoyak");
        lirik.add("[3:15] Ra isa gliyak-gliyak, stelan wis gas pol ndangak");
        lirik.add("[3:20] Muraiku menang mutlak, liyane mung padha nyimak");
        lirik.add("[3:25] Aku ra golek musuh, ya ora seneng rusuh");
        lirik.add("[3:30] Yen padha kepethuk, aja lali aruh-aruh");
        lirik.add("[3:35] Padha cah gantangan, ra sah iren-irenan");
        lirik.add("[3:40] Seduluran merga hobi, muga berkah ngrejekeni");
        lirik.add("[3:45] Kicau, kicau, kicau mania (hm)");
        lirik.add("[3:50] Kicau, kicau, kicau mania (hm)");
        lirik.add("[3:55] Kicau, kicau, kicau mania (hm)");
        lirik.add("[4:00] Kicau, kicau, kicau mania (hm)");
        lirik.add("[4:05] Kicau, kicau, kicau mania (hm)");
        lirik.add("[4:10] Kicau, kicau, kicau mania (hm)");
        lirik.add("[4:15] Kicau, kicau, kicau mania (hm)");
        lirik.add("[4:20] Kicau, kicau, kicau mania (hm)");
        lirik.add("[4:25] Kicau, kicau, kicau mania (hm)");
        lirik.add("[4:30] Kicau, kicau, kicau mania (hm)");
        lirik.add("[4:35] Kicau, kicau, kicau mania (hm)");
        lirik.add("[4:40] Kicau, kicau, kicau mania (hm)");
        return lirik;
    }

    //Lirik: NO BATIDAO

    private static List<String> createLirikNoBatidao() {
        List<String> lirik = new ArrayList<>();
        lirik.add("[0:00] NO BATIDÃO");
        lirik.add("[0:05] ");
        lirik.add("[0:05] Ela, ela desce, ela sobe");
        lirik.add("[0:10] No baile é pressão");
        lirik.add("[0:15] Mina linda, perigosa");
        lirik.add("[0:20] Rouba meu coração");
        lirik.add("[0:25] Vai quicando, vai jogando");
        lirik.add("[0:30] Não perde a razão");
        lirik.add("[0:35] No batidão, no batidão");
        lirik.add("[0:40] Só pura tentação");
        lirik.add("[0:45] ");
        lirik.add("[0:45] Ela desce, ela sobe");
        lirik.add("[0:50] No baile é pressão");
        lirik.add("[0:55] Mina linda, perigosa");
        lirik.add("[1:00] Rouba meu coração");
        lirik.add("[1:05] ");
        lirik.add("[1:05] Ela desce, ela sobe");
        lirik.add("[1:10] No baile é pressão");
        lirik.add("[1:15] Mina linda, perigosa");
        lirik.add("[1:20] Rouba meu coração");
        lirik.add("[1:25] ");
        lirik.add("[1:25] Ela desce, ela sobe");
        lirik.add("[1:30] No baile é pressão");
        lirik.add("[1:35] Mina linda, perigosa");
        lirik.add("[1:40] Rouba meu coração");
        lirik.add("[1:45] Vai quicando, vai jogando");
        lirik.add("[1:50] Não perde a razão");
        lirik.add("[1:55] No batidão, no batidão");
        lirik.add("[2:00] Só pura tentação");
        lirik.add("[2:05] ");
        lirik.add("[2:05] Desce, ela sobe");
        lirik.add("[2:10] No baile é pressão");
        lirik.add("[2:15] Mina linda, perigosa");
        lirik.add("[2:20] Rouba meu coração");
        lirik.add("[2:25] Ela desce, ela sobe");
        lirik.add("[2:30] No baile é pressão");
        lirik.add("[2:35] Mina linda, perigosa");
        lirik.add("[2:40] Rouba meu coração");
        lirik.add("[2:45] Ela desce, ela sobe");
        lirik.add("[2:50] No baile é pressão");
        lirik.add("[2:55] Mina linda, perigosa");
        lirik.add("[3:00] Rouba meu coração");
        lirik.add("[3:05] Vai quicando, vai jogando");
        lirik.add("[3:10] Não perde a razão");
        lirik.add("[3:15] No batidão, no batidão");
        lirik.add("[3:20] Só pura tentação");
        return lirik;
    }
}