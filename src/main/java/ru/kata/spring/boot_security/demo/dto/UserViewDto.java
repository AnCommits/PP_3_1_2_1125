package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
public class UserViewDto {
    private long id;
    private String firstName;
    private String lastName;
    private String position;
    private String email;
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar birthDate;
    private String birthDateAsString;

    @Temporal(TemporalType.TIMESTAMP)
    private Date recordDateTime;
    private String recordDateTimeAsString;

    private List<Role> roles;

    private String firstRole;
    private List<String> otherRoles;

    private boolean locked;

    private boolean admin;
}
