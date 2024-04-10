package africa.semicolon.notemanagementsystem.services;

import africa.semicolon.notemanagementsystem.data.repository.Notes;
import africa.semicolon.notemanagementsystem.dtos.request.AddNoteRequest;
import africa.semicolon.notemanagementsystem.dtos.request.DeleteNoteRequest;
import africa.semicolon.notemanagementsystem.dtos.request.EditNoteRequest;
import africa.semicolon.notemanagementsystem.exceptions.NoteManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NoteServicesTest {

    @Autowired
    private NoteServices noteServices;
    @Autowired
    private Notes notes;

    @BeforeEach
    public void setup(){
        notes.deleteAll();
    }

    @Test
    public void addNoteTest(){
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("note content");
        addNoteRequest.setAuthor("username");
        noteServices.addNote(addNoteRequest);
        assertEquals(1, notes.count());
    }

    @Test
    public void addNonUniqueTitleTest(){
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("note content");
        addNoteRequest.setAuthor("username");
        noteServices.addNote(addNoteRequest);
        assertEquals(1, notes.count());
        AddNoteRequest addNoteRequest2 = new AddNoteRequest();
        addNoteRequest2.setTitle("first note");
        addNoteRequest2.setContent("note content");
        addNoteRequest2.setAuthor("username");
        try {
            noteServices.addNote(addNoteRequest2);
        }catch (NoteManagementException e){
            assertEquals(e.getMessage(), "title exists");
        }
        assertEquals(1, notes.count());
    }

    @Test
    public void deleteNoteTest(){
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("note content");
        addNoteRequest.setAuthor("username");
        noteServices.addNote(addNoteRequest);
        assertEquals(1, notes.count());
        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setTitle("first note");
        noteServices.deleteNote(deleteNoteRequest);
        assertEquals(0, notes.count());
    }

    @Test
    public void deleteNonExistingNoteTest(){
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("note content");
        addNoteRequest.setAuthor("username");
        noteServices.addNote(addNoteRequest);
        assertEquals(1, notes.count());
        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setTitle("wrong note");
        try {
            noteServices.deleteNote(deleteNoteRequest);
        }catch (NoteManagementException e){
            assertEquals(e.getMessage(), "title does not exist");
        }
        assertEquals(1, notes.count());
    }

    @Test
    public void editNoteTest(){
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("note content");
        addNoteRequest.setAuthor("username");
        noteServices.addNote(addNoteRequest);
        assertEquals(1, notes.count());
        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setTitle("first note");
        editNoteRequest.setNewTitle("new note");
        editNoteRequest.setNewContent("new content");
        noteServices.editNote(editNoteRequest);
        assertNotNull(notes.findByTitle("new note").getId());
        assertEquals(1, notes.count());
    }

    @Test
    public void editNonExistingNote_throwsExceptionTest(){
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("first note");
        addNoteRequest.setContent("note content");
        addNoteRequest.setAuthor("username");
        noteServices.addNote(addNoteRequest);
        assertEquals(1, notes.count());
        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setTitle("wrong note");
        editNoteRequest.setNewTitle("new note");
        editNoteRequest.setNewContent("new content");

        try {
            noteServices.editNote(editNoteRequest);
        }catch (NoteManagementException e){
            assertEquals(e.getMessage(), "title does not exist");
        }
        assertEquals(1, notes.count());
    }

}