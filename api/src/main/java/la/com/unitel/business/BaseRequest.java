package la.com.unitel.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseRequest {
    @NotBlank
    String requestId;
    @NotBlank
    String currentTime;
}
