package damansilla.tp4_daa.services.cuenta;

import damansilla.tp4_daa.models.Cuenta;
import damansilla.tp4_daa.models.Movimiento;

import java.util.List;

public interface ICuentaService {

    Cuenta create(Cuenta cuenta);

    Cuenta getById(Long id);

    void actualizar(Cuenta cuenta, Long id);

    void extraerDinero(Long cuentaId, double monto);

    void depositarDinero(Long cuentaId, double monto);

    void delete(Long id);

    List<Movimiento> getMovimientos(Long id);
}
