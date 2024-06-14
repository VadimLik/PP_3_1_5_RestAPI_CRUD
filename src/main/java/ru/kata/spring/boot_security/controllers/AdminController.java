package ru.kata.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.models.Role;
import ru.kata.spring.boot_security.models.User;
import ru.kata.spring.boot_security.service.RoleService;
import ru.kata.spring.boot_security.service.UsersService;
import ru.kata.spring.boot_security.util.UserErrorResponce;
import ru.kata.spring.boot_security.util.UserNotCreatedException;
import ru.kata.spring.boot_security.util.UserNotFoundException;
import ru.kata.spring.boot_security.util.UserUpdateException;
import ru.kata.spring.boot_security.validators.UserValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class AdminController {

    private final UsersService usersService;

    private final RoleService roleService;

    private final UserValidator userValidator;

    @Autowired
    public AdminController(UsersService usersService, RoleService roleService, UserValidator userValidator) {

        this.usersService = usersService;

        this.roleService = roleService;

        this.userValidator = userValidator;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {

        System.out.println("Начало метода getAllUsers");

        ResponseEntity<List<User>> responseEntity = new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);

        return responseEntity;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {

        System.out.println("Начало метода getUser");

        ResponseEntity<User> response = new ResponseEntity<>(usersService.getUser(id), HttpStatus.OK);

        return response;
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {

        System.out.println("Начало метода createUser");

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {

            System.out.println("Продолжение метода createUser в теле обработки ошибки");

            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {

                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new UserNotCreatedException(errorMsg.toString());

        } else {

            usersService.saveUser(user);

            System.out.println("Конец метода createUser");

            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @PutMapping("/users")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {

        System.out.println("Начало метода updateUser");

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {

            System.out.println("Продолжение метода updateUser в теле обработки ошибки");

            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {

                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new UserUpdateException(errorMsg.toString());

        } else {

            usersService.updateUser(user, user.getId());

            System.out.println("Конец метода updateUser");

            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {

        System.out.println("Начало метода deleteUser");

        usersService.deleteUser(id);

        System.out.println("Конец метода deleteUser");

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/users/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {

        System.out.println("Начало метода getAllRoles");

        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponce> handleException(UserNotFoundException e) {

        System.out.println("Начало метода handleException");

        UserErrorResponce responce = new UserErrorResponce("Человек с таким id не был найден");

        System.out.println("Конец метода handleException");

        return new ResponseEntity<>(responce, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponce> handleException(UserNotCreatedException e) {

        System.out.println("Начало метода handleException, исключение UserNotCreatedException");

        UserErrorResponce responce = new UserErrorResponce(e.getMessage());

        System.out.println("Конец метода handleException, исключение UserNotCreatedException");

        return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponce> handleException(UserUpdateException e) {

        System.out.println("Начало метода handleException, исключение UserUpdateException");

        UserErrorResponce responce = new UserErrorResponce(e.getMessage());

        System.out.println("Конец метода handleException, исключение UserUpdateException");

        return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);

    }
}
