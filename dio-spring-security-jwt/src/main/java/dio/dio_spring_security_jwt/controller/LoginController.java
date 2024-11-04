package dio.dio_spring_security_jwt.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dio.dio_spring_security_jwt.dto.Login;
import dio.dio_spring_security_jwt.dto.Sessao;
import dio.dio_spring_security_jwt.model.User;
import dio.dio_spring_security_jwt.repository.UserRepository;
import dio.dio_spring_security_jwt.security.JWTCreator;
import dio.dio_spring_security_jwt.security.JWTObject;
import dio.dio_spring_security_jwt.security.SecurityConfig;

//quem faz a verificacao de autenticacao


@RestController
public class LoginController {
    


    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository repository;



    @PostMapping("/login")
    public Sessao logar(@RequestBody Login login){
        System.out.println("Tentando fazer login com usuário: " + login.getUsername());
        User user = repository.findByUsername(login.getUsername());
        System.out.println(login.getUsername());

        if(user!=null){
            boolean password0k = encoder.matches(login.getPassword(), user.getPassword());

            if(!password0k){
                throw new RuntimeException("Senha invalida para o login: " + login.getUsername());
                
            }

            Sessao sessao = new Sessao();
            sessao.setLogin(user.getUsername());

            
            JWTObject jwtObject = new JWTObject();
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(user.getRoles());
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
            return sessao;
        }else {
            throw new RuntimeException("Erro ao tentar fazer login");
        }
    }   

}
