package com.perdijimen.bethabank.controller;

import com.perdijimen.bethabank.model.User;
import com.perdijimen.bethabank.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Rest controller of users
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> userListFilter(@RequestParam(name="name", required = false) String name,
                                     @RequestParam(name="limit", required = false, defaultValue = "5") Integer limit,
                                     @RequestParam(name="page", required = false, defaultValue = "0") Integer page)  {

        if (name != null) {
            return this.userService.findAllByName(name, limit, page) ;
        }
        return this.userService.findAll(limit, page);
    }

    @GetMapping("/users/{id}")
    public Optional<User> userFilterById(@PathVariable Long id)  {

        return this.userService.findById(id);
    }

    /**
     * It saves an user and returns the user created with id
     * @param user New user
     * @return User created
     */
    @PostMapping("/users")
    @ApiOperation("Guarda en base de datos un usuario nuevo")
    public ResponseEntity<User> createUser(@ApiParam("Objeto usuario nuevo")
                                 @RequestBody User user) throws URISyntaxException {

        User userDB = userService.createUser(user);

        return userDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                                    ResponseEntity.created(new URI("/api/users/" + userDB.getId())).body(userDB);
    }

    /**
     * It updates an user
     * @param user User to update
     * @return Response of update user
     */
    @PutMapping("/users")
    @ApiOperation("Actualiza en base de datos un usuario existente")
    public ResponseEntity<User> updateUser(@ApiParam("Información del usuario") @RequestBody User user) {

        User userDB = userService.updateUser(user);

        return userDB == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                            ResponseEntity.ok().body(userDB);
    }
}
