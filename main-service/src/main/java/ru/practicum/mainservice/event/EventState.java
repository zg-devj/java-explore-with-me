package ru.practicum.mainservice.event;

/**
 * Список состояний жизненного цикла события
 * PENDING - в ожидании
 * PUBLISHED - опубликованный
 * CANCELED - отмененный события
 */
public enum EventState {
    PENDING, PUBLISHED, CANCELED
}
