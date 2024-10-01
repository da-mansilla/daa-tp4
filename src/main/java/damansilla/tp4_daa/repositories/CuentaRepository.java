package damansilla.tp4_daa.repositories;

import damansilla.tp4_daa.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}