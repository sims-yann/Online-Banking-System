package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@DiscriminatorValue("Transfer")
@EqualsAndHashCode(callSuper = true)
public class Transfer extends Transaction {

    private String state;

    // Use parent's SourceAccount/DestinationAccount instead of new fields
    public Account getSender() {
        return getSourceAccount();
    }

    public void setSender(Account sender) {
        setSourceAccount(sender);
    }

    public Account getReceiver() {
        return getDestinationAccount();
    }

    public void setReceiver(Account receiver) {
        setDestinationAccount(receiver);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}