package security.api_aula_security.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import security.api_aula_security.model.User;
//unidade User e o que o identifica Um id em Integer 
public interface UserRepository extends JpaRepository<User, Integer>{
    //o acesso da controller a tabela de armazenamento, aqui estamos tendo os scripts que o jpa no oferece a memória
    @Query("SELECT e FROM User e JOIN FETCH e.roles WHERE e.username= (:username)")
    public User findByUsername(@Param("username") String username); //devolve os perfis de acesso dos usuarios 

}
