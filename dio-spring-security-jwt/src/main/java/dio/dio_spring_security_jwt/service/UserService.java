package dio.dio_spring_security_jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import  dio.dio_spring_security_jwt.model.User;
import dio.dio_spring_security_jwt.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public void createUser(User user) {
        String pass = user.getPassword();

        // Verifique se a senha não é nula ou vazia
        if (pass == null || pass.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        System.out.println("Usuário salvo: " + user.getUsername());
        // Criptografando a senha antes de salvar no banco de dados
        user.setPassword(encoder.encode(pass));
        repository.save(user);
    }
}
