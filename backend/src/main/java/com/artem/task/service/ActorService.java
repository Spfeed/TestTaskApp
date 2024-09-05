package com.artem.task.service;

import com.artem.task.dao.ActorDao;
import com.artem.task.dto.ActorDTO;
import com.artem.task.dto.ActorUpdateDTO;
import com.artem.task.exception.EntityAlreadyExistsException;
import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.model.Actor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActorService {

    private final ActorDao actorDao;

    public ActorService(ActorDao actorDao) {
        this.actorDao = actorDao;
    }

    //CRUD
    //Вывод всех актеров
    public List<ActorDTO> getAllActors() {
        List<Actor> actors = actorDao.findAll();
        List<ActorDTO> actorDTOS = new ArrayList<>();
        for (Actor actor : actors) {
            ActorDTO actorDTO = new ActorDTO(
              actor.getName(),
              actor.getLastName(),
              actor.getAge()
            );
            actorDTOS.add(actorDTO);
        }
        return actorDTOS;
    }
    //Вывод одного по id
    public ActorDTO getActorById(Long id) {
        try {
            Actor actor = actorDao.findById(id);
            return new ActorDTO(
                    actor.getName(),
                    actor.getLastName(),
                    actor.getAge()
            );
        } catch (EntityNotFoundException e) {
            throw e;
        }

    }
    //Создание актера
    public void saveActor(ActorDTO actorDTO) {
        Actor actor = new Actor(
                actorDTO.getName(),
                actorDTO.getLastName(),
                actorDTO.getAge()
        );
        if (!actorDao.findByFullName(actorDTO.getName(), actorDTO.getLastName()).isEmpty()){
            throw new EntityAlreadyExistsException("Актер", actor.getName() + " " + actor.getLastName());
        }
        actorDao.save(actor);
    }
    //Редактирование актера
    public void update(ActorUpdateDTO actorDTO) {
        try {
            Actor actor = new Actor();
            actor.setId(actorDTO.getId());
            actor.setName(actorDTO.getName());
            actor.setLastName(actorDTO.getLastName());
            actor.setAge(actorDTO.getAge());
            actorDao.update(actor);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }
    //Удаление актера по id
    public void deleteActor(Long id) {
        try {
            actorDao.delete(id);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }
    //Поиск актера по имени, фамилии и возрасту
    public Actor getActorByNameLastNameAndAge (String name, String lastName, int age) {
        return actorDao.findByNameAndLastNameAndAge(name, lastName, age);
    }
}
