package com.todo.service.impl;

import com.todo.exception.TodoException;
import com.todo.exception.UserException;
import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.repository.TodoRepo;
import com.todo.repository.UserRepo;
import com.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TodoRepo todoRepo;
    @Override
    public User addUser(User user) throws UserException {
        if(user.getId()!=null){
            Optional<User> user1=userRepo.findById(user.getId());
            if(user1.isPresent())throw new UserException("User already present");

        }
        Optional<User> user2=userRepo.findByEmail(user.getEmail());
        if(user2.isPresent())throw new UserException(user.getEmail()+" already registered!");
        return userRepo.save(user);
    }

    @Override
    public User getUser(Integer userId) throws UserException {

        Optional<User> user1=userRepo.findById(userId);
        if(user1.isEmpty())throw new UserException("User not found");

        return user1.get();
    }

    @Override
    public User updateUser(Integer id,User user) throws UserException {

            Optional<User> user1=userRepo.findById(id);
            if(user1.isEmpty())throw new UserException("User not found");
            user.setId(id);
            User user2=user1.get();
            if(user.getUserName()==null)user.setUserName(user2.getUserName());
        if(user.getPassword()==null)user.setPassword(user2.getPassword());
        if(user.getEmail()==null)user.setEmail(user2.getEmail());
        user.setTodoList(user2.getTodoList());


        return userRepo.save(user);

    }

    @Override
    public Todo updateTodo(Integer userId, Integer todoId,Todo todo) throws UserException, TodoException {
        User user=getUser(userId);
        Optional<Todo> todo1=todoRepo.findById(todoId);

        if(todo1.isEmpty() || todo1.get().getUser().getId()!=userId)throw new TodoException("No todo found for the id: "+todoId+" and userId: "+userId);
        todo.setTodoId(todoId);
        todo.setUser(user);
        return todoRepo.save(todo);
    }

    @Override
    public Todo addTodo(Integer userId, Todo todo) throws UserException, TodoException {
        User user=getUser(userId);
        if(todo.getTodoId()!=null){
            Optional<Todo> todo1=todoRepo.findById(todo.getTodoId());
            if(todo1.isPresent())throw new TodoException("todo with id:"+todo.getTodoId()+" already present");
        }
        todo.setUser(user);
        return todoRepo.save(todo);
    }

    @Override
    public Todo deleteTodo(Integer userId, Integer todoId) throws UserException, TodoException {
        User user=getUser(userId);
        Optional<Todo> todo1=todoRepo.findById(todoId);
        if(todo1.isEmpty() || todo1.get().getUser().getId()!=userId)throw new TodoException("No todo found for the id: "+todoId+" and userId: "+userId);
        Todo todo=todo1.get();
        user.getTodoList().remove(todo);
        todo.setUser(user);
        todoRepo.delete(todo);
        return todo;
    }

    @Override
    public User deleteUser(Integer userId) throws UserException {
        User user=getUser(userId);
        userRepo.delete(user);
        return user;
    }

    @Override
    public User userLogin(String email, String password) throws UserException {
        Optional<User> user1=userRepo.findByEmailAndPassword(email, password);
        if(user1.isEmpty())throw new UserException("Invalid Credentials!");

        return user1.get();
    }
}
