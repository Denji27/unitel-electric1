package la.com.unitel.repository;

import la.com.unitel.entity.usage_payment.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface ContractRepo extends JpaRepository<Contract, String> {

    List<Contract> findByIsActiveTrue();
    boolean existsByName(String contractName);

    @Query(value = "select c.id as id, c.name as name, c.avatarId as avatarId, c.phoneNumber as phoneNumber, c.gender as gender,\n" +
            "d.name as district, p.name as province, c.contractType as contractType, md.id as meterCode, \n" +
            "c.latitude as latitude, c.longitude as longitude, c.isActive as isActive, c.address as address, c.remark as remark, c.createdBy as createdBy, " +
            "c.updatedBy as updatedBy, c.createdAt as createdAt " +
            "from Contract c, District d , Province p, MeterDevice md \n" +
            "WHERE c.districtId = d.id and c.provinceId = p.id and md.contractId = c.id and :input is NULL or c.name like :input")
    <T> Page<T> searchContractDetail(String input, Class<T> type, Pageable pageable);

    @Query(value = "select c.id as id, c.name as name, c.avatarId as avatarId, c.phoneNumber as phoneNumber, c.gender as gender,\n" +
            "d.name as district, p.name as province, c.contractType as contractType, md.id as meterCode, \n" +
            "c.latitude as latitude, c.longitude as longitude, c.isActive as isActive, c.address as address, c.remark as remark, c.createdBy as createdBy, c.updatedBy as updatedBy, c.createdAt as createdAt\n" +
            "from Contract c , District d , Province p, MeterDevice md \n" +
            "WHERE c.districtId = d.id and c.provinceId = p.id and c.id = :contractId and md.contractId = c.id")
    <T> T findContractDetail(String contractId, Class<T> type);

    @Query(value = "select c.id as id, c.name as name, c.avatarId as avatarId, c.phoneNumber as phoneNumber, c.gender as gender,\n" +
            "d.name as district, p.name as province, c.contractType as contractType, md.id as meterCode, \n" +
            "c.latitude as latitude, c.longitude as longitude, c.isActive as isActive, c.address as address, c.remark as remark, c.createdBy as createdBy, c.updatedBy as updatedBy, c.createdAt as createdAt\n" +
            "from Contract c, District d , Province p, MeterDevice md \n" +
            "WHERE c.districtId = d.id and c.provinceId = p.id and md.contractId = c.id and c.id in :contractIdList")
    <T> Page<T> findContractDetailByIdIn(List<String> contractIdList, Class<T> type, Pageable pageable);

    @Query("select a.username From Contract c, ReaderContractMap rc, Account a where c.id = rc.id.contractId and rc.role = :role and a.id = rc.id.readerId and c.id = :contractId")
    Optional<String> findReaderOrCashierByContractId(String contractId, String role);
}
