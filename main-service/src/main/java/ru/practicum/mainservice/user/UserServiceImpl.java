package ru.practicum.mainservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.exceptions.ConflictException;
import ru.practicum.mainservice.exceptions.NotFoundException;
import ru.practicum.mainservice.user.criteria.UserSpecs;
import ru.practicum.mainservice.user.dto.NewUserRequest;
import ru.practicum.mainservice.user.dto.UserDto;

import java.util.List;

import static ru.practicum.mainservice.utils.Util.getPageRequest;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> findUsers(List<Long> ids, int from, int size) {
        PageRequest pageRequest = getPageRequest(from, size);
        return UserMapper.userToUserDto(userRepository.findAll(UserSpecs.isIdsIn(ids), pageRequest).toList());
    }

    @Override
    @Transactional
    public UserDto createUser(NewUserRequest newUserRequest) {
        User newUser = UserMapper.newUserRequestToUser(newUserRequest);
        try {
            User savedUser = userRepository.save(newUser);
            return UserMapper.userToUserDto(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("The integrity constraint has been violated.", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format("User with id=%d not found.", userId));
        }
    }
}
