package com.artem.task.crud;

import com.artem.task.controller.ActorController;
import com.artem.task.dto.ActorDTO;
import com.artem.task.dto.ActorUpdateDTO;
import com.artem.task.exception.EntityAlreadyExistsException;
import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.exception.GlobalExceptionHandler;
import com.artem.task.service.ActorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActorController.class)
@Import(GlobalExceptionHandler.class)
public class ActorControllerCrudTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ActorService actorService;

    //Поиск несуществующего актера
    @Test
    public void testGetNonExistActor() throws Exception {
        Long nonExistActorId = 999L;
        Mockito.when(actorService.getActorById(nonExistActorId))
                .thenThrow(new EntityNotFoundException("Актер", nonExistActorId));
        mockMvc.perform(get("/actors/{id}", nonExistActorId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Актер с ключом '999' не найден."));
    }
    //Создание уже существующего актера
    @Test
    public void testCreateActorWithDuplicateName() throws Exception {
        ActorDTO actorDTO = new ActorDTO("Имя", "Фамилия", 35);

        Mockito.doThrow(new EntityAlreadyExistsException("Актер", actorDTO.getName() + " " + actorDTO.getLastName()))
                .when(actorService).saveActor(Mockito.refEq(actorDTO));

        mockMvc.perform(post("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Актер с ключом 'Имя Фамилия' уже существует."));
    }
    //Редактирование несуществующего актера
    @Test
    public void testUpdateNonExistActor() throws Exception{
        ActorUpdateDTO actorUpdateDTO = new ActorUpdateDTO(
                999L, "Имя", "Фамилия", 35);


        Mockito.doThrow(new EntityNotFoundException("Актер", actorUpdateDTO.getId()))
                .when(actorService).update(Mockito.refEq(actorUpdateDTO));

        mockMvc.perform(put("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorUpdateDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Актер с ключом '999' не найден."));
    }
    // Удаление несуществующего актера
    @Test
    public void testDeleteNonExistActor() throws Exception {
        Long nonExistActorId = 999L;

        Mockito.doThrow(new EntityNotFoundException("Актер", nonExistActorId))
                .when(actorService).deleteActor(nonExistActorId);

        mockMvc.perform(delete("/actors/{id}", nonExistActorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Актер с ключом '999' не найден."));
    }
}
