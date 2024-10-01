package unju.daa.daa_agustin_mansilla_tp3.repository;

import org.springframework.data.repository.CrudRepository;
import unju.daa.daa_agustin_mansilla_tp3.entity.Account;

public interface AccountRepository  extends CrudRepository<Account, Long> {
}
