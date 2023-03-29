package com.todo.controller;

import com.todo.dto.UserDTO;
import com.todo.exception.TodoException;
import com.todo.exception.UserException;
import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todoapp")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class Controller {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/user/login")
    public ResponseEntity<User> userLogin(@RequestBody UserDTO userDTO) throws UserException {
        User user = userService.userLogin(userDTO.getEmail(),userDTO.getPassword());
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
    @PostMapping("/user/signup")
    public ResponseEntity<User> userSignUp(@RequestBody User user1) throws UserException {
        User user = userService.addUser(user1);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id,@RequestBody User user) throws UserException {
        User user1 = userService.updateUser(id,user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }
    @PutMapping("/user/{id}/todo/{todoId}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Integer id,@PathVariable Integer todoId ,@RequestBody Todo todo1) throws UserException, TodoException {
        Todo todo = userService.updateTodo(id,todoId,todo1);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }
    @DeleteMapping("/user/{id}/todo/{todoId}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable Integer id,@PathVariable Integer todoId ) throws UserException, TodoException {
        Todo todo = userService.deleteTodo(id,todoId);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }
    @PostMapping("/user/{id}/todo")
    public ResponseEntity<Todo> addTodo(@PathVariable Integer id,@RequestBody Todo todo1) throws UserException, TodoException {
        Todo todo = userService.addTodo(id,todo1);
        return new ResponseEntity<>(todo, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id ) throws UserException {
        User user = userService.deleteUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
