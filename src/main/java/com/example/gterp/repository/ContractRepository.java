package com.example.gterp.repository;
import com.example.gterp.entity.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    // 如果需要，可以在这里定义自定义查询方法
    // 根据客户 ID 查询合同
    List<Contract> findByClientId(Long clientId);

    // 根据员工 ID 查询合同（通过客户的员工）
    List<Contract> findByClientStaffId(Long staffId);
}

