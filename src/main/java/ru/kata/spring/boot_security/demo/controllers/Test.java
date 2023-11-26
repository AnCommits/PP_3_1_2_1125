package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.constants.RolesType;
import ru.kata.spring.boot_security.demo.dto.UserViewDto;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class Test {
    private final UserService userService;

    public Test(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("test")
    public String showAddUser(ModelMap model) {
        RolesType[] allRolesTypes = RolesType.values();
        List<Role> allRoles = new ArrayList<>();
        Arrays.stream(allRolesTypes).map(r -> new Role(r.name())).forEach(allRoles::add);
                User user = userService.getUserById(12L);
        UserViewDto ut = UserMapper.toUserViewDto(user);
        allRoles.remove(new Role("ADMIN"));

        model.addAttribute("aRoles", allRoles);
        model.addAttribute("user", ut);
        return "test";
    }

    @PostMapping("/test2")
    public String saveUser(@ModelAttribute("user") UserViewDto ut) {

        return "redirect:/test";
    }
}
