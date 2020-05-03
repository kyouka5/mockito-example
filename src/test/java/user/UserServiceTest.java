package user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class UserServiceTest {
    private UserService underTest;

    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        underTest = new UserService(userRepository);
    }

    @Test
    public void testCreateUserShouldCreateTheInputUserWhenTheInputIsValid() {
        // Given
        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn(1);
        Mockito.when(user.getUsername()).thenReturn("testuser");
        Mockito.when(user.getEmail()).thenReturn("user@test.test");
        // When
        underTest.createUser(user);
        // Then
        Mockito.verify(userRepository).create(user);
        Mockito.verify(userRepository).existsByUsername("testuser");
        Mockito.verify(userRepository).existsByEmail("user@test.test");
        Mockito.verify(user, Mockito.times(2)).getUsername();
        Mockito.verify(user, Mockito.times(2)).getEmail();
        Mockito.verifyNoMoreInteractions(userRepository, user);
    }

    @Test
    public void testCreateUserShouldThrowIllegalArgumentExceptionWhenTheInputIsNull() {
        // Given
        User user = null;
        // When
        try {
            underTest.createUser(user);
        } catch (IllegalArgumentException e) {
            assertEquals("User cannot be null", e.getMessage());
        } catch (Exception e) {
            fail();
        }
        // Then
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testCreateUserShouldThrowIllegalArgumentExceptionWhenTheInputUsernameIsBlank(){
        // Given
        User user = Mockito.mock(User.class);
        Mockito.when(user.getUsername()).thenReturn("   ");
        // When
        try {
            underTest.createUser(user);
        } catch (IllegalArgumentException e) {
            assertEquals("Username cannot be empty", e.getMessage());
        } catch (Exception e) {
            fail();
        }
        // Then
        Mockito.verify(user, Mockito.times(1)).getUsername();
        Mockito.verifyNoMoreInteractions(userRepository, user);
    }

    @Test
    public void testFindUserShouldFindTheUserWithTheGivenValidId() {
        // Given
        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn(1);
        Mockito.when(user.getUsername()).thenReturn("testuser");
        Mockito.when(user.getEmail()).thenReturn("user@test.test");
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        // When
        underTest.findUserById(1);
        // Then
        Mockito.verify(userRepository).findById(1);
        Mockito.verifyNoMoreInteractions(userRepository, user);
    }
}
