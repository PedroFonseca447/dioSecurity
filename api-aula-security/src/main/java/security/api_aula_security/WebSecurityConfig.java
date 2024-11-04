package security.api_aula_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import security.api_aula_security.service.SecurityDataBaseService;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    // Configuração de segurança existente
    
    @Autowired
    private SecurityDataBaseService securityService;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(securityService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers(HttpMethod.POST, "/login").permitAll() //posso definir tipos de métodos que esse recurso pode ter acesso( somente post)
                .requestMatchers("/managers").hasRole("MANAGERS")
                .requestMatchers("/users").hasAnyRole("USERS", "MANAGERS")
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());  // Usa o método com configurações padrão e define que é uma autenticação básica

        return http.build();
    }

    // Define o usuário e senha em memória
    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user = User.withUsername("usuario")
    //             .password(passwordEncoder().encode("senha123"))  // Define e encripta a senha
    //             .roles("USERS")  // Define o papel (role) do usuário
    //             .build();

    //     UserDetails manager = User.withUsername("gerente")
    //             .password(passwordEncoder().encode("senha1234"))
    //             .roles("MANAGERS")
    //             .build();

    //     return new InMemoryUserDetailsManager(user, manager);
    // }

    // // Configura o codificador de senha
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }
}
