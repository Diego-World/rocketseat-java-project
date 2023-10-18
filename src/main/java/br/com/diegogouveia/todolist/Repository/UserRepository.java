package br.com.diegogouveia.todolist.Repository;

import br.com.diegogouveia.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>{
    User findByUsername(String username);
}
