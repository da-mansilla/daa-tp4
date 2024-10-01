package unju.daa.daa_agustin_mansilla_tp3;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import unju.daa.daa_agustin_mansilla_tp3.entity.Account;
import unju.daa.daa_agustin_mansilla_tp3.entity.Client;
import unju.daa.daa_agustin_mansilla_tp3.repository.AccountRepository;
import unju.daa.daa_agustin_mansilla_tp3.repository.ClientRepository;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DaaAgustinMansillaTp3ApplicationTests {
    public static Client client1;
    public static Client client2;


    public static Account account;
    public static Account account2;
    public static Account account3;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeAll
    public static void setUp() {
        System.out.println("Iniciando pruebas unitarias...");
        client1 = new Client();
        client1.setDni(12345678L);
        client1.setName("Dario Cruz");
        client1.setEmail("dario@gmail.com");
        client1.setAddress("Siempre Viva 123");
        client1.setState("Active");

        client2 = new Client();
        client2.setDni(12345678L);
        client2.setName("Leandro lopez");
        client2.setEmail("leandro@gmail.com");
        client2.setAddress("Rinconada 123");
        client2.setState("Active");

        account = new Account();
        account.setAccountNumber(100);
        account.setClient(client1);
        account.setEntryDate(new Date());
        account.setBalance(1000000.00);
        account.setStatus("Active");
        account.setExtractionLimit(500000.00);

        account2 = new Account();
        account2.setAccountNumber(100);
        account2.setClient(client2);
        account2.setEntryDate(new Date());
        account2.setBalance(1000000.00);
        account2.setStatus("Active");
        account2.setExtractionLimit(500000.00);

        account3 = new Account();
        account3.setAccountNumber(200);
        account3.setClient(client1);
        account3.setEntryDate(new Date());
        account3.setBalance(1000000.00);
        account3.setStatus("Active");
        account3.setExtractionLimit(500000.00);
    }

    // Test para probar que el numero de cuenta sea unico
    @Test
    public void Test_Unique_Account_Number() {
        assertThrows(Exception.class, () -> {
            accountRepository.save(account);
            accountRepository.save(account2);
        });
    }

    @Test
    public void Test_One_Account_Per_Client(){
        assertThrows(Exception.class, () -> {
            clientRepository.save(client1);
            accountRepository.save(account);
            accountRepository.save(account3);
        });
    }

    @Test
    public void Test_Create_Client() {
        clientRepository.save(client1);

        // Recuperar el cliente
        Client retrievedClient = clientRepository.findById(client1.getId()).orElse(null);

        assert retrievedClient != null;
        assertEquals(client1.getDni(), retrievedClient.getDni());
        assertEquals(client1.getName(), retrievedClient.getName());
        assertEquals(client1.getEmail(), retrievedClient.getEmail());
        assertEquals(client1.getAddress(), retrievedClient.getAddress());
        assertEquals(client1.getState(), retrievedClient.getState());
    }

    @Test
    public void Test_Create_Account() {
        clientRepository.save(client1);
        accountRepository.save(account);

        // Recuperar la cuenta
        Account retrievedAccount = accountRepository.findById(account.getId()).orElse(null);

        // Verificar los valores
        assert retrievedAccount != null;
        assertEquals(account.getAccountNumber(), retrievedAccount.getAccountNumber());
        assertEquals(account.getBalance(), retrievedAccount.getBalance());
        assertEquals(account.getStatus(), retrievedAccount.getStatus());
        assertEquals(account.getExtractionLimit(), retrievedAccount.getExtractionLimit());
    }

}
