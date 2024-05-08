package com.example.myapp.utils;

import com.example.myapp.dto.full.UserFullDto;
import com.example.myapp.dto.update.UserUpdateDto;
import com.example.myapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMappingUtils {
    public UserFullDto userToUserFull(User user) {
        return UserFullDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .incomes(user.getIncomes())
                .expenses(user.getExpenses())
                .lastUpdateTime(user.getLastUpdateTime())
                .creationTime(user.getCreationTime())
                .build();
    }
    public User userFullToUser(UserFullDto userFullDto) {
        return User.builder()
                .id(userFullDto.getId())
                .name(userFullDto.getName())
                .email(userFullDto.getEmail())
                .password(userFullDto.getPassword())
                .incomes(userFullDto.getIncomes())
                .expenses(userFullDto.getExpenses())
                .build();
    }

    public User userUpdateToUser(UserUpdateDto userFullDto) {
        return User.builder()
                .name(userFullDto.getName())
                .email(userFullDto.getEmail())
                .password(userFullDto.getPassword())
                .incomes(userFullDto.getIncomes())
                .expenses(userFullDto.getExpenses())
                .build();
    }

    public UserFullDto userToUserUpdate(User user) {
        return UserFullDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .incomes(user.getIncomes())
                .expenses(user.getExpenses())
                .build();
    }
}
