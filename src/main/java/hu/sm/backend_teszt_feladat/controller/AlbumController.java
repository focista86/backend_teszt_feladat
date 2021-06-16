package hu.sm.backend_teszt_feladat.controller;

import hu.sm.backend_teszt_feladat.dto.AlbumDto;
import hu.sm.backend_teszt_feladat.dto.AlbumsDto;
import hu.sm.backend_teszt_feladat.dto.ArtistsDto;
import hu.sm.backend_teszt_feladat.dto.TrackDto;
import hu.sm.backend_teszt_feladat.entity.Album;
import hu.sm.backend_teszt_feladat.entity.AlbumRepository;
import hu.sm.backend_teszt_feladat.entity.Artists;
import hu.sm.backend_teszt_feladat.entity.ArtistsRepository;
import hu.sm.backend_teszt_feladat.entity.Track;
import hu.sm.backend_teszt_feladat.entity.TrackRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbumController {

    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;
    private final ArtistsRepository artistsRepository;

    public AlbumController(AlbumRepository albumRepository, TrackRepository trackRepository, ArtistsRepository artistsRepository) {
        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
        this.artistsRepository = artistsRepository;
    }

//    Legyen listázve lekérhetőek az albumok, 10-esével lapozhatóan, és rendezhetően cím vagy megjelenés éve szerint.
//A lista legyen szűrhető előadóra, és címre.
//A válasz tartalmazza az album adatain kívül a hozzátartozó trackek számát, és a lejátszási idő hosszát is.
    @GetMapping(path = "/albums")
    public List<AlbumsDto> getAlbums(@RequestParam int page, @RequestParam(required = false) String sort,
            @RequestParam(required = false) String artistSearch, @RequestParam(required = false) String titleSearch) {
        List<Album> albumDb;
        List<Album> albumFilter;
        if (sort != null && (sort.equals("releaseDate") || sort.equals("title"))) {
            albumDb = albumRepository.findAll(Sort.by(Sort.Direction.ASC, sort));
        } else {
            albumDb = albumRepository.findAll();
        }
        if (artistSearch != null) {
            Artists artists = artistsRepository.findById(Long.valueOf(artistSearch)).get();
            albumFilter = albumDb.stream().filter(a -> a.getArtist().equals(artistSearch)).collect(Collectors.toList());;
        } else 
            if (titleSearch != null) {
            albumFilter = albumDb.stream().filter(a -> a.getTitle().equals(titleSearch)).collect(Collectors.toList());;
        } else {
            albumFilter = albumDb;
        }
        
        int lapVege = 10 * page;
        if (lapVege > albumFilter.size()) {
            lapVege = albumFilter.size();
        }
        List<AlbumsDto> albumsDtoList = createAlbumsDtoList(albumFilter.subList((page - 1) * 10, lapVege));
        return albumsDtoList;
    }

//    ID alapján legyen lekérhető egy album minden alapadata és a hozzátartozó trackek listája. 
//    A trackek listájában szerepeljen a számok címe, hossza és stílusa.
    @GetMapping(path = "/album")
    public AlbumDto getAlbum(@RequestParam int id) {
        return createAlbumDto(albumRepository.findById(Long.valueOf(id)).get());
    }

    private AlbumDto createAlbumDto(Album album) {
        AlbumDto albumDto = new AlbumDto();
        albumDto.album = album;
        for (Track track : trackRepository.findAll().stream().filter(t -> t.getAlbum().equals(album)).collect(Collectors.toList())) {
            TrackDto trackDto = new TrackDto();
            trackDto.duration = track.getDuration();
            trackDto.genre = track.getGenre();
            albumDto.tracks.add(trackDto);
        }
        return albumDto;
    }

    private List<AlbumsDto> createAlbumsDtoList(List<Album> albums) {
        List<AlbumsDto> albumsDtos = new ArrayList<AlbumsDto>();
        for (Album album : albums) {
            AlbumsDto albumsDto = new AlbumsDto();
            albumsDto.album = album;
//            Stream<Track> tracks = trackRepository.findAll().stream().filter(a -> a.getAlbum().equals(album));
            albumsDto.numberOfTruck = trackRepository.findAll().stream().filter(a -> a.getAlbum().equals(album)).count();
            trackRepository.findAll().stream().filter(a -> a.getAlbum().equals(album)).forEach(t -> albumsDto.playingTime += t.getDuration() );
            albumsDtos.add(albumsDto);
        }
        return albumsDtos;
    }

}
