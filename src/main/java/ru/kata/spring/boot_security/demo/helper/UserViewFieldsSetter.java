package ru.kata.spring.boot_security.demo.helper;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserViewFieldsSetter {

    public static void setViewFields(List<User> users) {
        users.forEach(UserViewFieldsSetter::setViewFields);
    }

    public static void setViewFields(User user) {
        user.setBirthDateAsString(user.getBirthDate() != null
                ? new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthDate().getTime())
                : "");
        user.setRecordDateTimeAsString(user.getRecordDateTime() != null
                ? new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss").format(user.getRecordDateTime())
                : "");
        user.setFirstRole(user.getRoles().isEmpty() ? "-" : user.getRoles().get(0).getName());
        user.setOtherRoles(user.getRoles().isEmpty()
                ? new ArrayList<>()
                : user.getRoles().stream().skip(1).map(Role::getName).toList());
        user.setAdmin(user.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN")));
    }
}
