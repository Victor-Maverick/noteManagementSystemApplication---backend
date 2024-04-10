package africa.semicolon.notemanagementsystem.services;

import africa.semicolon.notemanagementsystem.data.model.Note;
import africa.semicolon.notemanagementsystem.data.repository.Notes;
import africa.semicolon.notemanagementsystem.dtos.request.AddNoteRequest;
import africa.semicolon.notemanagementsystem.dtos.request.DeleteNoteRequest;
import africa.semicolon.notemanagementsystem.dtos.request.EditNoteRequest;
import africa.semicolon.notemanagementsystem.dtos.response.AddNoteResponse;
import africa.semicolon.notemanagementsystem.dtos.response.EditNoteResponse;
import africa.semicolon.notemanagementsystem.exceptions.NoteNotFoundException;
import africa.semicolon.notemanagementsystem.exceptions.TitleExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static africa.semicolon.notemanagementsystem.utils.Mapper.map;
import static africa.semicolon.notemanagementsystem.utils.Mapper.mapUpdate;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteServices {
    private final Notes notes;
    @Override
    public AddNoteResponse addNote(AddNoteRequest addNoteRequest) {
        notes.findAll().forEach(note->{if(note.getTitle().equalsIgnoreCase(addNoteRequest.getTitle()))throw new TitleExistsException("title exists");});
        Note note = new Note();
        map(note, addNoteRequest);
        notes.save(note);
        return map(note);
    }

    @Override
    public String deleteNote(DeleteNoteRequest deleteNoteRequest) {
        notes.findAll().forEach(note->{if(!note.getTitle().equalsIgnoreCase(deleteNoteRequest.getTitle()))throw new NoteNotFoundException("title does not exist");});
        Note note = notes.findByTitle(deleteNoteRequest.getTitle());
        notes.delete(note);
        return "delete success";
    }

    @Override
    public EditNoteResponse editNote(EditNoteRequest editNoteRequest) {
        notes.findAll().forEach(note->{if(!note.getTitle().equalsIgnoreCase(editNoteRequest.getTitle()))throw new NoteNotFoundException("title does not exist");});
        Note note = notes.findByTitle(editNoteRequest.getTitle());
        map(note, editNoteRequest);
        notes.save(note);
        return mapUpdate(note);
    }


    @Override
    public Note findByTitle(String header) {
        return notes.findByTitle(header);
    }

    @Override
    public List<Note> findNotesFor(String username) {
        return notes.findByAuthor(username);
    }
}
