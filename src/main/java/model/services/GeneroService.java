package model.services;

import model.entitys.Genero;
import model.repositorys.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Transactional(readOnly = true)
    public Iterable<Genero> findAll(){
        return generoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Genero> findById(Long id){
        return generoRepository.findById(id);
    }

    @Transactional
    public Genero save(Genero genero){
        return generoRepository.save(genero);
    }

    @Transactional
    public void deleteById(Long id){
        generoRepository.deleteById(id);
    }

        /////////////////////////////////////////

    /*
    public boolean existsByUsername(String username){
        List<Usuario> ListUsers = usuarioRepository.findAll();
        for(Usuario us : ListUsers){
            if(us.getUsername().equals(username)){
                return true;
            }
        }

        return false;
    }

    public boolean existsByEmail(String email){
        List<Usuario> ListUsers = usuarioRepository.findAll();
        for(Usuario us : ListUsers){
            if(us.getEmail().equals(email)){
                return true;
            }
        }

        return false;
    }

    public Optional<Usuario> findByUsername(String username){
        Optional<Usuario> opUser = Optional.empty();
        List<Usuario> ListUsers = usuarioRepository.findAll();
        for(Usuario us : ListUsers){
            if(us.getUsername().equals(username)){
                opUser = Optional.of(us);

            }
        }

        return opUser;
    }

     */

}
