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
    private List<Client> clients;

    @OneToMany(mappedBy = "staff")
    private List<Contract> contracts;

    // Getter and Setter methods
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
