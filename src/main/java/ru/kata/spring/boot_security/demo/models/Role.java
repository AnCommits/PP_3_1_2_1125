package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.constants.RolesType;

import javax.persistence.*;
import java.util.*;
import java.util.stream.IntStream;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> user;

//    @Transient
//    public final static RolesType[] allRolesTypes = RolesType.values();

//    @Transient
//    private final static List<Role> allRoles = new ArrayList<>();

//    static {
//        Arrays.stream(allRolesTypes).map(r -> new Role(r.name())).forEach(allRoles::add);
//    }

//    public static List<Role> allRoles() {
//        return allRoles;
//    }

    public Role(String name) {
        setName(name);
    }

    public void setName(String name) {
        String nameInUpperCase = name.toUpperCase();
//        List<String> allRolesNames = RolesType.allRolesNames();
        List<String> allRolesNames = Arrays.stream(RolesType.values()).map(Enum::name).toList();
        this.name = allRolesNames.contains(nameInUpperCase) ? nameInUpperCase : allRolesNames.get(0);
    }

    @Override
    public String getAuthority() {
        return name;
    }

//    public static List<String> listOfAllRoles() {
//        return Arrays.stream(allRolesTypes).map(Enum::name).toList();
//    }

    // todo delete

//    public static List<Role> getListOfRoles(int numberOfRoles) {
//        List<Role> roles = new ArrayList<>();
//        IntStream.range(0, numberOfRoles).mapToObj(n -> new Role(allRolesTypes[n].name())).forEach(roles::add);
//        return roles;
//    }

    // todo delete

//    public static Comparator<Role> roleComparator =
//            Comparator.comparingInt(r -> RolesType.valueOf(r.getName()).ordinal());

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
