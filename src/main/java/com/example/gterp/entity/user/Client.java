package com.example.gterp.entity.user;

import jakarta.persistence.*;
import java.util.List;
import com.example.gterp.entity.contract.Contract;
import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
public class Client extends User {

    @Column(length = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    @JsonBackReference // 添加此注解
    private Staff staff;

    @OneToMany(mappedBy = "client")
    private List<Contract> contracts;

    @Column(length = 250)
    private String address;

    //复制器
    public void copyNonNullPropertiesFrom(User other) {
        super.copyNonNullPropertiesFrom(other);
        if (other instanceof Client) {
            Client otherClient = (Client) other;
            if (this.description == null && otherClient.description != null) {
                this.description = otherClient.description;
            }
            if (this.staff == null && otherClient.staff != null) {
                this.staff = otherClient.staff;
            }
            if (this.contracts == null && otherClient.contracts != null) {
                this.contracts = otherClient.contracts;
            }
            if (this.address == null && otherClient.address != null) {
                this.address = otherClient.address;
            }
        }
    }

    // Getter and Setter methods

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
