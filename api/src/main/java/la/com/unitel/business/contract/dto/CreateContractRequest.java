package la.com.unitel.business.contract.dto;

import la.com.unitel.business.BaseRequest;
import la.com.unitel.exception.validation.GenderRegex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContractRequest extends BaseRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String districtId;
    @NotBlank
    private String contractType;
    @NotBlank
    private String deviceId;
    private String latitude;
    private String longitude;
    private String address;
    private String remark;

    @GenderRegex
    private String gender;
}


