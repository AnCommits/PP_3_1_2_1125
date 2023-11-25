package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.dto.UserViewDto;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.models.User;

@Controller
@RequestMapping("/user")
public class UserControllers {

    @GetMapping
    public String showUser(ModelMap model, Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        UserViewDto user = UserMapper.toUserViewDto(principal);
        model.addAttribute("title", "Моя страница");
        model.addAttribute("user", user);
        return "user";
    }
}
