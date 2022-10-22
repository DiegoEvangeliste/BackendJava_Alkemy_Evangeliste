package model.controllers;

import model.entitys.Genero;
import model.services.GeneroService;
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
@RequestMapping("/api/generes")
public class GeneroController {

    @Autowired
    private GeneroService generoService;
    private final Logger log = LoggerFactory.getLogger(GeneroService.class);

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @PostMapping
    public ResponseEntity<Genero> create(@RequestBody Genero genero){
        if(genero.getId() != null) {
            log.warn("No se puede crear el genero, porque este ID ya existe");
            return ResponseEntity.badRequest().build();
        }
        Genero resultado = generoService.save(genero);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> findById(@PathVariable Long id){
        Optional<Genero> opGenero = generoService.findById(id);

        if (!opGenero.isPresent()){
            log.warn("El genero no existe en la base de datos");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(opGenero.get());
    }

    @GetMapping
    public List<Genero> findAll(){
        return StreamSupport
                .stream(generoService.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> update(@RequestBody Genero genero, @PathVariable Long id) {
        if(genero.getId() == null){
            log.warn("No se puede actualizar el genero, ya que no tienen Id");
            return ResponseEntity.badRequest().build();
        }

        Optional<Genero> opGenero = generoService.findById(id);

        if (!opGenero.isPresent()){
            log.warn("El genero no existe en la base de datos");
            return ResponseEntity.notFound().build();
        }

        opGenero.get().setNombre(genero.getNombre());

        Genero resultado = generoService.save(opGenero.get());
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Genero> delete(@PathVariable Long id) {
        Optional<Genero> opGenero = generoService.findById(id);

        if(!opGenero.isPresent()) {
            log.warn("No se puede borrar un genero que no está en la base de datos");
            return ResponseEntity.notFound().build();
        }

        generoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
