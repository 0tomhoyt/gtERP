package com.example.gterp.entity.user;

import jakarta.persistence.*;
import java.util.List;
import com.example.gterp.entity.contract.Contract;

@Entity
public class Staff extends User {

    @Column(length = 250)
    private String role;

    @OneToMany(mappedBy = "staff")
    private List<Client> clients;

    @OneToMany(mappedBy = "staff")
    private List<Contract> contracts;



    // Getter and Setter methods
    public List<Client> getClients() {
        return clients;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

