package ru.practicum.mainservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.exceptions.NotFoundException;
import ru.practicum.mainservice.exceptions.ObjectAlreadyExistException;
import ru.practicum.mainservice.user.dto.NewUserRequest;
import ru.practicum.mainservice.user.dto.UserDto;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> findUsers(List<Long> ids, int from, int size) {
        return null;
    }

    @Override
    @Transactional
    public UserDto createUser(NewUserRequest newUserRequest) {
        User newUser = UserMapper.newUserRequestToUser(newUserRequest);
        try {
            User savedUser = userRepository.save(newUser);
            return UserMapper.userToUserDto(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new ObjectAlreadyExistException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteUser(long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("Пользователь c id=%d не найден", userId));
        }
    }
}
