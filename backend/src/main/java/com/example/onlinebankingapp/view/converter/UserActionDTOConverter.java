package com.example.onlinebankingapp.view.converter;

import com.example.onlinebankingapp.model.entities.UserAction;
import com.example.onlinebankingapp.view.dto.UserActionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserActionDTOConverter {

    @Autowired
    ModelMapper modelMapper;


    public UserActionDTO convertToDto(UserAction userAction) {
        UserActionDTO userActionDTO = modelMapper.map(userAction, UserActionDTO.class);
        return userActionDTO;
    }

    public UserAction convertToEntity(UserActionDTO userActionDTO) {
        UserAction userAction = modelMapper.map(userActionDTO, UserAction.class);
        return userAction;
    }


}
