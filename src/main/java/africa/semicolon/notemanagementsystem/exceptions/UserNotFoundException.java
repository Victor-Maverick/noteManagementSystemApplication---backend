package africa.semicolon.notemanagementsystem.exceptions;

public class UserNotFoundException extends NoteManagementException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
