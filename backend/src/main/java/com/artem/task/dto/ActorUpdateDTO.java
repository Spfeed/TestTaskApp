package com.artem.task.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ActorUpdateDTO {
    @NotNull(message = "Id актера не должно быть пустым")
    private Long id;
    @Size(min = 2, max = 255, message = "Имя актера должно быть длиной от 2 до 255 символов")
    private String name;
    @Size(min = 2, max = 255, message = "Фамилия актера должна быть длиной от 2 до 255 символов")
    private String lastName;
    @Min(value = 0, message = "Возраст актера не может быть отрицательным")
    @Max(value = 120, message = "Возраст актера не может превышать 120 лет")
    private int age;

    public ActorUpdateDTO() {}

    public ActorUpdateDTO(Long id, String name, String lastName, int age) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
