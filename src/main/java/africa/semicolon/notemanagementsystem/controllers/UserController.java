package africa.semicolon.notemanagementsystem.controllers;


import africa.semicolon.notemanagementsystem.dtos.request.LockRequest;
import africa.semicolon.notemanagementsystem.dtos.request.RegisterRequest;
import africa.semicolon.notemanagementsystem.dtos.request.UnlockRequest;
import africa.semicolon.notemanagementsystem.dtos.response.ApiResponse;
import africa.semicolon.notemanagementsystem.dtos.response.RegisterResponse;
import africa.semicolon.notemanagementsystem.exceptions.NoteManagementException;
import africa.semicolon.notemanagementsystem.services.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<?> unlockNotes(UnlockRequest unlockRequest) {
        try{
            var response = userServices.unlockNotes(unlockRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), OK);
        }
        catch (NoteManagementException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_GATEWAY);
        }
    }
}
