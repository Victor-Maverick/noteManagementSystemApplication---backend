package africa.semicolon.notemanagementsystem.utils;

import africa.semicolon.notemanagementsystem.data.model.Note;
import africa.semicolon.notemanagementsystem.data.model.User;
import africa.semicolon.notemanagementsystem.dtos.request.AddNoteRequest;
import africa.semicolon.notemanagementsystem.dtos.request.EditNoteRequest;
import africa.semicolon.notemanagementsystem.dtos.request.RegisterRequest;
import africa.semicolon.notemanagementsystem.dtos.response.AddNoteResponse;
import africa.semicolon.notemanagementsystem.dtos.response.EditNoteResponse;
import africa.semicolon.notemanagementsystem.dtos.response.unlockResponse;
import africa.semicolon.notemanagementsystem.dtos.response.RegisterResponse;

import java.time.LocalDateTime;

public class Mapper {

    public static void map(User user, RegisterRequest registerRequest){
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail().toLowerCase());
        user.setPassword(registerRequest.getPassword());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
    }

    public static RegisterResponse map(User user){
        RegisterResponse response = new RegisterResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setLocked(user.isLocked());
        response.setDateCreated(LocalDateTime.now());
        return response;
    }
    public static void map(Note note, AddNoteRequest addNoteRequest){
        note.setTitle(addNoteRequest.getTitle());
        note.setAuthor(addNoteRequest.getAuthor());
        note.setContent(addNoteRequest.getContent());
        note.setDateCreated(LocalDateTime.now());
    }

    public static AddNoteResponse map(Note note){
        AddNoteResponse response = new AddNoteResponse();
        response.setTitle(note.getTitle());
        response.setDateCreated(note.getDateCreated());
        return response;
    }

    public static EditNoteResponse mapUpdate(Note note){
        EditNoteResponse response = new EditNoteResponse();
        response.setId(note.getId());
        response.setNewTitle(note.getTitle());
        response.setNewContent(note.getContent());
        return response;
    }
    public static unlockResponse mapLogin(User user){
        unlockResponse response = new unlockResponse();
        response.setId(user.getId());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setUsername(response.getUsername());
        response.setLoggedIn(user.isLocked());
        return response;
    }

    public static void map(Note note, EditNoteRequest editNoteRequest){
        note.setTitle(editNoteRequest.getNewTitle());
        note.setContent(editNoteRequest.getNewContent());
        note.setDateUpdated(LocalDateTime.now());
    }
}
