package africa.semicolon.notemanagementsystem.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private String id;
    private String username;
    private boolean isLoggedIn;
    private String phoneNumber;
}
