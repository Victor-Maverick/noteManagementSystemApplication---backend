package africa.semicolon.notemanagementsystem.controllers;

import africa.semicolon.notemanagementsystem.data.repository.Notes;
import africa.semicolon.notemanagementsystem.data.repository.Users;
import africa.semicolon.notemanagementsystem.dtos.request.*;
import com.mongodb.internal.bulk.UpdateRequest;
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
    @Autowired
    Notes notes;

    @BeforeEach
    public void setUp() {
        notes.deleteAll();
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

    @Test
    public void addNoteTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("user");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        controller.register(registerRequest);
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("first content");
        addNoteRequest.setAuthor("user");
        controller.addNote(addNoteRequest);
        assertEquals(1, notes.count());
    }

    @Test
    public void updateNoteTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("user");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        controller.register(registerRequest);
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("first content");
        addNoteRequest.setAuthor("user");
        controller.addNote(addNoteRequest);
        assertEquals(1, notes.count());
        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setTitle("first note");
        editNoteRequest.setNewTitle("new note title");
        editNoteRequest.setNewContent("new note content");
        editNoteRequest.setAuthor("user");
        controller.updateNote(editNoteRequest);
        assertEquals(1, notes.count());
        assertNotNull(notes.findByTitle("new note title").getId());
    }

    @Test
    public void deleteNoteTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("user");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        controller.register(registerRequest);
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("first content");
        addNoteRequest.setAuthor("user");
        controller.addNote(addNoteRequest);
        assertEquals(1, notes.count());
        DeleteNoteRequest deleteRequest = new DeleteNoteRequest();
        deleteRequest.setTitle("first note");
        deleteRequest.setAuthor("user");
        controller.deleteNote(deleteRequest);
        assertEquals(0, notes.count());
    }

}