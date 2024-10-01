package damansilla.tp4_daa.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Cuenta cuenta;

    private String tipo;

    private Double monto;


}
