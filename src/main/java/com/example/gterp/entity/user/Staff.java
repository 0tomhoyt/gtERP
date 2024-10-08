package com.example.gterp.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import com.example.gterp.entity.contract.Contract;

@Entity
public class Staff extends User {

    @Column(length = 250)
    private String role;

    @OneToMany(mappedBy = "staff")
    @JsonIgnore
    private List<Client> clients;

    //复制器
    @Override
    public void copyNonNullPropertiesFrom(User other) {
        super.copyNonNullPropertiesFrom(other);
        if (other instanceof Staff) {
            Staff otherStaff = (Staff) other;
            if (this.role == null && otherStaff.role != null) {
                this.role = otherStaff.role;
            }
            if (this.clients == null && otherStaff.clients != null) {
                this.clients = otherStaff.clients;
            }
        }
    }


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

}

