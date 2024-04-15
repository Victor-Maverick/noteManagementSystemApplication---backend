package africa.semicolon.notemanagementsystem.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnlockResponse {
    private String id;
    private String username;
    private boolean isLocked;
}
