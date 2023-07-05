package ru.practicum.mainservice.user;

import ru.practicum.mainservice.user.dto.NewUserRequest;
import ru.practicum.mainservice.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findUsers(List<Long> ids, int from, int size);

    UserDto createUser(NewUserRequest newUserRequest);

    void deleteUser(long userId);
}
