package model.controllers;

import model.entitys.Film;
import model.entitys.Genero;
import model.entitys.Personaje;
import model.services.FilmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/movies")
public class FilmController {

    @Autowired
    private FilmService filmService;
    private final Logger log = LoggerFactory.getLogger(FilmService.class);

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<Film> create(@RequestBody Film film){
        if(film.getId() != null) {
            log.warn("No se puede crear el film, porque este ID ya existe");
            return ResponseEntity.badRequest().build();
        }
        Film resultado = filmService.save(film);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> findById(@PathVariable Long id){
        Optional<Film> opFilm = filmService.findById(id);

        if(!opFilm.isPresent()){
            log.warn("El film no existe en la base de datos");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(opFilm.get());
    }

    @GetMapping
    public List<Film> findAll(){
        return StreamSupport
                .stream(filmService.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> update(@RequestBody Film film, @PathVariable Long id) {
        if(film.getId() == null){
            log.warn("No se puede actualizar el film, ya que no tienen Id");
            return ResponseEntity.badRequest().build();
        }

        Optional<Film> opFilm = filmService.findById(id);

        if (!opFilm.isPresent()){
            log.warn("El film no existe en la base de datos");
            return ResponseEntity.notFound().build();
        }

        opFilm.get().setTitulo(film.getTitulo());
        opFilm.get().setFechaCreacion(film.getFechaCreacion());
        opFilm.get().setCalificacion(film.getCalificacion());

        Film resultado = filmService.save(opFilm.get());
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Film> delete(@PathVariable Long id) {
        Optional<Film> opFilm = filmService.findById(id);

        if(!opFilm.isPresent()) {
            log.warn("No se puede borrar un film que no está en la base de datos");
            return ResponseEntity.notFound().build();
        }

        filmService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{nameTitle}")
    public ResponseEntity<Film> findByTitle(@PathVariable String nameTitle){
        Optional<Film> opFilm = filmService.findByTitle(nameTitle);

        if(!opFilm.isPresent()){
            log.warn("El film no existe en la base de datos");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(opFilm.get());
    }

    @GetMapping("/generes/{idGenere}")
    public ResponseEntity<List<Film>> filterByGenere(@PathVariable Long idGenere){
        if(idGenere <= 0) {
            log.warn("El ID del genero no puede ser menor o igual a 0 (cero)");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(filmService.filterByGenere(idGenere));
    }

    @PostMapping("/{idMovie}/personaje/{idPersonaje}")
    public ResponseEntity<Film> addPersonaje(@PathVariable Long idMovie, @RequestBody Personaje personaje, @PathVariable Long idPersonaje){
        Optional<Film> opFilm = filmService.findById(idMovie);

        if(opFilm.isPresent()){
            if(personaje.getId().equals(idPersonaje)){
                if (!opFilm.get().existsPersonaje(idPersonaje)) {
                    Film resultado = filmService.save(filmService.addPersonaje(opFilm.get(), personaje));
                    return ResponseEntity.ok(resultado);
                } else {
                    log.warn("El personaje que quiere agregar ya existe en la pelicula");
                }
            } else {
                log.warn("El id del personaje no coincide con el personaje que quiere agregar");
            }
            return ResponseEntity.badRequest().build();
        }

        log.warn("No existe en la base de datos el id del film solicitado");
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idMovie/personaje/{idPersonaje}")
    public ResponseEntity<Film> deletePersonaje(@PathVariable Long idMovie, @RequestBody Personaje personaje, @PathVariable Long idPersonaje){
        Optional<Film> opFilm = filmService.findById(idMovie);

        if(opFilm.isPresent()){
            if(personaje.getId().equals(idPersonaje)){
                if (opFilm.get().existsPersonaje(idPersonaje)) {
                    Film resultado = filmService.save(filmService.deletePersonaje(opFilm.get(), personaje));
                    return ResponseEntity.ok(resultado);
                } else {
                    log.warn("El personaje que quiere eliminar no existe en la pelicula");
                }
            } else {
                log.warn("El id del personaje no coincide con el personaje que quiere eliminar");
            }
            return ResponseEntity.badRequest().build();
        }

        log.warn("No existe en la base de datos el id del film solicitado");
        return ResponseEntity.notFound().build();
    }

}
