package id.danafix.cbs.service;

import id.danafix.cbs.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {
    Flux<Account> findAll();
    Mono<Account> findById(String id);
    Mono<Account> save(Account account);
    Mono<Account> delete(Account account);

    Mono<Account> findByOpeningDate(String openingDate);

    Mono<Account> findByStatus(String accountStatus);

    Flux<Account> insert(Mono<Account> bodyToMono);
}
