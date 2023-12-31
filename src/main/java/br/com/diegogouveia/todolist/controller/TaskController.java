package br.com.diegogouveia.todolist.controller;

import br.com.diegogouveia.todolist.Repository.TaskRepository;
import br.com.diegogouveia.todolist.model.Task;
import br.com.diegogouveia.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @PostMapping("/")
    public ResponseEntity create(@RequestBody Task task, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        task.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início / data de término deve ser maior que a data atual");
        }

        if(task.getStartAt().isAfter(task.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início deve ser menor que a data de término");
        }
        var createdTask = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/{idUser}")
    public List<Task> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Task task, @PathVariable UUID id, HttpServletRequest request){
        var taskBanco = this.taskRepository.findById(id).orElse(null);

        if(taskBanco == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
        }

        var idUser = request.getAttribute("idUser");

        if(!taskBanco.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário não tem permissão para alterar essa tarefa");
        }

        Utils.copyNonNullProperties(task,taskBanco);
        var updatedTask = this.taskRepository.save(taskBanco);
        return ResponseEntity.ok().body(this.taskRepository.save(updatedTask));
    }

}
