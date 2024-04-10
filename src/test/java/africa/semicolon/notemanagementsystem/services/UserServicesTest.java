package africa.semicolon.notemanagementsystem.services;

import africa.semicolon.notemanagementsystem.data.repository.Notes;
import africa.semicolon.notemanagementsystem.data.repository.Users;
import africa.semicolon.notemanagementsystem.dtos.request.*;
import africa.semicolon.notemanagementsystem.exceptions.NoteManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServicesTest {
    @Autowired
    UserServices userServices;
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
    public void registerUserTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("user");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);
        assertEquals(1, users.count());
    }
    @Test
    public void registerNonUniqueUsersTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);
        assertEquals(1, users.count());
        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setUsername("username");
        registerRequest2.setPassword("password");
        registerRequest2.setEmail("vic@gmail.com");
        registerRequest2.setPhoneNumber("08148624687");
        try {
            userServices.register(registerRequest);
        }catch (NoteManagementException e){
            assertEquals(e.getMessage(), registerRequest2.getUsername()+" already exists");
        }
        assertEquals(1, users.count());
    }

    @Test
    public void deleteUserTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);
        assertEquals(1, users.count());
        DeleteUserRequest deleteRequest = new DeleteUserRequest();
        deleteRequest.setUsername("username");
        userServices.deleteUser(deleteRequest);
        assertEquals(0, users.count());
    }
    @Test
    public void deleteNonExistingUserTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);
        assertEquals(1, users.count());
        DeleteUserRequest deleteRequest = new DeleteUserRequest();
        deleteRequest.setUsername("wrong username");
        try {
            userServices.deleteUser(deleteRequest);
        }catch (NoteManagementException e){
            assertEquals(e.getMessage(), deleteRequest.getUsername()+ " does not exist");
        }
        assertEquals(1, users.count());

    }

    @Test
    public void userLoginTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);
        UnlockRequest unlockRequest = new UnlockRequest();
        unlockRequest.setUsername(registerRequest.getUsername());
        unlockRequest.setPassword(registerRequest.getPassword());
        userServices.unlockNotes(unlockRequest);
        assertFalse(users.findByUsername("username").isLocked());
    }

    @Test
    public void userLoginWithWrongPasswordTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);
        UnlockRequest unlockRequest = new UnlockRequest();
        unlockRequest.setUsername(registerRequest.getUsername());
        unlockRequest.setPassword("wrong password");
        try {
            userServices.unlockNotes(unlockRequest);
        }catch (NoteManagementException e){
            assertEquals(e.getMessage(), "wrong password");
        }
        assertFalse(users.findByUsername("username").isLocked());
    }

    @Test
    public void userLoginWithNonExistingUserNameTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);
        UnlockRequest unlockRequest = new UnlockRequest();
        unlockRequest.setUsername("wrong username");
        unlockRequest.setPassword("password");
        try {
            userServices.unlockNotes(unlockRequest);
        }catch (NoteManagementException e){
            assertEquals(e.getMessage(), unlockRequest.getUsername()+" does not exist");
        }
        assertFalse(users.findByUsername("username").isLocked());
    }

    @Test
    public void logoutTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);
        UnlockRequest unlockRequest = new UnlockRequest();
        unlockRequest.setUsername(registerRequest.getUsername());
        unlockRequest.setPassword(registerRequest.getPassword());
        userServices.unlockNotes(unlockRequest);
        assertFalse(users.findByUsername("username").isLocked());
        LockRequest logoutResponse = new LockRequest();
        logoutResponse.setUsername("username");
        userServices.lockNotes(logoutResponse);
        assertTrue(users.findByUsername("username").isLocked());
    }

    @Test
    public void addNoteTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);
        UnlockRequest unlockRequest = new UnlockRequest();
        unlockRequest.setUsername(registerRequest.getUsername());
        unlockRequest.setPassword(registerRequest.getPassword());
        userServices.unlockNotes(unlockRequest);
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("note content");
        addNoteRequest.setAuthor(registerRequest.getUsername());
        userServices.addNote(addNoteRequest);
        assertEquals(1, users.findByUsername(registerRequest.getUsername()).getUserNotes().size());
    }

    @Test
    public void editNoteTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);
        UnlockRequest unlockRequest = new UnlockRequest();
        unlockRequest.setUsername(registerRequest.getUsername());
        unlockRequest.setPassword(registerRequest.getPassword());
        userServices.unlockNotes(unlockRequest);
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("note content");
        addNoteRequest.setAuthor(registerRequest.getUsername());
        userServices.addNote(addNoteRequest);
        assertEquals(1, users.findByUsername(registerRequest.getUsername()).getUserNotes().size());
        EditNoteRequest editRequest = new EditNoteRequest();
        editRequest.setTitle(addNoteRequest.getTitle());
        editRequest.setNewTitle("new note title");
        editRequest.setNewContent("new note content");
        editRequest.setAuthor("username");
        userServices.editNote(editRequest);
        assertNotNull(notes.findByTitle(editRequest.getNewTitle()).getId());
        assertEquals(1, users.findByUsername("username").getUserNotes().size());

    }

    @Test
    public void deleteNoteTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);

        UnlockRequest unlockRequest = new UnlockRequest();
        unlockRequest.setUsername(registerRequest.getUsername());
        unlockRequest.setPassword(registerRequest.getPassword());
        userServices.unlockNotes(unlockRequest);

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("note content");
        addNoteRequest.setAuthor(registerRequest.getUsername());
        userServices.addNote(addNoteRequest);
        assertEquals(1, users.findByUsername(registerRequest.getUsername()).getUserNotes().size());
        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setTitle("first note");
        deleteNoteRequest.setAuthor("username");
        userServices.deleteNote(deleteNoteRequest);
        assertEquals(0, users.findByUsername("username").getUserNotes().size());
        assertEquals(0, userServices.findNotesFor("username").size());
    }

    @Test
    public void findNoteByTitleTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        registerRequest.setEmail("vic@gmail.com");
        registerRequest.setPhoneNumber("08148624687");
        userServices.register(registerRequest);

        UnlockRequest unlockRequest = new UnlockRequest();
        unlockRequest.setUsername(registerRequest.getUsername());
        unlockRequest.setPassword(registerRequest.getPassword());
        userServices.unlockNotes(unlockRequest);

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("note content");
        addNoteRequest.setAuthor(registerRequest.getUsername());
        userServices.addNote(addNoteRequest);
        assertEquals(1, users.findByUsername(registerRequest.getUsername()).getUserNotes().size());
        SearchNoteRequest noteRequest = new SearchNoteRequest();
        noteRequest.setTitle("first note");
        noteRequest.setAuthor("username");
        assertNotNull(userServices.searchForNote(noteRequest).getId());
        System.out.println(userServices.searchForNote(noteRequest));
    }

    
}