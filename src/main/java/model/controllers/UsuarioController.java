package model.controllers;

import model.entitys.Usuario;
import model.services.UsuarioService;
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
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    private final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
        if(usuario.getId() != null) {
            log.warn("No se puede crear el usuario, porque este ID ya existe");
            return ResponseEntity.badRequest().build();
        }
        Usuario resultado = usuarioService.save(usuario);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){
        Optional<Usuario> opUsuario = usuarioService.findById(id);

        if (!opUsuario.isPresent()){
            log.warn("El usuario no existe en la base de datos");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(opUsuario.get());
    }

    @GetMapping
    public List<Usuario> findAll(){
        return StreamSupport
                .stream(usuarioService.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario, @PathVariable Long id) {
        if(usuario.getId() == null){
            log.warn("No se puede actualizar el usuario, ya que no tienen Id");
            return ResponseEntity.badRequest().build();
        }

        Optional<Usuario> opUsuario = usuarioService.findById(id);

        if (!opUsuario.isPresent()){
            log.warn("El usuario no existe en la base de datos");
            return ResponseEntity.notFound().build();
        }

        opUsuario.get().setUsername(usuario.getUsername());
        opUsuario.get().setEmail(usuario.getEmail());
        opUsuario.get().setPassword(usuario.getPassword());

        Usuario resultado = usuarioService.save(opUsuario.get());
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Usuario> opUsuario = usuarioService.findById(id);

        if(!opUsuario.isPresent()) {
            log.warn("No se puede borrar un personaje que no está en la base de datos");
            return ResponseEntity.notFound().build();
        }

        usuarioService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username){
        Optional<Usuario> opUsuario = usuarioService.findByUsername(username);

        if (!opUsuario.isPresent()){
            log.warn("El usuario no existe en la base de datos");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(opUsuario);
    }



}
