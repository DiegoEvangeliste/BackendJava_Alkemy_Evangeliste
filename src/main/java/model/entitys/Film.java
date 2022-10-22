package model.entitys;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private LocalDate fechaCreacion;
    private Integer calificacion;
    private ArrayList<Personaje> myPersonajes;
    private ArrayList<Genero> myGeneros;

    public Film(){}

    public Film(Long id, String titulo, LocalDate fechaCreacion, Integer calificacion) {
        this.id = id;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        this.myPersonajes = new ArrayList<>();
        this.myGeneros = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public ArrayList<Personaje> getAllMyPersonajes() {
        return myPersonajes;
    }

    public void setMyPersonajes(ArrayList<Personaje> personajes) {
        this.myPersonajes.addAll(personajes);
    }

    public Film addPersonaje(Personaje personaje) {
        this.myPersonajes.add(personaje);
        return this;
    }

    public Film deletePersonaje(Personaje personaje) {
        this.myPersonajes.remove(personaje);
        return this;
    }

    public ArrayList<Genero> getAllMyGeneros() {
        return myGeneros;
    }

    public void setMyGeneros(ArrayList<Genero> generos) {
        this.myGeneros.addAll(generos);
    }

    public void addGenero(Genero genero) {
        this.myGeneros.add(genero);
    }

    public boolean existsGenere(Long idGenere) {
        for(Genero genero : myGeneros)
            if(genero.getId().equals(idGenere)){
                return true;
            }

        return false;
    }

    public boolean existsPersonaje(Long idPersonaje) {
        for(Personaje personaje : myPersonajes)
            if(personaje.getId().equals(idPersonaje)){
                return true;
            }

        return false;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", calificacion=" + calificacion +
                ", myPersonajes=" + myPersonajes +
                '}';
    }
}
