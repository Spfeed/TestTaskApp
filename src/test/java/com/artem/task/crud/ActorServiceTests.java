package com.artem.task.crud;

import com.artem.task.dao.ActorDao;
import com.artem.task.dto.ActorDTO;
import com.artem.task.dto.ActorUpdateDTO;
import com.artem.task.exception.EntityAlreadyExistsException;
import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.model.Actor;
import com.artem.task.service.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ActorServiceTests {
    private ActorService actorService;
    private ActorDao actorDao;

    @BeforeEach
    void setUp() {
        actorDao = mock(ActorDao.class);
        actorService = new ActorService(actorDao);
    }

    //Поиск несуществующего актера
    @Test
    void testGetActorByIdThrowsEntityNotFoundException() {
        Long actorId = 1L;

        when(actorDao.findById(actorId)).thenThrow(new EntityNotFoundException("Актер", actorId.toString()));

        assertThrows(EntityNotFoundException.class, () -> actorService.getActorById(actorId));

        verify(actorDao, times(1)).findById(actorId);
    }
    //Создание существующего актера
    @Test
    void testSaveActorThrowsEntityAlreadyExistsException() {
        ActorDTO actorDTO = new ActorDTO("Имя", "Фамилия", 35);

        when(actorDao.findByFullName(actorDTO.getName(), actorDTO.getLastName())).thenReturn(List.of(new Actor()));

        assertThrows(EntityAlreadyExistsException.class, () -> actorService.saveActor(actorDTO));

        verify(actorDao, times(1)).findByFullName(actorDTO.getName(), actorDTO.getLastName());
        verify(actorDao, never()).save(any(Actor.class)); // save не должен вызываться
    }
    //Редактирование несуществующего актера
    @Test
    void testUpdateActorThrowsEntityNotFoundException() {
        ActorUpdateDTO actorUpdateDTO = new ActorUpdateDTO(1L, "Имя", "Фамилия", 35);

        doThrow(new EntityNotFoundException("Актер", actorUpdateDTO.getId().toString())).when(actorDao).update(any(Actor.class));

        assertThrows(EntityNotFoundException.class, () -> actorService.update(actorUpdateDTO));

        verify(actorDao, times(1)).update(any(Actor.class));
    }
    //Удаление несуществующего актера
    @Test
    void testDeleteActorThrowsEntityNotFoundException() {
        Long actorId = 1L;

        doThrow(new EntityNotFoundException("Актер", actorId.toString())).when(actorDao).delete(actorId);

        assertThrows(EntityNotFoundException.class, () -> actorService.deleteActor(actorId));

        verify(actorDao, times(1)).delete(actorId);
    }

}
