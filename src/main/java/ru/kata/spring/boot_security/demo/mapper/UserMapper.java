package ru.kata.spring.boot_security.demo.mapper;

import ru.kata.spring.boot_security.demo.constants.RolesType;
import ru.kata.spring.boot_security.demo.dto.UserViewDto;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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


//        List<Role> roles = new ArrayList<>();

//        IntStream.range(0, 4).mapToObj(n -> new Role(RolesType.values()[n].name())).forEach(roles::add);

//        List<String> roles = new ArrayList<>(RolesType.allRolesNames());
//        roles.remove(new Role("ADMIN"));
        userViewDto.setRoles(user.getRoles());

        if (user.getRoles().isEmpty()) {
            userViewDto.setFirstRole("-");
            userViewDto.setOtherRoles(new ArrayList<>());
        } else {
            userViewDto.setFirstRole(user.getRoles().get(0).getName());
            userViewDto.setOtherRoles(user.getRoles().stream().skip(1).map(Role::getName).toList());
        }

        userViewDto.setAdmin(user.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN")));

        return userViewDto;
    }
}
