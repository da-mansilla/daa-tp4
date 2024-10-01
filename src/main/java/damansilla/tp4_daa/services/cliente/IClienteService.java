package damansilla.tp4_daa.services.cliente;

import damansilla.tp4_daa.models.Cliente;

public interface IClienteService {

    Cliente create(Cliente cliente);

    Cliente getClientById(Long id);

    void addAdherente(Cliente adherente, Long idTitular);

    void delete(Long id);
}
