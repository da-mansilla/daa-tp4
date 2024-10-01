package damansilla.tp4_daa.services.cliente;

import damansilla.tp4_daa.models.Cliente;
import damansilla.tp4_daa.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente create(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente getClientById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public void addAdherente(Cliente adherente, Long idTitular) {
        Cliente titular = clienteRepository.findById(idTitular)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        titular.setAdherente(adherente);
        clienteRepository.save(titular);
    }

    @Override
    public void delete(Long id) {
        clienteRepository.findById(id)
                .ifPresent(clienteRepository::delete);
    }
}
