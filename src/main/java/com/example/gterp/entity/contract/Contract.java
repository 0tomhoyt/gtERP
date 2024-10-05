package com.example.gterp.entity.contract;

import com.example.gterp.entity.user.Client;
import com.example.gterp.entity.user.Staff;
import jakarta.persistence.*;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)  // 多个合同对应一个客户
    private Client client;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)  // 一个合同对应一个员工
    private Staff staff;

    private String contractDetails;

    // Getter和Setter方法
}
