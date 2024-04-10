package africa.semicolon.notemanagementsystem.services;

import africa.semicolon.notemanagementsystem.data.model.Note;
import africa.semicolon.notemanagementsystem.dtos.request.AddNoteRequest;
import africa.semicolon.notemanagementsystem.dtos.request.DeleteNoteRequest;
import africa.semicolon.notemanagementsystem.dtos.request.EditNoteRequest;
import africa.semicolon.notemanagementsystem.dtos.response.AddNoteResponse;
import africa.semicolon.notemanagementsystem.dtos.response.EditNoteResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteServices {
    AddNoteResponse addNote(AddNoteRequest addNoteRequest);
    String deleteNote(DeleteNoteRequest deleteNoteRequest);
    EditNoteResponse editNote(EditNoteRequest editNoteRequest);
    Note findByTitle(String header);
    List<Note> findNotesFor(String username);
}
