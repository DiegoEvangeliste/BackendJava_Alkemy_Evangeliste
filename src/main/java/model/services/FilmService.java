package model.services;

import model.entitys.Film;
import model.entitys.Personaje;
import model.repositorys.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Transactional(readOnly = true)
    public Iterable<Film> findAll(){
        return filmRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Film> findById(Long id){
        return filmRepository.findById(id);
    }

    @Transactional
    public Film save(Film film){
        return filmRepository.save(film);
    }

    @Transactional
    public void deleteById(Long id){
        filmRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Film> findByTitle(String nameTitle) {
        Optional<Film> opFilm = Optional.empty();
        List<Film> listFilm = new ArrayList<Film>(filmRepository.findAll());
        for(Film film : listFilm){
            if(film.getTitulo().equals(nameTitle)){
                opFilm = Optional.of(film);
            }
        }

        return opFilm;
    }

    @Transactional(readOnly = true)
    public List<Film> filterByGenere(Long idGenere) {
        List<Film> listFilm = new ArrayList<Film>(filmRepository.findAll());
        if(idGenere == null)
            return listFilm;

        List<Film> listFiltered = new ArrayList<Film>();
        for(Film film : listFilm){
            if(film.existsGenere(idGenere))
                listFiltered.add(film);
        }

        return listFiltered;
    }

    @Transactional
    public Film addPersonaje(Film film, Personaje personaje) {
        return film.addPersonaje(personaje);
    }

    @Transactional
    public Film deletePersonaje(Film film, Personaje personaje) {
        return film.deletePersonaje(personaje);
    }
}
