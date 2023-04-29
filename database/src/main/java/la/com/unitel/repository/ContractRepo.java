package la.com.unitel.repository;

import la.com.unitel.entity.usage_payment.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface ContractRepo extends JpaRepository<Contract, String> {

    boolean existsByMeterCode(String meterCode);
    boolean existsByName(String contractName);

    @Query(value = "select c.id as id, c.name as name, a.username as username, a.avatar_id as avatarId, a.phone_number as phoneNumber, a.gender as gender,\n" +
            "d.name as district, p.name as province, c.contract_type as contractType, c.meter_code as meterCode, \n" +
            "c.latitude as latitude, c.longitude as longitude, c.is_active as isActive, c.address as address, c.remark as remark, c.created_by as createdBy, " +
            "c.updated_by as updatedBy, c.created_at as createdAt " +
            "from contract c , account a , district d , province p \n" +
            "WHERE c.account_id = a.id and c.district_id = d.id and c.province_id = p.id and :input is NULL or c.name like :input",
            nativeQuery = true)
    <T> Page<T> searchContractDetail(String input, Class<T> type, Pageable pageable);

    /*countQuery = "select count(*) from contract c , account a , district d , province p " +
                    "WHERE c.account_id = a.id and c.district_id = d.id and c.province_id = p.id and :input is NULL or c.name like :input",*/

    @Query(value = "select c.id as id, c.name as name, a.username as username, a.avatar_id as avatarId, a.phone_number as phoneNumber, a.gender as gender,\n" +
            "d.name as district, p.name as province, c.contract_type as contractType, c.meter_code as meterCode, \n" +
            "c.latitude as latitude, c.longitude as longitude, c.is_active as isActive, c.address as address, c.remark as remark, c.created_by as createdBy, c.updated_by as updatedBy, c.created_at as createdAt\n" +
            "from contract c , account a , district d , province p \n" +
            "WHERE c.account_id = a.id and c.district_id = d.id and c.province_id = p.id and c.id = :contractId", nativeQuery = true)
    <T> T findContractDetail(String contractId, Class<T> type);

    @Query(value = "select c.id as id, c.name as name, a.username as username, a.avatar_id as avatarId, a.phone_number as phoneNumber, a.gender as gender,\n" +
            "d.name as district, p.name as province, c.contract_type as contractType, c.meter_code as meterCode, \n" +
            "c.latitude as latitude, c.longitude as longitude, c.is_active as isActive, c.address as address, c.remark as remark, c.created_by as createdBy, c.updated_by as updatedBy, c.created_at as createdAt\n" +
            "from contract c , account a , district d , province p \n" +
            "WHERE c.account_id = a.id and c.district_id = d.id and c.province_id = p.id and c.id in :contractIdList", nativeQuery = true)
    <T> Page<T> findContractDetailByIdIn(List<String> contractIdList, Class<T> type, Pageable pageable);
}
