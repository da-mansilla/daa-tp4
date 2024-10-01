package damansilla.tp4_daa.repositories;

import damansilla.tp4_daa.models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}