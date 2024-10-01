package damansilla.tp4_daa.services.cuenta;

import damansilla.tp4_daa.exceptions.CuentaLimiteExtraccionExcedidoException;
import damansilla.tp4_daa.exceptions.CuentaSaldoInsuficienteException;
import damansilla.tp4_daa.models.Cuenta;
import damansilla.tp4_daa.models.Movimiento;
import damansilla.tp4_daa.repositories.CuentaRepository;
import damansilla.tp4_daa.repositories.MovimientoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService implements ICuentaService{

    private final CuentaRepository cuentaRepository;

    @Override
    public Cuenta create(Cuenta cuenta) {
        cuenta.setMovimientos(new ArrayList<>());
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Cuenta getById(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    @Override
    public void actualizar(Cuenta cuentaActualizada, Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        cuenta.setNumeroCuenta(cuentaActualizada.getNumeroCuenta());
        cuenta.setSaldo(cuentaActualizada.getSaldo());
        cuenta.setEstado(cuentaActualizada.getEstado());
        cuenta.setLimiteExtraccion(cuentaActualizada.getLimiteExtraccion());
        cuentaRepository.save(cuenta);
    }

    @Transactional
    @Override
    public void extraerDinero(Long cuentaId, double monto) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        if(cuenta.getSaldo() < monto){
            throw new CuentaSaldoInsuficienteException("Saldo insuficiente");
        }
        if(cuenta.getLimiteExtraccion() < monto){
            throw new CuentaLimiteExtraccionExcedidoException("Límite de extracción insuficiente");
        }
        cuenta.setSaldo(cuenta.getSaldo() - monto);
        Movimiento movimiento =  createMovimiento(cuenta, monto, "Extracción");
        cuenta.getMovimientos().add(movimiento);

        cuentaRepository.save(cuenta);
    }

    @Transactional
    @Override
    public void depositarDinero(Long cuentaId, double monto) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        cuenta.setSaldo(cuenta.getSaldo() + monto);
        Movimiento movimiento = createMovimiento(cuenta, monto, "Depósito");
        cuenta.getMovimientos().add(movimiento);
        cuentaRepository.save(cuenta);
    }

    @Override
    public void delete(Long id) {
        cuentaRepository.findById(id)
                .ifPresent(cuentaRepository::delete);
    }

    @Transactional
    @Override
    public List<Movimiento> getMovimientos(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return cuenta.getMovimientos();
    }

    private Movimiento createMovimiento(Cuenta cuenta, double monto, String tipo){
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(new Date());
        movimiento.setCuenta(cuenta);
        movimiento.setTipo(tipo);
        movimiento.setMonto(monto);
        return movimiento;
    }
}
