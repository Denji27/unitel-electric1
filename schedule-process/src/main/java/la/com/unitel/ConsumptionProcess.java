package la.com.unitel;

import la.com.unitel.entity.account.ReaderContractMap;
import la.com.unitel.entity.constant.ConsumptionStatus;
import la.com.unitel.entity.usage_payment.Consumption;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.repository.ConsumptionRepo;
import la.com.unitel.repository.ContractRepo;
import la.com.unitel.repository.ReaderContractMapRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author : Tungct
 * @since : 5/3/2023, Wed
 **/
@Service
@Slf4j
public class ConsumptionProcess {

    @Autowired
    private ConsumptionRepo consumptionRepo;

    @Autowired
    private ContractRepo contractRepo;
    @Autowired
    private ReaderContractMapRepo readerContractMapRepo;

    @Autowired
    private Util util;

    @Scheduled(fixedDelay = 9000000000000000000L)
    public void insertConsumptionForMonth() {
        String processId = UUID.randomUUID().toString();
        long start = System.currentTimeMillis();
        String period =  util.getMonthCode(LocalDate.now());
        log.info("[START] insert consumption for month {}, process id = {}", period, processId);

        /*try (Stream<Consumption> collect = consumptionRepo.streamAllBy()){

        }*/

        List<Contract> contracts = contractRepo.findByIsActiveTrue();
        log.info("Contract list size = {}", contracts.size());
        for (Contract contract : contracts) {
            log.info("Current contract id {}", contract.getId());

            Consumption consumption = new Consumption();
            consumption.setContractId(contract.getId());
            consumption.setPeriod(period);
            consumption.setStatus(ConsumptionStatus.UNREAD);
            consumption.setReadBy(findReaderByContractId(contract.getId(), "edl-reader"));
            consumptionRepo.save(consumption);
        }

        long end = System.currentTimeMillis();
        log.info("[FINISH] process id {} in [{}] ms", processId, (end - start));
    }

    private String findReaderByContractId(String contractId, String role) {
        List<String> readerList = readerContractMapRepo.findByIdContractIdAndRole(contractId, role);
        if (readerList.size() != 1) {
            log.warn("Reader not found for contract id {}", contractId);
            return null;
        }
        return readerList.get(0);
    }
}
