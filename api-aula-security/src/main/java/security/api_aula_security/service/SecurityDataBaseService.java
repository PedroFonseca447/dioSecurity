package security.api_aula_security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import security.api_aula_security.model.User;
import security.api_aula_security.repository.UserRepository;

@Service
public class SecurityDataBaseService implements UserDetailsService {

    @Autowired 
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            throw new UsernameNotFoundException(username);
        }
        //spring valida as caracteristicas de segurança do usuario 
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        userEntity.getRoles().forEach( role -> {
            authorities.add(new SimpleGrantedAuthority( "ROLE_" + role));
        });

        UserDetails user = new org.springframework.security.core.userdetails.User(userEntity.getUsername(), 
            userEntity.getPassword(),
            authorities);

        return user;

    }




    }

