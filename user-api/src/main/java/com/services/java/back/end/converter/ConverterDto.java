package com.services.java.back.end.converter;

import com.services.java.back.end.clientDto.UserDto;
import com.services.java.back.end.model.User;

public class ConverterDto {

    public static UserDto convert(User user){
        UserDto userDTO = new UserDto();
        userDTO.setNome(user.getNome());
        userDTO.setCpf(user.getCpf());
        userDTO.setEndereco(user.getEndereco());
        userDTO.setEmail(user.getEmail());
        userDTO.setTelefone(user.getTelefone());
        userDTO.setDataCadastro(user.getDataCadastro());
        userDTO.setKey(user.getKey());

        return userDTO;
    }
}
