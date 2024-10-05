package com.example.gterp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gterp.entity.user.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    // 你可以根据需要定义自定义查询方法，比如根据员工的用户名查找员工
    Staff findByUsername(String username);
}
