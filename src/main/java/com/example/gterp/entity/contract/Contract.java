package com.example.gterp.entity.contract;

import jakarta.persistence.*;
import com.example.gterp.entity.user.Client;
import java.time.LocalDate;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String contractNumber; // 合同号

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate creationDate; // 创建日期

    @Temporal(TemporalType.DATE)
    private LocalDate productionDate; // 排产日期

    @Temporal(TemporalType.DATE)
    private LocalDate scheduledDeliveryDate; // 预定交货日期

    @Temporal(TemporalType.DATE)
    private LocalDate actualDeliveryDate; // 实际交货日期

    @Column(length = 250)
    private String deliveryAddress; // 送货地址

    @Column(length = 100)
    private String deliveryMethod; // 送货方式

    @Column(length = 100)
    private String deliveryContact; // 送货联系人

    @Column(length = 50)
    private String deliveryPhone; // 送货电话

    @Column(nullable = false)
    private Double shippingCost; // 运费

    // Many-to-One 关系，多个合同对应一个客户
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client; // 客户外键

    // Getter and Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDate getScheduledDeliveryDate() {
        return scheduledDeliveryDate;
    }

    public void setScheduledDeliveryDate(LocalDate scheduledDeliveryDate) {
        this.scheduledDeliveryDate = scheduledDeliveryDate;
    }

    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDate actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryContact() {
        return deliveryContact;
    }

    public void setDeliveryContact(String deliveryContact) {
        this.deliveryContact = deliveryContact;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
