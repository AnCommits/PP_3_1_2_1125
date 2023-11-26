package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.constants.RolesType;
import ru.kata.spring.boot_security.demo.dto.UserViewDto;
//import ru.kata.spring.boot_security.demo.helper.RolesForView;
import ru.kata.spring.boot_security.demo.helper.UserViewFieldsSetter;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControllers {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private UserViewDto userToRepeatEdit;
    private StringBuilder message;

    public AdminControllers(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        UserViewFieldsSetter.setViewFields(users);
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/show-edit-user")
    public String showEditUser(@RequestParam long id, ModelMap model) {
        User user = userService.getUserById(id);
//        UserViewDto userViewDto = UserMapper.toUserViewDto(user);
//
//        RolesType[] allRolesTypes = RolesType.values();
//        List<Role> allRoles = new ArrayList<>();
//            Arrays.stream(allRolesTypes).map(r -> new Role(r.name())).forEach(allRoles::add);

        RolesType[] allRolesTypes = RolesType.values();
        List<Role> allRoles = new ArrayList<>();
        Arrays.stream(allRolesTypes).map(r -> new Role(r.name())).forEach(allRoles::add);
//        User user = userService.getUserById(12L);
        UserViewDto userViewDto = UserMapper.toUserViewDto(user);
        allRoles.remove(new Role("ADMIN"));

        model.addAttribute("aRoles", allRoles);
        model.addAttribute("user", userViewDto);
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Редактирование пользователя");
        return "admin-edit";
    }

    @GetMapping("/show-repeat-edit-user")
    public String showRepeatEditUser(ModelMap model) {

        RolesType[] allRolesTypes = RolesType.values();
        List<Role> allRoles = new ArrayList<>();
        Arrays.stream(allRolesTypes).map(r -> new Role(r.name())).forEach(allRoles::add);

        model.addAttribute("aRoles", allRoles);
        model.addAttribute("user", userToRepeatEdit);
        model.addAttribute("message", message.toString());
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Редактирование пользователя");
        return "admin-edit";
    }

    @PutMapping("/save-user")
    public String updateUser(@ModelAttribute("user") UserViewDto user) {
        message = new StringBuilder();
        long idFromForm = user.getId();
        String emailFromForm = user.getEmail();
        User userFromDb = userService.getUserByEmail(emailFromForm);
        boolean emailError = userFromDb != null && idFromForm != userFromDb.getId();
        if (emailError) {
            message.append(user.getEmail()).append(" уже зарегистрирован. Используйте другой е-мэйл.");
        }
        if (/*user.getRoles().isEmpty()*/ true) {
            if (!message.isEmpty()) {
                message.append("\n");
            }
            message.append("Отметьте роли.");
        }
        if (!message.isEmpty()) {
            userToRepeatEdit = user;
            return "redirect:/admin/show-repeat-edit-user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/show-add-user")
    public String showAddUser(ModelMap model) {
//        List<Role> roles = Role.allRoles();
//        roles.remove(new Role("ADMIN"));
//        model.addAttribute("aRoles", roles);

        RolesType[] allRolesTypes = RolesType.values();
        List<Role> allRoles = new ArrayList<>();
        Arrays.stream(allRolesTypes).map(r -> new Role(r.name())).forEach(allRoles::add);

        model.addAttribute("aRoles", allRoles);
        model.addAttribute("user", new UserViewDto());
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Новый пользователь");
        return "admin-edit";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") UserViewDto user) {
        message = new StringBuilder();
        String emailFromForm = user.getEmail();
        User userFromDb = userService.getUserByEmail(emailFromForm);
        boolean emailError = userFromDb != null;
        if (emailError) {
            message.append(user.getEmail()).append(" уже зарегистрирован. Используйте другой е-мэйл.");
        }
        if (user.getRoles().isEmpty()) {
            if (!message.isEmpty()) {
                message.append("\n");
            }
            message.append("Выберите роли.");
        }
        if (!message.isEmpty()) {
            userToRepeatEdit = user;
            return "redirect:/admin/show-repeat-edit-user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/change-ban/{id}")
    public String changeUserBan(@PathVariable long id) {
        User user = userService.getUserById(id);
        user.setLocked(!user.isLocked());
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/remove-user/{id}")
    public String removeUser(@PathVariable long id) {
        userService.removeUserById(id);
        userToRepeatEdit = null;
        return "redirect:/admin";
    }
}
