package damansilla.tp4_daa.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer dni;

    private String nombre;

    private String email;

    private String domicilio;

    private Date fechaIngreso;

    private String estado;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

    @OneToOne
    @JoinColumn(name = "adherente_id")
    private Cliente adherente;
}
