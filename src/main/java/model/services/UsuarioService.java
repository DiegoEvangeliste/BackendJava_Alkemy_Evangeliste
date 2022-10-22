package model.services;

import model.entitys.Usuario;
import model.repositorys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //@Transactional(readOnly = true)
    public Iterable<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    //@Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    //@Transactional
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    //@Transactional
    public void deleteById(Long id){
        usuarioRepository.deleteById(id);
    }

        /////////////////////////////////////////

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

}
