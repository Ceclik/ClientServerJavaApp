package Entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testGetters() {
        User user = new User(1L, "testLogin", "testPassword", "John Doe", "john@example.com", "123 Main St", 'u');

        assertEquals(1L, user.getId());
        assertEquals("testLogin", user.getLogin());
        assertEquals("testPassword", user.getPassword());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("123 Main St", user.getAdress());
        assertEquals('u', user.getRole());
    }

    @Test
    public void testCopyConstructor() {
        User originalUser = new User(1L, "testLogin", "testPassword", "John Doe", "john@example.com", "123 Main St", 'u');
        User copiedUser = new User(originalUser);

        assertEquals(originalUser.getId(), copiedUser.getId());
        assertEquals(originalUser.getLogin(), copiedUser.getLogin());
        assertEquals(originalUser.getPassword(), copiedUser.getPassword());
        assertEquals(originalUser.getName(), copiedUser.getName());
        assertEquals(originalUser.getEmail(), copiedUser.getEmail());
        assertEquals(originalUser.getAdress(), copiedUser.getAdress());
        assertEquals(originalUser.getRole(), copiedUser.getRole());
    }
}
