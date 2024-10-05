package com.example.gterp.entity.user;

import jakarta.persistence.*;
import java.util.List;
import com.example.gterp.entity.contract.Contract;

@Entity
public class Client extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)  // 多个客户对应一个员工
    private Staff staff;

    @OneToMany(mappedBy = "client")
    private List<Contract> contracts;  // 一个客户可以有多个合同

    @Column(length = 250)
    private String address;

    // Getter和Setter方法
}

