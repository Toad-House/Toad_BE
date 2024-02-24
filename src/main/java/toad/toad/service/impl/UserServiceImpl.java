package toad.toad.service.impl;

import org.springframework.stereotype.Service;
import toad.toad.data.entity.User;
import toad.toad.repository.UserRepository;
import toad.toad.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserById(int userId) {
        return userRepository.findById(userId);
    }

}
