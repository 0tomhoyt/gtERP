package com.example.gterp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.gterp.entity.user.Client;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long> {
    // 自定义查询方法，比如根据员工ID查找所有客户
    List<Client> findByStaffId(Long staffId);

    @Query("SELECT c FROM Client c WHERE c.isDeleted = false")
    List<Client> findAllActiveClients();

}

