package user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void create(User user);

    Optional<User> findById(Integer id);

    List<User> findAll();

    void update(User user);

    void delete(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
