package africa.semicolon.notemanagementsystem.exceptions;

public class UsernameExistsException extends NoteManagementException{
    public UsernameExistsException(String message) {
        super(message);
    }
}
