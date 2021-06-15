package hu.sm.backend_teszt_feladat.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Artists {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String genre;
    private String formedYear;
    private String biography;

    public Artists() {
    }

    public Artists(String name, String genre, String formedYear, String biography) {
        this.name = name;
        this.genre = genre;
        this.formedYear = formedYear;
        this.biography = biography;
    } 
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFormedYear() {
        return formedYear;
    }

    public void setFormedYear(String formedYear) {
        this.formedYear = formedYear;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    
}
