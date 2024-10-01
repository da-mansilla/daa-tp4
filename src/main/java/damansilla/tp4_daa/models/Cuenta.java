package damansilla.tp4_daa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroCuenta;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private Date fechaIngreso;

    private Double saldo;

    private String estado;

    private Double limiteExtraccion;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Movimiento> movimientos = new ArrayList<>();

}
