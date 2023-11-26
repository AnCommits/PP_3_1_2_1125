package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.helper.UserViewFieldsSetter;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControllers {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private User userRepeatEdit;
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
        UserViewFieldsSetter.setAdminField(user);
        model.addAttribute("aRoles", UserViewFieldsSetter.allRolesWithoutAdmin());
        model.addAttribute("user", user);
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Редактирование пользователя");
        return "admin-edit";
    }

    @GetMapping("/show-repeat-edit-user")
    public String showRepeatEditUser(ModelMap model) {
        model.addAttribute("aRoles", UserViewFieldsSetter.allRolesWithoutAdmin());
        model.addAttribute("user", userRepeatEdit);
        model.addAttribute("message", message.toString());
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Редактирование пользователя");
        return "admin-edit";
    }

    @PutMapping("/save-user")
    public String updateUser(@ModelAttribute("user") User user) {
        message = new StringBuilder();
        long idFromForm = user.getId();
        String emailFromForm = user.getEmail();
        User userFromDb = userService.getUserByEmail(emailFromForm);
        boolean emailError = userFromDb != null && idFromForm != userFromDb.getId();
        if (emailError) {
            message.append(user.getEmail()).append(" уже зарегистрирован. Используйте другой е-мэйл.");
        }
        if (user.getRoles().isEmpty()) {
            if (!message.isEmpty()) {
                message.append("\n");
            }
            message.append("Отметьте роли.");
        }
        if (!message.isEmpty()) {
            userRepeatEdit = user;
            return "redirect:/admin/show-repeat-edit-user";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.isAdmin()) {
            user.getRoles().add(0, new Role("ADMIN"));
        }
        userService.updateUser(user);
        userRepeatEdit = null;
        return "redirect:/admin";
    }

    @GetMapping("/show-add-user")
    public String showAddUser(ModelMap model) {
        model.addAttribute("aRoles", UserViewFieldsSetter.allRolesWithoutAdmin());
        model.addAttribute("user", new User());
        model.addAttribute("title", "Страница администратора");
        model.addAttribute("title2", "Новый пользователь");
        return "admin-edit";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user) {
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
            userRepeatEdit = user;
            return "redirect:/admin/show-repeat-edit-user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        userRepeatEdit = null;
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
        return "redirect:/admin";
    }
}
