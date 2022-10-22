package security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSegurityConfig {

    // EL METODO SIGUIENTE CONFIGURA QUE PERMISIVIDAD TENDRAN CIERTOS URLS, O QUE USUARIOS PODRAN ACCEDER O NO
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/auth/register").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic();
        return http.build();
    }

    // EL METODO SIGUIENTE DEFINE EN MEMORIA LOS USUARIOS Y SU PASSWORD CORRESPONDIENTE
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEnc().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    //  EL METODO SIGUIENTE LO QUE PERMITE ES ENCRIPTAR LA CONTRASEÑA PARA BRINDARLE MAS SEGURIDAD FRENTE A UN ATAQUE/HACKEO EN LA BD
    @Bean
    public PasswordEncoder passwordEnc(){
        return new BCryptPasswordEncoder();
    }


}