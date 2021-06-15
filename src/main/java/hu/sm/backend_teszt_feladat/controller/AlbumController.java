package hu.sm.backend_teszt_feladat.controller;

import hu.sm.backend_teszt_feladat.entity.Album;
import hu.sm.backend_teszt_feladat.entity.AlbumRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbumController {

    private final AlbumRepository albumRepository;

    public AlbumController(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

//    Legyen listázve lekérhetőek az albumok, 10-esével lapozhatóan, és rendezhetően cím vagy megjelenés éve szerint.
//A lista legyen szűrhető előadóra, és címre.
//A válasz tartalmazza az album adatain kívül a hozzátartozó trackek számát, és a lejátszási idő hosszát is.
    @RequestMapping(value = "/albums", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Album> getAlbums(@RequestParam int page) {
        return albumRepository.findAll();
    }

//    ID alapján legyen lekérhető egy album minden alapadata és a hozzátartozó trackek listája. 
//    A trackek listájában szerepeljen a számok címe, hossza és stílusa.
    @GetMapping(path = "/album")
    public @ResponseBody
    Album getAlbum(@RequestParam Long id) {
        return new Album();
    }

}
