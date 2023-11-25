package ru.kata.spring.boot_security.demo.init;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Position;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class InitDataBase {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InitDataBase(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method creates and puts in the table an admin in case there are no entries in the table.
     */
    @PostConstruct
    public void initUsers() {
        if (userService.countUsers() == 0) {
            initAdmin();

            initTrainee();
            initRepairer1();
            initRepairer2();

            initTvManufacturer1();
            initTvManufacturer2();

            initPhoneManufacturer1();
            initPhoneManufacturer2();

            initTvManufactureMaster();
            initPhoneManufactureMaster();

            initRepairMaster();

            initManufactureBoss();
            initHead();
        }
    }

    public void initAdmin() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        roles.add(new Role("USER"));

        User user = new User(null, null,
                "1", passwordEncoder.encode("1"),
                null, roles, null, false);
        userService.saveUser(user);
    }

    public void initTrainee() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("ученик"));

        User user = new User("Антон", "Антонов",
                "a", passwordEncoder.encode("a"),
                null, roles, positions, false);
        userService.saveUser(user);
    }

    public void initRepairer1() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("ремонтник"));

        User user = new User("Борис", "Борисов",
                "b", passwordEncoder.encode("b"),
                new GregorianCalendar(2000, Calendar.JANUARY, 01),
                roles, positions, false);
        userService.saveUser(user);
    }

    public void initRepairer2() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("ремонтник"));

        User user = new User("Вася", "Васильев",
                "v", passwordEncoder.encode("v"),
                new GregorianCalendar(2000, Calendar.JANUARY, 01),
                roles, positions, false);
        userService.saveUser(user);
    }

    public void initTvManufacturer1() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("сборщик(ТВ)"));

        User user = new User("Григорий", "Григорьев",
                "g", passwordEncoder.encode("g"),
                new GregorianCalendar(2000, Calendar.JANUARY, 01),
                roles, positions, false);
        userService.saveUser(user);
    }

    public void initTvManufacturer2() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("настройщик(ТВ)"));

        User user = new User("Дима", "Дмитриев",
                "d", passwordEncoder.encode("d"),
                new GregorianCalendar(2000, Calendar.JANUARY, 01),
                roles, positions, false);
        userService.saveUser(user);
    }

    public void initPhoneManufacturer1() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("сборщик(тел)"));

        User user = new User("Егор", "Егоров",
                "e", passwordEncoder.encode("e"),
                new GregorianCalendar(2000, Calendar.JANUARY, 01),
                roles, positions, false);
        userService.saveUser(user);
    }

    public void initPhoneManufacturer2() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("настройщик(тел)"));

        User user = new User("Зина", "Зиновьева",
                "z", passwordEncoder.encode("z"),
                new GregorianCalendar(2000, Calendar.JANUARY, 01),
                roles, positions, false);
        userService.saveUser(user);
    }

    public void initTvManufactureMaster() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("TV_MANUFACTURE_MASTER"));
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("нач отд произв ТВ"));

        User user = new User("Кирилл", "Кириллов",
                "k", passwordEncoder.encode("k"),
                new GregorianCalendar(2000, Calendar.JANUARY, 01),
                roles, positions, false);
        userService.saveUser(user);
    }

    public void initPhoneManufactureMaster() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("PHONE_MANUFACTURE_MASTER"));
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("нач отд произв тел"));

        User user = new User("Миша", "Михайлов",
                "m", passwordEncoder.encode("m"),
                new GregorianCalendar(2000, Calendar.JANUARY, 01),
                roles, positions, false);
        userService.saveUser(user);
    }

    public void initRepairMaster() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("REPAIR_MASTER"));
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("нач ремонт отдела"));

        User user = new User("Петр", "Петров",
                "p", passwordEncoder.encode("p"),
                new GregorianCalendar(2000, Calendar.JANUARY, 01),
                roles, positions, false);
        userService.saveUser(user);
    }

    public void initManufactureBoss() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("TV_MANUFACTURE_MASTER"));
        roles.add(new Role("PHONE_MANUFACTURE_MASTER"));
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("нач производства"));

        User user = new User("Рома", "Романов",
                "r", passwordEncoder.encode("r"),
                new GregorianCalendar(2000, Calendar.JANUARY, 01),
                roles, positions, false);
        userService.saveUser(user);
    }

    public void initHead() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("TV_MANUFACTURE_MASTER"));
        roles.add(new Role("PHONE_MANUFACTURE_MASTER"));
        roles.add(new Role("REPAIR_MASTER"));
        roles.add(new Role("USER"));

        List<Position> positions = new ArrayList<>();
        positions.add(new Position("глава фирмы"));

        User user = new User("Степан", "Степанов",
                "s", passwordEncoder.encode("s"),
                new GregorianCalendar(2000, Calendar.JANUARY, 01),
                roles, positions, false);
        userService.saveUser(user);
    }
}
