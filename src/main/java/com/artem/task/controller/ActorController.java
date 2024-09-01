package com.artem.task.controller;

import com.artem.task.dto.ActorDTO;
import com.artem.task.dto.ActorUpdateDTO;
import com.artem.task.service.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    //CRUD
    //Вывод всех актеров
    @GetMapping
    public ResponseEntity<List<ActorDTO>> getAllActors() {
        List<ActorDTO> actors = actorService.getAllActors();
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }
    //Вывод актера по id
    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Long id) {
        ActorDTO actor = actorService.getActorById(id);
        return new ResponseEntity<>(actor, HttpStatus.OK);
    }
    //Создание актера
    @PostMapping
    public ResponseEntity<Void> createActor(@RequestBody ActorDTO actorDTO) {
        actorService.saveActor(actorDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //Редактирование существующего актера
    @PutMapping()
    public ResponseEntity<Void> updateActor(@RequestBody ActorUpdateDTO actorUpdateDTO) {
        actorService.update(actorUpdateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Удаление актера
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
