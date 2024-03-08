package ru.dorofeev.graphqlapplication.model.enums;

/**
 * Статус аккаунта пользователя
 */
public enum UserStatusType {

    /**
     * Статус 'Создан'
     */
    CREATED,

    /**
     * Статус 'Удален'
     */
    DELETED,

    /**
     * Статус 'Активный'
     */
    ACTIVE,

    /**
     * Статус 'Не активный'
     */
    NOT_ACTIVE,

    /**
     * Статус 'Забанен'
     */
    BANNED,

    /**
     * Статус 'Гость'
     */
    GUEST
}
