package damansilla.tp4_daa;

import damansilla.tp4_daa.exceptions.CuentaLimiteExtraccionExcedidoException;
import damansilla.tp4_daa.exceptions.CuentaSaldoInsuficienteException;
import damansilla.tp4_daa.models.Cliente;
import damansilla.tp4_daa.models.Cuenta;
import damansilla.tp4_daa.services.cliente.ClienteService;
import damansilla.tp4_daa.services.cuenta.CuentaService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class Tp4DaaApplicationTests {
	public static Cliente cliente;
	public static Cliente adherente;
	public static Cuenta cuenta;
    @Autowired
    private  CuentaService cuentaService;
	@Autowired
	private  ClienteService clienteService;



	@BeforeEach
	public void setUp() {
		cliente = new Cliente();
		adherente = new Cliente();
		cuenta = new Cuenta();

		cliente.setNombre("Agustin");
		cliente.setDni(42072819);
		cliente.setEmail("agustin@gmail.com");
		cliente.setDomicilio("Calle falsa 123");
		cliente.setFechaIngreso(new Date());
		cliente.setEstado("Activo");
		clienteService.create(cliente);

		adherente.setNombre("Juan");
		adherente.setDni(12345678);
		adherente.setEmail("juan@gmail.com");
		adherente.setDomicilio("Calle Humahuaca 123");
		adherente.setFechaIngreso(new Date());
		adherente.setEstado("Activo");
		clienteService.create(adherente);


		cuenta.setCliente(cliente);
		cuenta.setNumeroCuenta(102);
		cuenta.setFechaIngreso(new Date());
		cuenta.setSaldo(100000.0);
		cuenta.setLimiteExtraccion(50000.0);
		//cuenta.setMovimientos(null);
		cuentaService.create(cuenta);
	}

	@AfterEach
	public  void clean() {
		cuentaService.delete(cuenta.getId());
		clienteService.delete(cliente.getId());
		clienteService.delete(adherente.getId());


	}


	@Test
	public void Add_Adherente_A_Cliente() {
		clienteService.addAdherente(adherente,cliente.getId());
		// Verificar que se puede obtener al adherente a travez del cliente
		assertEquals(clienteService.getClientById(cliente.getId()).getAdherente().getDni(),12345678);
	}

	@Test
	public void Add_Cuenta_A_Cliente() {
		assertEquals(cuentaService.getById(cuenta.getId()).getCliente().getDni(),42072819);
	}

	@Test
	public void Realizar_Extraccion_Cuenta_Monto_Correcto(){
		cuentaService.extraerDinero(cuenta.getId(),50000.0);
		assertEquals(50000.0,cuentaService.getById(cuenta.getId()).getSaldo());

	}

	@Test
	public void Realizar_Extraccion_Hasta_Cuenta_Este_Vacia(){
		cuentaService.extraerDinero(cuenta.getId(),50000.0);
		cuentaService.extraerDinero(cuenta.getId(),50000.0);
		assertEquals(0.0,cuentaService.getById(cuenta.getId()).getSaldo());
	}

	@Test
	public void Realizar_Extraccion_Monto_Mayor_A_Saldo(){
		assertThrows(CuentaSaldoInsuficienteException.class, () -> {
			cuentaService.extraerDinero(cuenta.getId(),150000.0);
		});
	}

	@Test
	public void Realizar_Extraccion_Monto_Mayor_A_Limite_Extraccion(){
		assertThrows(CuentaLimiteExtraccionExcedidoException.class, () -> {
			cuentaService.extraerDinero(cuenta.getId(),60000.0);
		});
	}

	@Test
	public void Realizar_Deposito_Cuenta(){
		cuentaService.depositarDinero(cuenta.getId(),50000.0);
		assertEquals(150000.0,cuentaService.getById(cuenta.getId()).getSaldo());
	}

	@Test
	public void Obtener_Movimientos_Cuenta(){
		cuentaService.depositarDinero(cuenta.getId(),10000.0);
		cuentaService.extraerDinero(cuenta.getId(),10000.0);
		assertEquals(2,cuentaService.getMovimientos(cuenta.getId()).size());
	}


}
