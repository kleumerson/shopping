package com.services.java.back.end.controller;

import com.services.java.back.end.clientDto.UserDto;
import com.services.java.back.end.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    private static List<UserDto> usuarios = new ArrayList<UserDto>();

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initiateList() {

    }

    @GetMapping
    public List<UserDto> getUser() {
        return userService.getAll();
    }

    @GetMapping("/{cpf}/cpf?key={key}")
    public UserDto findByCpf(
            @PathVariable String cpf,
            @RequestParam(name = "key", required = true) String key) {
        return userService.findByCpfAndKey(cpf, key);
    }

    @GetMapping("/{cpf}")
    public UserDto getUsersFiltro(@PathVariable String cpf) {
        return usuarios
                .stream()
                .filter(UserDTO -> UserDTO.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto inserir(@RequestBody @Valid UserDto userDTO) {
        return userService.save(userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws UserPrincipalNotFoundException {
        userService.delete(id);
    }

    @PatchMapping("{id}")
    public UserDto editUser(@PathVariable long id,
                            @RequestBody UserDto userDTO) {
        return userService.editUser(id, userDTO);
    }

    @GetMapping("/search")
    public List<UserDto> queryByName(
            @RequestParam(name = "nome", required = true) String nome) {
        return userService.queryByName(nome);
    }

    @GetMapping("/pageable")
    public Page<UserDto> getUserPage(Pageable pageable) {
        return userService.getAllPage(pageable);
    }
}
