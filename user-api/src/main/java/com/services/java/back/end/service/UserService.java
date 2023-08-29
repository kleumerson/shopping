package com.services.java.back.end.service;

import com.services.java.back.end.clientDto.UserDto;
import com.services.java.back.end.converter.ConverterDto;
import com.services.java.back.end.model.User;
import com.services.java.back.end.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${USER_API_URL:http://localhost:8080}")
    private String userApiURL;

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAll() {
        List<User> usuarios = userRepository.findAll();
        return usuarios
                .stream()
                .map(ConverterDto::convert)
                .collect(Collectors.toList());
    }

    public UserDto findById(long userId) {
        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ConverterDto.convert(usuario);

    }

    public UserDto save(UserDto userDTO) {
        userDTO.setDataCadastro(LocalDateTime.now());
        userDTO.setKey(UUID.randomUUID().toString());
        User user = userRepository.save(User.convert(userDTO));
        return ConverterDto.convert(user);
    }

    public UserDto delete(long userId) {
        User user = userRepository
                .findById(userId).orElseThrow(() -> new RuntimeException());
        userRepository.delete(user);
        return ConverterDto.convert(user);

    }

    public UserDto findByCpfAndKey(String cpf, String key) {
        User user = userRepository.findByCpfAndKey(cpf, key);
        if (user != null) {
            return ConverterDto.convert(user);
        }
        //throw new UserNotFoundException();
        throw new IllegalArgumentException();
    }

    public List<UserDto> queryByName(String name) {
        List<User> usuarios = userRepository.queryByNomeLike(name);
        return usuarios
                .stream()
                .map(ConverterDto::convert)
                .collect(Collectors.toList());
    }

    public UserDto editUser(Long userId, UserDto userDTO) {

        User user = userRepository
                .findById(userId).orElseThrow(() -> new RuntimeException());

        if (userDTO.getEmail() != null &&
                !user.getEmail().equals(userDTO.getEmail())) {
            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getTelefone() != null &&
                !user.getTelefone().equals(userDTO.getTelefone())) {
            user.setTelefone(user.getTelefone());
        }

        if (userDTO.getEndereco() != null &&
                !user.getEndereco().equals(userDTO.getEndereco())) {
            user.setTelefone(user.getTelefone());
        }

        user = userRepository.save(user);
        return ConverterDto.convert(user);

    }

    public Page<UserDto> getAllPage(Pageable page) {
        Page<User> users = userRepository.findAll(page);
        return users
                .map(ConverterDto::convert);
    }

    public UserDto getUserByCpf(String cpf, String key) {
        try {
            WebClient webClient = WebClient.builder()
                    .baseUrl(userApiURL)
                    .build();
            Mono<UserDto> userDTOMono = webClient.get()
                    .uri("/user" + cpf + "/cpf?key=" + key)
                    .retrieve()
                    .bodyToMono(UserDto.class);
            return userDTOMono.block();
        } catch (Exception e) {
            //throw new UserNotFoundException();
            throw new IllegalArgumentException();
        }
    }
}
