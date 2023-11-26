package ru.kata.spring.boot_security.demo.constants;

/**
 * While updating this enum the roles must preferably end with minimum privileges.
 * In case of setting an incorrect name into an instance of a role
 * the last one from this enum will be installed.
 */
public enum RolesType {
    ADMIN,
    MASTER,
    TV_MANUFACTURER,
    PHONE_MANUFACTURER,
    REPAIRER,
    USER
}
