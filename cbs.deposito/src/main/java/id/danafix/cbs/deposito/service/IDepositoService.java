package id.danafix.cbs.deposito.service;

import id.danafix.cbs.deposito.entity.Deposito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IDepositoService {
    Flux<Deposito> findAll();
    Mono<Deposito> findById(String id);
    Mono<Deposito> save(Deposito account);
    Mono<Deposito> delete(Deposito account);

    Mono<Deposito> findByAccountNumberAndBranchNumber(String accountNumber,String branchNumber);
    Mono<Deposito> getCurrentBalance(Deposito accountFilter);
}
