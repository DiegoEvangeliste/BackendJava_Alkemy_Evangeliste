package model.services;

import model.entitys.Personaje;
import model.repositorys.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Transactional(readOnly = true)
    public Iterable<Personaje> findAll(){
        return personajeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Personaje> findById(Long id){
        return personajeRepository.findById(id);
    }

    @Transactional
    public Personaje save(Personaje personaje){
        return personajeRepository.save(personaje);
    }

    @Transactional
    public void deleteById(Long id){
        personajeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Personaje> findByName(String name){
        Optional<Personaje> opPersonaje = Optional.empty();
        List<Personaje> listPersonaje = new ArrayList<Personaje>(personajeRepository.findAll());
        for(Personaje personaje : listPersonaje){
            if(personaje.getNombre().equals(name)){
                opPersonaje = Optional.of(personaje);
            }
        }

        return opPersonaje;
    }

    @Transactional(readOnly = true)
    public List<Personaje> filterByEdad(Integer varEdad) {
        List<Personaje> listPersonaje = new ArrayList<Personaje>(personajeRepository.findAll());
        if(varEdad == null)
            return listPersonaje;

        List<Personaje> listFiltered = new ArrayList<Personaje>();
        for(Personaje personaje : listPersonaje){
            if(personaje.getEdad().equals(varEdad))
                listFiltered.add(personaje);
        }

        return listFiltered;
    }

    @Transactional(readOnly = true)
    public List<Personaje> filterByPeso(Double varPeso) {
        List<Personaje> listPersonaje = new ArrayList<Personaje>(personajeRepository.findAll());
        if(varPeso == null)
            return listPersonaje;

        List<Personaje> listFiltered = new ArrayList<Personaje>();
        for(Personaje personaje : listPersonaje){
            if(personaje.getPeso().equals(varPeso))
                listFiltered.add(personaje);
        }

        return listFiltered;
    }

    @Transactional(readOnly = true)
    public List<Personaje> filterByFilm(Long idFilm) {
        List<Personaje> listPersonaje = new ArrayList<Personaje>(personajeRepository.findAll());
        if(idFilm == null)
            return listPersonaje;

        List<Personaje> listFiltered = new ArrayList<Personaje>();
        for(Personaje personaje : listPersonaje){
            if(personaje.existsFilm(idFilm))
                listFiltered.add(personaje);
        }

        return listFiltered;
    }
}
