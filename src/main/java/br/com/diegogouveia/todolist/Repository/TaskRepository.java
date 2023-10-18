package br.com.diegogouveia.todolist.Repository;

import br.com.diegogouveia.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByIdUser(UUID idUser);

}
