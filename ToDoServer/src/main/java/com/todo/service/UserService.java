package com.todo.service;

import com.todo.exception.TodoException;
import com.todo.exception.UserException;
import com.todo.model.Todo;
import com.todo.model.User;


public interface UserService {

    public User addUser(User user) throws UserException;
    public User getUser(Integer userId) throws UserException;
    public User updateUser(Integer id,User user) throws UserException;
    public Todo updateTodo(Integer userId,Integer todoId,Todo todo)throws UserException, TodoException;
    public Todo addTodo(Integer userId,Todo todo)throws UserException, TodoException;
    public Todo deleteTodo(Integer userId,Integer todoId)throws UserException, TodoException;
    public User deleteUser(Integer userI)throws UserException;
    public User userLogin(String email,String password)throws UserException;

}
