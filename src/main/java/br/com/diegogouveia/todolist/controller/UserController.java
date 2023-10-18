package br.com.diegogouveia.todolist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.diegogouveia.todolist.Repository.UserRepository;
import br.com.diegogouveia.todolist.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public ResponseEntity create(@RequestBody User user){
        var username = this.userRepository.findByUsername(user.getUsername());

        if(username != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12,user.getPassword().toCharArray());
        user.setPassword(passwordHashed);

        var userCreated = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso");
    }
}
