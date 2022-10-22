package model.controllers;

import model.entitys.Personaje;
import model.services.PersonajeService;
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
@RequestMapping("/api/personajes")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;
    private final Logger log = LoggerFactory.getLogger(PersonajeService.class);

    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    @PostMapping
    public ResponseEntity<Personaje> create(@RequestBody Personaje personaje){
        if(personaje.getId() != null) {
            log.warn("No se puede crear el personaje, porque este ID ya existe");
            return ResponseEntity.badRequest().build();
        }
        Personaje resultado = personajeService.save(personaje);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personaje> findById(@PathVariable Long id){
        Optional<Personaje> opPersonaje = personajeService.findById(id);

        if (!opPersonaje.isPresent()){
            log.warn("El personaje no existe en la base de datos");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(opPersonaje.get());
    }

    @GetMapping
    public List<Personaje> findAll(){
        return StreamSupport
                .stream(personajeService.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personaje> update(@RequestBody Personaje personaje, @PathVariable Long id) {
        if(personaje.getId() == null){
            log.warn("No se puede actualizar el personaje, ya que no tienen Id");
            return ResponseEntity.badRequest().build();
        }

        Optional<Personaje> opPersonaje = personajeService.findById(id);

        if (!opPersonaje.isPresent()){
            log.warn("El personaje no existe en la base de datos");
            return ResponseEntity.notFound().build();
        }

        opPersonaje.get().setNombre(personaje.getNombre());
        opPersonaje.get().setEdad(personaje.getEdad());
        opPersonaje.get().setPeso(personaje.getPeso());
        opPersonaje.get().setHistoria(personaje.getHistoria());

        Personaje resultado = personajeService.save(opPersonaje.get());
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Personaje> delete(@PathVariable Long id) {
        Optional<Personaje> opPersonaje = personajeService.findById(id);

        if(!opPersonaje.isPresent()) {
            log.warn("No se puede borrar un personaje que no está en la base de datos");
            return ResponseEntity.notFound().build();
        }

        personajeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Personaje> findByName(@PathVariable String name){
        Optional<Personaje> opPersonaje = personajeService.findByName(name);

        if (!opPersonaje.isPresent()){
            log.warn("El personaje no existe en la base de datos");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(opPersonaje.get());
    }

    @GetMapping("/edad/{varEdad}")
    public ResponseEntity<List<Personaje>> filterByEdad(@PathVariable Integer varEdad){
        if(varEdad <= 0){
            log.warn("La edad del personaje no puede ser menor o igual a 0 (cero)");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(personajeService.filterByEdad(varEdad));
    }

    @GetMapping("/peso/{varPeso}")
    public ResponseEntity<List<Personaje>> filterByPeso(@PathVariable Double varPeso){
        if(varPeso <= 0) {
            log.warn("El peso del personaje no puede ser menor o igual a 0 (cero)");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(personajeService.filterByPeso(varPeso));
    }

    @GetMapping("/films/{idFilm}")
    public ResponseEntity<List<Personaje>> filterByFilm(@PathVariable Long idFilm){
        if(idFilm <= 0){
            log.warn("El ID del personaje no puede ser menor o igual a 0 (cero)");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(personajeService.filterByFilm(idFilm));
    }

}
