package africa.semicolon.notemanagementsystem.services;

import africa.semicolon.notemanagementsystem.data.model.Note;
import africa.semicolon.notemanagementsystem.dtos.request.*;
import africa.semicolon.notemanagementsystem.dtos.response.AddNoteResponse;
import africa.semicolon.notemanagementsystem.dtos.response.EditNoteResponse;
import africa.semicolon.notemanagementsystem.dtos.response.unlockResponse;
import africa.semicolon.notemanagementsystem.dtos.response.RegisterResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServices {
    RegisterResponse register(RegisterRequest registerRequest);

    String deleteUser(DeleteUserRequest deleteRequest);

    unlockResponse unlockNotes(UnlockRequest unlockRequest);

    String lockNotes(LockRequest lockRequest);

    AddNoteResponse addNote(AddNoteRequest addNoteRequest);

    EditNoteResponse editNote(EditNoteRequest editRequest);

    String deleteNote(DeleteNoteRequest deleteNoteRequest);

    List<Note> findNotesFor(String username);

    Note searchForNote(SearchNoteRequest searchNoteRequest);
}
