package ru.kata.spring.boot_security.demo.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Roles preferably start with minimum privileges
 * since if the role is specified incorrectly the first one will be installed.
 */
public enum RolesType {
    USER,
    REPAIRER,
    PHONE_MANUFACTURER,
    TV_MANUFACTURER,
    MASTER,
    ADMIN;


//    private final static List<RolesType> allRolesTypes = Arrays.stream(RolesType.values()).toList();

//    private String name;

//    RolesType(String name) {
//        this.name = name;
//    }

//    public static List<String> allRolesNames() {
//        return Arrays.stream(RolesType.values()).map(Enum::name).toList();
//    }

}
