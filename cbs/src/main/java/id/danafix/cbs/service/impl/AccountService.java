package id.danafix.cbs.service.impl;

import id.danafix.cbs.api.ApiErrorResponse;
import id.danafix.cbs.entity.Account;
import id.danafix.cbs.enumeration.EValidationResponse;
import id.danafix.cbs.exceptions.CoreException;
import id.danafix.cbs.repository.AccountRepo;
import id.danafix.cbs.repository.AccountRepository;
import id.danafix.cbs.service.IAccountService;
import id.danafix.cbs.validation.AccountValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class AccountService implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private AccountRepository accountRepository;

    private AccountValidation accountValidation;

    private AccountRepo repo;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountValidation accountValidation, AccountRepo repo) {
        this.accountRepository = accountRepository;
        this.accountValidation = accountValidation;
        this.repo = repo;
    }


    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new CoreException("Account not found")))
                .onErrorResume(error -> {
                    logger.error("[ERROR] Searching for account id {} : {}", id, error.getMessage());
                    return Mono.error(
                            new CoreException(
                                    new ApiErrorResponse(EValidationResponse.VALIDATION_ERROR_ACCOUNT_NOT_FOUND)));
                });
    }

    @Override
    public Mono<Account> save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> delete(Account account) {
        return accountRepository.delete(account);
    }

    @Override
    public Mono<Account> findByOpeningDate(String openingDate) {
        return null;
    }

    @Override
    public Mono<Account> findByStatus(String accountStatus) {
        return null;
    }

    @Override
    public Flux<Account> insert(Mono<Account> bodyToMono) {
        return repo.insert(bodyToMono);
    }

    public Mono<Account> getCurrentBalance(Account accountFilter){
        logger.debug("Current balance executing : " + accountFilter.toString());
        return accountRepository.findByBranchAndId(accountFilter)
                .switchIfEmpty(Mono.error(
                        new CoreException(
                                new ApiErrorResponse(EValidationResponse.VALIDATION_ERROR_BALANCE_ACCOUNT))))
                .onErrorResume(error -> {
                    logger.error("[ERROR] Getting current balance : {}",  error.getMessage());
                    return Mono.error(
                            new CoreException(
                                    new ApiErrorResponse(EValidationResponse.VALIDATION_ERROR_GENERIC)));
                });
    }

    public Mono<Account> verifyAccountExistence(Account account){
        logger.debug("Verifing account existence. Branch : {} - Account number : {}",
                account.getBranchNumber(), account.getId());
        return accountValidation.validate(account)
                .flatMap(accountRepository::findByBranchAndId)
                .flatMap(accountValidation::validateAll)
                .onErrorResume(error -> {
                    logger.error("[ERROR] Verifing account existence : {}", error.getMessage());
                    return Mono.error(
                            new CoreException(
                                    new ApiErrorResponse(EValidationResponse.VALIDATION_ERROR_ACCOUNT_NOT_FOUND)));
                });
    }
}
