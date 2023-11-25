package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
//@RequiredArgsConstructor
public class UserViewDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date recordDateTime;

    private String firstPosition;
    private List<String> otherPositions;

    private String firstRole;
    private List<String> otherRoles;

    private boolean locked;

    public String birthDateAsString;

    public String recordDateTimeAsString;

    // todo delete

    @Override
    public String toString() {
        return "User{" + id + ' ' + firstName + ' ' + lastName + ' ' + email + '}';
    }

    // todo delete

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
