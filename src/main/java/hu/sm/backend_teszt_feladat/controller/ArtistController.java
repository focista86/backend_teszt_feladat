package hu.sm.backend_teszt_feladat.controller;

import hu.sm.backend_teszt_feladat.entity.Artists;
import hu.sm.backend_teszt_feladat.entity.ArtistsRepository;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtistController {
    
    private final ArtistsRepository artistsRepository;

    public ArtistController(ArtistsRepository artistsRepository) {
        this.artistsRepository = artistsRepository;
    }
    
    @GetMapping(path = "/artists")
    public List<Artists> getArtists(@RequestParam int page) {
        return artistsRepository.findAll();
    }
    
    @GetMapping(path = "/artist")
    public Artists getArtist(@RequestParam Long id) {
        return artistsRepository.getOne(id);
    }
    
    @PostMapping(path = "/artist")
    public void setArtist(@RequestParam Artists artists) {
        artistsRepository.save(artists);
    }
    
    @DeleteMapping(path = "/artist")
    public void deleteArtist(@RequestParam Long id) {
        artistsRepository.deleteById(id);
    }
    
}
