package la.com.unitel.business.device.dto;

import la.com.unitel.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDeviceRequest extends BaseRequest {
    private String description;
    private String brand;
    private Integer maximumUnit;
    private String remark;
}