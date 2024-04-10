package africa.semicolon.notemanagementsystem.controllers;

import africa.semicolon.notemanagementsystem.data.repository.Users;
import africa.semicolon.notemanagementsystem.dtos.request.LockRequest;
import africa.semicolon.notemanagementsystem.dtos.request.RegisterRequest;
import africa.semicolon.notemanagementsystem.dtos.request.UnlockRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    UserController controller;
    @Autowired
    Users users;

    @BeforeEach
    public void setUp() {
        users.deleteAll();
    }

    @Test
    public void registerTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("user");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        controller.register(registerRequest);
        assertEquals(1, users.count());
    }
    @Test
    public void lockNotesTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("user");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        controller.register(registerRequest);
        LockRequest lockRequest = new LockRequest();
        lockRequest.setUsername(registerRequest.getUsername());
        controller.lockNotes(lockRequest);
        assertTrue(users.findByUsername(registerRequest.getUsername()).isLocked());
    }

    @Test
    public void unlockNotesTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("user");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        controller.register(registerRequest);
        LockRequest lockRequest = new LockRequest();
        lockRequest.setUsername(registerRequest.getUsername());
        controller.lockNotes(lockRequest);
        assertTrue(users.findByUsername(registerRequest.getUsername()).isLocked());
        UnlockRequest unlockRequest = new UnlockRequest();
        unlockRequest.setUsername("user");
        unlockRequest.setPassword("password");
        controller.unlockNotes(unlockRequest);
        assertFalse(users.findByUsername(registerRequest.getUsername()).isLocked());
    }
    
}