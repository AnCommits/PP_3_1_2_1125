package ru.kata.spring.boot_security.demo.constants;

import java.util.Arrays;
import java.util.List;

/**
 * While updating this enum the roles must preferably end with minimum privileges.
 * In case of trying to set an incorrect name into an instance of a role
 * the last one from this enum will be installed.
 */
public enum RolesType {
    ADMIN,
    MASTER,
    TV_MANUFACTURER,
    PHONE_MANUFACTURER,
    REPAIRER,
    USER;

    public static final List<String> allRolesNames = Arrays.stream(RolesType.values()).map(Enum::name).toList();
}
