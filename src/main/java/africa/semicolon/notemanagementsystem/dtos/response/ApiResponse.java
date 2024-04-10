package africa.semicolon.notemanagementsystem.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse {
    private boolean isSuccessful;
    private Object data;
}
