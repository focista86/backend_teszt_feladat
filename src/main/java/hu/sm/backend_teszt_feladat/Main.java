package hu.sm.backend_teszt_feladat;

import hu.sm.backend_teszt_feladat.entity.Album;
import hu.sm.backend_teszt_feladat.entity.AlbumRepository;
import hu.sm.backend_teszt_feladat.entity.Artists;
import hu.sm.backend_teszt_feladat.entity.ArtistsRepository;
import hu.sm.backend_teszt_feladat.entity.TrackRepository;
import java.sql.Date;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner demo(ArtistsRepository artistsRepository, AlbumRepository albumRepository, TrackRepository trackRepository) {
        return (args) -> {
            for (int i = 0; i < 15; i++) {
                artistsRepository.save(new Artists("Kispál és a borz"+i, "Alternativ", "1896", "biography"));
            }
            artistsRepository.save(new Artists("ZAZ", "Alternativ", "1896", "biography"));
            Artists artists = new Artists("Abba", "Alternativ", "1896", "biography");
            artistsRepository.save(artists);
            artistsRepository.save(new Artists("ZAP", "Alternativ", "1896", "biography"));
//            Random rand = new Random();
//            for (int i = 0; i < 10; i++) {
//                Album album = new Album("cim"+i, new Date(2000,1,1), artists, "url"+i);
//                albumRepository.save(album);
//            }
//            for (Artists artists : artistsRepository.findAll()) {
//                System.out.println(artists.toString());
//            }
        };
    }
}
