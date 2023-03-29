package com.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer todoId;
    @NotNull(message = "completed cannot be null")
    private Boolean completed=false;
    @NotNull(message = "description cannot be null")
    @NotBlank(message = "description cannot be blank")
    private String description;
    @NotNull
    @Pattern(regexp = "^(mustdo|couldo|shouldo|maybe)$")
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    @JsonIgnore
    private User user;

    public Todo() {
    }

    public Integer getTodoId() {
        return todoId;
    }

    public void setTodoId(Integer todoId) {
        this.todoId = todoId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
