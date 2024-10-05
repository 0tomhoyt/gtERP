package com.example.gterp.entity.user;

import jakarta.persistence.*;
import java.util.List;
import com.example.gterp.entity.contract.Contract;

@Entity
public class Staff extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "staff")
    private List<Client> clients;  // 一个员工可以有多个客户

    @OneToMany(mappedBy = "staff")
    private List<Contract> contracts;  // 一个员工可以有多个合同

    // Getter和Setter方法
}

