package toad.toad.service;


import toad.toad.data.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(int userId);
}
