package model.entitys;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "personajes")
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer edad;
    private Double peso;
    private String historia;
    private ArrayList<Film> myFilms;

    public Personaje(){}

    public Personaje(Long id, String nombre, Integer edad, Double peso, String historia) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public ArrayList<Film> getAllMyFilms() {
        return myFilms;
    }

    public void setMyFilms(ArrayList<Film> films) {
        for(Film film : films){
            this.myFilms.add(film);
        }
    }

    public boolean existsFilm(Long idFilm) {
        for(Film film : myFilms)
            if(film.getId().equals(idFilm)){
                return true;
            }

        return false;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", peso=" + peso +
                ", historia='" + historia + '\'' +
                ", myFilms=" + myFilms +
                '}';
    }
}