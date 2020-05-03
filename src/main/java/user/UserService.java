package user;

import java.util.Optional;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email address cannot be empty");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("This username is taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("This email is already in use");
        }

        userRepository.create(user);
    }

    public User findUserById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        return user.get();
    }


    public void deleteUserById(Integer id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public void updateEmail(Integer id, String newEmail) {
        User user = findUserById(id);
        if (newEmail.isBlank()) {
            throw new IllegalArgumentException("Email address cannot be empty");
        }
        if (user.getEmail().equals(newEmail)) {
            throw new IllegalArgumentException("Email address did not change");
        }
        user.setEmail(newEmail);
        userRepository.update(user);
    }


}
