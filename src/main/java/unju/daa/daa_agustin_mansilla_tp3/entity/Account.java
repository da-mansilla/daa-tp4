package unju.daa.daa_agustin_mansilla_tp3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private long accountNumber;

    @OneToOne
    private Client client;

    private Date entryDate;

    private double balance;

    private String status;

    private double extractionLimit;

}

