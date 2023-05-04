package la.com.unitel.business.consumption;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.Translator;
import la.com.unitel.business.consumption.dto.ConsumptionDetailDto;
import la.com.unitel.business.consumption.dto.ReadConsumptionDto;
import la.com.unitel.entity.account.Account;
import la.com.unitel.entity.account.MeterDevice;
import la.com.unitel.entity.constant.ConsumptionStatus;
import la.com.unitel.entity.usage_payment.Consumption;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class ConsumptionBusinessCommon extends BaseBusiness implements IConsumptionCommon {

    @Override
    public CommonResponse onGetUnReadByReader(String readerUsername, int page, int size) {
        Account reader = baseService.getAccountService().findByUsername(readerUsername);
        if (reader == null || !reader.getIsActive())
            throw new ErrorCommon(ErrorCode.READER_INVALID, Translator.toLocale(ErrorCode.READER_INVALID));

//        find unread list
        Page<Consumption> unreadList = baseService.getConsumptionService().findUnReadByReaderTillNow(readerUsername, page, size);
        List<ReadConsumptionDto> collect = unreadList.getContent().parallelStream().map(this::convert).collect(Collectors.toList());
        Page<ReadConsumptionDto> result = new PageImpl<>(collect, unreadList.getPageable(), unreadList.getTotalElements());


//        find contract detail of unread list
        /*Page<ContractDetailView> views = contractService.findByIdInList(unreadList, ContractDetailView.class, pageable);
        List<ContractDetail> collect = views.getContent().parallelStream().map(ContractDetail::generate).collect(Collectors.toList());
        Page<ContractDetail> result = new PageImpl<>(collect, pageable, views.getTotalElements());*/
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }

    /*private UnreadDto convert(Consumption c) {
        Contract contract = baseService.getContractService().findById(c.getContractId());
        if (contract == null || !contract.getIsActive())
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        MeterDevice device = baseService.getDeviceService().findByContractIdAndIsActiveTrue(c.getContractId());
        if (device == null)
            throw new ErrorCommon(ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));

        return UnreadDto.builder()
                .consumptionId(c.getId())
                .contractName(contract.getName())
                .contractId(contract.getId())
                .meterDevice(device.getId())
                .period(c.getPeriod())
                .address(contract.getAddress())
                .readBy(c.getReadBy())
                .build();
    }*/

    @Override
    public CommonResponse onGetReadHistoryByReader(String readerUsername, LocalDate fromDate, LocalDate toDate, int page, int size) {
        Account reader = baseService.getAccountService().findByUsername(readerUsername);
        if (reader == null || !reader.getIsActive())
            throw new ErrorCommon(ErrorCode.READER_INVALID, Translator.toLocale(ErrorCode.READER_INVALID));

        /*Page<HistoryReadView> views = consumptionService.findByCreatedAtAndReader(fromDate, toDate, reader.getUsername(), HistoryReadView.class, pageable);
        List<HistoryRead> collect = views.getContent().parallelStream().map(HistoryRead::generate).collect(Collectors.toList());
        Page<HistoryRead> result = new PageImpl<>(collect, pageable, views.getTotalElements());*/

        Page<Consumption> readList = baseService.getConsumptionService().findReadByReader(readerUsername, fromDate, toDate, page, size);
        List<ReadConsumptionDto> collect = readList.getContent().parallelStream().map(this::convert).collect(Collectors.toList());
        Page<ReadConsumptionDto> result = new PageImpl<>(collect, readList.getPageable(), readList.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }

    private ReadConsumptionDto convert(Consumption c) {
        Contract contract = baseService.getContractService().findById(c.getContractId());
        if (contract == null || !contract.getIsActive())
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        MeterDevice device = baseService.getDeviceService().findByContractIdAndIsActiveTrue(c.getContractId());
        if (device == null)
            throw new ErrorCommon(ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));

        return ReadConsumptionDto.builder()
                .consumptionId(c.getId())
                .contractName(contract.getName())
                .contractId(contract.getId())
                .meterDevice(device.getId())
                .period(c.getPeriod())
                .address(contract.getAddress())
                .readBy(c.getReadBy())
                .usageConsumption(c.getUsageConsumption())
                .readAt(c.getReadAt())
                .build();
    }

    @Override
    public CommonResponse onConsumptionDetail(String consumptionId) {
        Consumption consumption = baseService.getConsumptionService().findById(consumptionId);
        if (consumption == null || !consumption.getStatus().equals(ConsumptionStatus.READ))
            throw new ErrorCommon(ErrorCode.CONSUMPTION_INVALID, Translator.toLocale(ErrorCode.CONSUMPTION_INVALID));

        return generateSuccessResponse(UUID.randomUUID().toString(), detailConvert(consumption));
    }

    private ConsumptionDetailDto detailConvert(Consumption c) {
        Contract contract = baseService.getContractService().findById(c.getContractId());
        if (contract == null || !contract.getIsActive())
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        return ConsumptionDetailDto.builder()
                .consumptionId(c.getId())
                .contractName(contract.getName())
                .contractId(contract.getId())
                .meterDevice(c.getMeterCode())
                .period(c.getPeriod())
                .address(contract.getAddress())
                .readBy(c.getReadBy())
                .usageConsumption(c.getUsageConsumption())
                .readAt(c.getReadAt())
                .imageId(c.getImageId())
                .latitude(c.getLatitude())
                .longitude(c.getLongitude())
                .status(c.getStatus().name())
                .isRollOver(c.getIsRollOver())
                .photoAt(c.getReadAt())
                .build();
    }

    @Override
    public CommonResponse onGetConsumptionHistoryByContractId(String contractId, LocalDate fromDate, LocalDate toDate, int page, int size) {
        Contract contract = baseService.getContractService().findById(contractId);
        if (contract == null || !contract.getIsActive())
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        Page<Consumption> histories = baseService.getConsumptionService().findConsumptionByContractId(contractId, fromDate, toDate, page, size);
        return generateSuccessResponse(UUID.randomUUID().toString(), histories);
    }
}
