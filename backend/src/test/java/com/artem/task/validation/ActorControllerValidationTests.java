package com.artem.task.validation;

import com.artem.task.controller.ActorController;
import com.artem.task.dto.ActorDTO;
import com.artem.task.dto.ActorUpdateDTO;
import com.artem.task.exception.GlobalExceptionHandler;
import com.artem.task.service.ActorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActorController.class)
@Import(GlobalExceptionHandler.class)
public class ActorControllerValidationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ActorService actorService;

    //Создание актера с неверными значениями полей
    @Test
    public void testValidationForActorCreate() throws Exception{
        ActorDTO actorDTO = new ActorDTO("", "Фамилия", 33);
        mockMvc.perform(post("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name")
                        .value("Имя актера должно быть длиной от 2 до 255 символов"));

        actorDTO.setName("Имя");
        actorDTO.setLastName("");
        mockMvc.perform(post("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastName")
                        .value("Фамилия актера должна быть длиной от 2 до 255 символов"));

        actorDTO.setLastName("Фамилия");
        actorDTO.setAge(-1);
        mockMvc.perform(post("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.age")
                        .value("Возраст актера не может быть отрицательным"));

        actorDTO.setAge(121);
        mockMvc.perform(post("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.age")
                        .value("Возраст актера не может превышать 120 лет"));
    }
    //Редактирование актера с неверными значениями полей
    @Test
    public void testValidationForActorUpdate() throws Exception {
        ActorUpdateDTO actorUpdateDTO = new ActorUpdateDTO(null, "Имя", "Фамилия", 33);
        mockMvc.perform(put("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id")
                        .value("Id актера не должно быть пустым"));

        actorUpdateDTO.setId(1L);
        actorUpdateDTO.setName("");
        mockMvc.perform(put("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name")
                        .value("Имя актера должно быть длиной от 2 до 255 символов"));

        actorUpdateDTO.setName("Имя");
        actorUpdateDTO.setLastName("");
        mockMvc.perform(put("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastName")
                        .value("Фамилия актера должна быть длиной от 2 до 255 символов"));

        actorUpdateDTO.setLastName("Фамилия");
        actorUpdateDTO.setAge(-1);
        mockMvc.perform(put("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.age")
                        .value("Возраст актера не может быть отрицательным"));

        actorUpdateDTO.setAge(121);
        mockMvc.perform(put("/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.age")
                        .value("Возраст актера не может превышать 120 лет"));
    }
}
