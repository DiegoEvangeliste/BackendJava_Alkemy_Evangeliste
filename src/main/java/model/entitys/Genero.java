package model.entitys;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "generos")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private ArrayList<Film> myFilms;

    public Genero(){}

    public Genero(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.myFilms = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Film> getMyFilms() {
        return myFilms;
    }

    public void setMyFilms(ArrayList<Film> films) {
        for(Film film : films) {
            this.myFilms.add(film);
        }
    }

    @Override
    public String toString() {
        return "Genero{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", myFilms=" + myFilms +
                '}';
    }
}