package id.danafix.cbs.deposito.repository;

import id.danafix.cbs.deposito.entity.Deposito;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface DepositoRepo extends ReactiveMongoRepository<Deposito,String> {
    Mono<Deposito> findByAccountNumberAndBranchNumber(String accountNumber, String branchNumber);

    Mono<Deposito> findDepositoByAccountNumberAndBranchNumber(String accountNumber, String branchNumber);

    Mono<Deposito> findDepositoByDueDateAndAccountNumber(Date dueDate, String accountNumber);
}
