package africa.semicolon.notemanagementsystem.services;
import africa.semicolon.notemanagementsystem.data.model.Note;
import africa.semicolon.notemanagementsystem.data.model.User;
import africa.semicolon.notemanagementsystem.data.repository.Users;
import africa.semicolon.notemanagementsystem.dtos.request.*;
import africa.semicolon.notemanagementsystem.dtos.response.AddNoteResponse;
import africa.semicolon.notemanagementsystem.dtos.response.EditNoteResponse;
import africa.semicolon.notemanagementsystem.dtos.response.UnlockResponse;
import africa.semicolon.notemanagementsystem.dtos.response.RegisterResponse;
import africa.semicolon.notemanagementsystem.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.semicolon.notemanagementsystem.utils.Mapper.map;
import static africa.semicolon.notemanagementsystem.utils.Mapper.mapLogin;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserServices {
    private final Users users;
    private final NoteServices noteServices;
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        validateUser(registerRequest);
        User user = new User();
        map(user,registerRequest);
        users.save(user);
        return map(user);
    }

    @Override
    public String deleteUser(DeleteUserRequest deleteRequest) {
        users.findAll().forEach(user -> {if(!user.getUsername().equals(deleteRequest.getUsername().toLowerCase()))throw new UserNotFoundException(deleteRequest.getUsername()+" does not exist");});
        User user = users.findByUsername(deleteRequest.getUsername());
        users.delete(user);
        return "delete success";
    }

    @Override
    public UnlockResponse unlockNotes(UnlockRequest unlockRequest) {
        User user = users.findByUsername(unlockRequest.getUsername());
        if (user == null)throw new UserNotFoundException(unlockRequest.getUsername()+ " not found");
        if(!user.getPassword().equals(unlockRequest.getPassword()))throw new IncorrectPasswordException("wrong password");
        user.setLocked(false);
        users.save(user);
        return mapLogin(user);
    }

    @Override
    public String lockNotes(LockRequest lockRequest) {
        User user = users.findByUsername(lockRequest.getUsername());
        if (user == null)throw new UserNotFoundException(lockRequest.getUsername()+ " does not exist");
        user.setLocked(true);
        users.save(user);
        return lockRequest.getUsername()+" locked";
    }

    @Override
    public AddNoteResponse addNote(AddNoteRequest addNoteRequest) {
        User user = users.findByUsername(addNoteRequest.getAuthor());
        if (user == null)throw new UserNotFoundException(addNoteRequest.getAuthor()+" not found");
        validateLock(user);
        AddNoteResponse noteResponse = noteServices.addNote(addNoteRequest);
        List<Note> userNotes = user.getUserNotes();
        Note note = noteServices.findByTitle(addNoteRequest.getTitle());
        userNotes.add(note);
        user.setUserNotes(userNotes);
        users.save(user);
        return noteResponse;
    }

    private static void validateLock(User user) {
        if (user.isLocked())throw new LoginException("unlock first");
    }

    @Override
    public EditNoteResponse editNote(EditNoteRequest editRequest) {
        User user = users.findByUsername(editRequest.getAuthor());
        if (user == null) throw new UserNotFoundException(editRequest.getAuthor()+" does not exist");
        validateLock(user);
        EditNoteResponse noteResponse = noteServices.editNote(editRequest);
        List<Note> userNotes = user.getUserNotes();
        Note note = noteServices.findByTitle(editRequest.getNewTitle());
        userNotes.removeIf(note1 -> note1.getTitle().equalsIgnoreCase(editRequest.getTitle()));
        userNotes.add(note);
        user.setUserNotes(userNotes);
        users.save(user);
        return noteResponse;
    }

    @Override
    public String deleteNote(DeleteNoteRequest deleteNoteRequest) {
        User user = users.findByUsername(deleteNoteRequest.getAuthor());
        if (user == null) throw new UserNotFoundException(deleteNoteRequest.getAuthor()+" does not exist");
        validateLock(user);
        String response = noteServices.deleteNote(deleteNoteRequest);
        List<Note> userNotes = user.getUserNotes();
        userNotes.removeIf(note1 -> note1.getTitle().equalsIgnoreCase(deleteNoteRequest.getTitle()));
        user.setUserNotes(userNotes);
        users.save(user);
        return response;
    }

    @Override
    public List<Note> findNotesFor(String username) {
        return noteServices.findNotesFor(username);
    }

    @Override
    public Note searchForNote(SearchNoteRequest searchNoteRequest) {
        User user = users.findByUsername(searchNoteRequest.getAuthor());
        if (user == null) throw new UserNotFoundException(searchNoteRequest.getAuthor()+" does not exist");
        validateLock(user);
        return noteServices.findByTitle(searchNoteRequest.getTitle());
    }

    private void validateUser(RegisterRequest registerRequest){
        users.findAll().forEach(user -> {if(user.getUsername().equals(registerRequest.getUsername().toLowerCase()))throw new UsernameExistsException(registerRequest.getUsername()+" already exists");});
        users.findAll().forEach(user -> {if(user.getEmail().equals(registerRequest.getEmail().toLowerCase()))throw new UsernameExistsException("email already exists");});
        if(registerRequest.getPhoneNumber().length() != 11)throw new InvalidPhoneNumberException("enter valid phone number");
    }
}
