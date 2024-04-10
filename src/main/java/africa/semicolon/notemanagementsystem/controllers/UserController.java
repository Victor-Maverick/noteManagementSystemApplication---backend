package africa.semicolon.notemanagementsystem.controllers;
import africa.semicolon.notemanagementsystem.data.model.Note;
import africa.semicolon.notemanagementsystem.dtos.request.*;
import africa.semicolon.notemanagementsystem.dtos.response.ApiResponse;
import africa.semicolon.notemanagementsystem.dtos.response.EditNoteResponse;
import africa.semicolon.notemanagementsystem.exceptions.NoteManagementException;
import africa.semicolon.notemanagementsystem.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try{
            var response = userServices.register(registerRequest);
            return new ResponseEntity<>(new ApiResponse(true,response), CREATED);
        }catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), BAD_REQUEST);
        }
    }


    @PostMapping("/lock")
    public ResponseEntity<?> lockNotes(@RequestBody LockRequest lockRequest) {
        try {
            var response = userServices.lockNotes(lockRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), LOCKED);
        }catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/unlock")
    public ResponseEntity<?> unlockNotes(@RequestBody UnlockRequest unlockRequest) {
        try{
            var response = userServices.unlockNotes(unlockRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), OK);
        }
        catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_GATEWAY);
        }
    }

    @PostMapping("/add-note")
    public ResponseEntity<?> addNote(@RequestBody AddNoteRequest addNoteRequest) {
        try{
            var response = userServices.addNote(addNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/update-note")
    public ResponseEntity<?> updateNote(@RequestBody EditNoteRequest editNoteRequest) {
        try{
            EditNoteResponse editResponse = userServices.editNote(editNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, editResponse), OK);
        }
        catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-note")
    public ResponseEntity<?> deleteNote(@RequestBody DeleteNoteRequest deleteRequest) {
        try{
            var deleteResponse = userServices.deleteNote(deleteRequest);
            return new ResponseEntity<>(new ApiResponse(true, deleteResponse), OK);
        }
        catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(true, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/find-note")
    public ResponseEntity<?> findByTitle(SearchNoteRequest searchRequest) {
        try {
            Note note = userServices.searchForNote(searchRequest);
            return new ResponseEntity<>(new ApiResponse(true, note), OK);
        }catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/find-all-notes{name}")
    public ResponseEntity<?> findAllNotes(@PathVariable("name") String username){
        try {
            List<Note> notes = userServices.findNotesFor(username);
            return new ResponseEntity<>(new ApiResponse(true, notes),OK);
        }
        catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

}
