package hu.sm.backend_teszt_feladat.controller;

import hu.sm.backend_teszt_feladat.dto.ArtistsDto;
import hu.sm.backend_teszt_feladat.entity.AlbumRepository;
import hu.sm.backend_teszt_feladat.entity.Artists;
import hu.sm.backend_teszt_feladat.entity.ArtistsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtistController {

    private final ArtistsRepository artistsRepository;
    private final AlbumRepository albumRepository;

    public ArtistController(ArtistsRepository artistsRepository, AlbumRepository albumRepository) {
        this.artistsRepository = artistsRepository;
        this.albumRepository = albumRepository;
    }

    @GetMapping(path = "/artists")
    public List<ArtistsDto> getArtists(@RequestParam int page, @RequestParam(required = false) String sort,
            @RequestParam(required = false) String nameSearch, @RequestParam(required = false) String formedYearSearch) {
        List<Artists> artistsDb;
        List<Artists> artistsFilter;
        if (sort != null && (sort.equals("name") || sort.equals("formedYear"))) {
            artistsDb = artistsRepository.findAll(Sort.by(Sort.Direction.ASC, sort));
        } else {
            artistsDb = artistsRepository.findAll();
        }
        if (nameSearch != null) {
            artistsFilter = artistsDb.stream().filter(a -> a.getName().equals(nameSearch)).collect(Collectors.toList());;
        } else if (formedYearSearch != null) {
            artistsFilter = artistsDb.stream().filter(a -> a.getFormedYear().equals(formedYearSearch)).collect(Collectors.toList());;
        } else {
            artistsFilter = artistsDb;
        }
        
        int lapVege = 10 * page;
        if (lapVege > artistsFilter.size()) {
            lapVege = artistsFilter.size();
        }
        List<ArtistsDto> artistsDtoList = createArtistsDtoList(artistsFilter.subList((page - 1) * 10, lapVege));
        return artistsDtoList;
    }

    @GetMapping(path = "/artist")
    public Artists getArtist(@RequestParam int id) {
        return artistsRepository.findById(Long.valueOf(id)).get();
    }

    @PostMapping(path = "/artist")
    public Artists setArtist(@RequestBody Artists artist) {
        artistsRepository.save(artist);
        return artist;
    }

    @DeleteMapping(path = "/artist")
    public void deleteArtist(@RequestParam Long id) {
        artistsRepository.deleteById(id);
    }

    private List<ArtistsDto> createArtistsDtoList(List<Artists> artists) {
        List<ArtistsDto> artistsDtos = new ArrayList<ArtistsDto>();
        for (Artists artist : artists) {
            ArtistsDto artistsDto = new ArtistsDto();
            artistsDto.artists = artist;
            artistsDto.numberOfAlbums = albumRepository.findAll().stream().filter(a -> a.getArtist().equals(artist)).count();
            artistsDtos.add(artistsDto);
        }
        return artistsDtos;
    }

}
