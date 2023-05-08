package la.com.unitel.business.bill.dto;

import la.com.unitel.business.bill.dto.projection.BillDetailView;
import la.com.unitel.entity.constant.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillResponse {
    private String billId;
    private String phoneNumber;
    private String status;
    private String contractId;
    private String meterDevice;
    private String period;
    private String contractName;
    private String contractType;
    private String address;
    private String readBy;
    private String cashier;
    private String district;
    private String province;
    private String meterCode;
    private String paidBy;
    private Integer usageConsumption;
    private Integer readUnit;
    private LocalDateTime readAt;
    private LocalDateTime billingDate;
    private LocalDateTime dueDate;
    private LocalDateTime paidAt;
    private BigDecimal totalAmount;
    private BigDecimal usageCharge;
    private BigDecimal serviceCharge;
    private BigDecimal tax;
    private BigDecimal lateFee;



    /*public static BillResponse generate(BillDetailView view){
        return BillResponse.builder()
                .billId(view.getBillId())
                .phoneNumber(view.getPhoneNumber())
                .status(view.getStatus().name())
                .totalAmount(view.getTotalAmount())
                .district(view.getDistrict())
                .province(view.getProvince())
                .district(view.getDistrict())
                .province(view.getProvince())
                .billingDate(view.getCreatedAt())
                .dueDate(view.getDueDate())
                .contractId(view.getContractId())
                .contractName(view.getContractName())
                .readUnit(view.getReadUnit())
                .meterCode(view.getMeterCode())
                .build();
    }*/
}






