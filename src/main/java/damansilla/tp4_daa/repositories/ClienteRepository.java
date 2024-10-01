package damansilla.tp4_daa.repositories;

import damansilla.tp4_daa.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}