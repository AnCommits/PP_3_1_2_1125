package ru.kata.spring.boot_security.demo.mapper;

import ru.kata.spring.boot_security.demo.dto.UserViewDto;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserMapper {

    public static UserViewDto toUserViewDto(User user) {
        UserViewDto userViewDto = new UserViewDto();

        userViewDto.setId(user.getId());
        userViewDto.setFirstName(user.getFirstName());
        userViewDto.setLastName(user.getLastName());
        userViewDto.setPosition(user.getPosition());
        userViewDto.setEmail(user.getEmail());
        userViewDto.setPassword("");
        userViewDto.setBirthDate(user.getBirthDate());
        userViewDto.setRecordDateTime(user.getRecordDateTime());
        userViewDto.setLocked(user.isLocked());

        userViewDto.setBirthDateAsString(user.getBirthDate() != null
                ? new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthDate().getTime())
                : "");
        userViewDto.setRecordDateTimeAsString(user.getRecordDateTime() != null
                ? new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss").format(user.getRecordDateTime())
                : "");

            if (user.getRoles().isEmpty()) {
                userViewDto.setFirstRole("-");
                userViewDto.setOtherRoles(new ArrayList<>());
            } else {
                userViewDto.setFirstRole(user.getRoles().get(0).getName());
                userViewDto.setOtherRoles(user.getRoles().stream().skip(1).map(Role::getName).toList());
            }

//            if (user.getPositions().isEmpty()) {
//                userViewDto.setFirstPosition("-");
//                userViewDto.setOtherPositions(new ArrayList<>());
//            } else {
//                userViewDto.setFirstPosition(user.getPositions().get(0).getName());
//                userViewDto.setOtherPositions(user.getPositions().stream().skip(1).map(Position::getName).toList());
//            }

            return userViewDto;
        }
    }
