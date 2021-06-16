package hu.sm.backend_teszt_feladat.dto;

import hu.sm.backend_teszt_feladat.entity.Album;
import java.util.ArrayList;
import java.util.List;

public class AlbumDto {
    
    public Album album;
    public List<TrackDto> tracks = new ArrayList<TrackDto>();
    
}
