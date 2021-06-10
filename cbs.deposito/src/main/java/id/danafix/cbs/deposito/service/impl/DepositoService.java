package id.danafix.cbs.deposito.service.impl;

import id.danafix.cbs.deposito.api.ApiErrorResponse;
import id.danafix.cbs.deposito.entity.Deposito;
import id.danafix.cbs.deposito.enumeration.EValidationResponse;
import id.danafix.cbs.deposito.exceptions.CoreException;
import id.danafix.cbs.deposito.repository.DepositoRepo;
import id.danafix.cbs.deposito.repository.DepositoRepository;
import id.danafix.cbs.deposito.service.IDepositoService;
import id.danafix.cbs.deposito.validation.DepositoValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepositoService implements IDepositoService {

    private static final Logger logger = LoggerFactory.getLogger(DepositoService.class);

    private DepositoRepository depositoRepository;
    private DepositoValidation depositoValidation;
    private DepositoRepo repo;

    @Autowired
    public DepositoService(DepositoRepository depositoRepository, DepositoValidation depositoValidation, DepositoRepo repo) {
        this.depositoRepository = depositoRepository;
        this.depositoValidation = depositoValidation;
        this.repo = repo;
    }

    @Override
    public Flux<Deposito> findAll() {
        return depositoRepository.findAll();
    }

    @Override
    public Mono<Deposito> findById(String id) {
        return depositoRepository.findById(id)
                .switchIfEmpty(Mono.error(new CoreException("Account not found")))
                .onErrorResume(error -> {
                    logger.error("[ERROR] Searching for account id {} : {}", id, error.getMessage());
                    return Mono.error(
                            new CoreException(
                                    new ApiErrorResponse(EValidationResponse.VALIDATION_ERROR_ACCOUNT_NOT_FOUND)));
                });
    }

    @Override
    public Mono<Deposito> save(Deposito account) {

        return depositoRepository.save(account);
    }

    @Override
    public Mono<Deposito> delete(Deposito account) {
        return depositoRepository.delete(account);
    }

    @Override
    public Mono<Deposito> findByAccountNumberAndBranchNumber(String accountNumber,String branchNumber) {
        return repo.findDepositoByAccountNumberAndBranchNumber(accountNumber, branchNumber).switchIfEmpty(Mono.error(new CoreException("Account not found")))
                .onErrorResume(error -> {
                    logger.error("[ERROR] Searching for account number {} : {}", accountNumber, error.getMessage());
                    return Mono.error(
                            new CoreException(
                                    new ApiErrorResponse(EValidationResponse.VALIDATION_ERROR_ACCOUNT_NOT_FOUND)));
                });
    }

    @Override
    public Mono<Deposito> getCurrentBalance(Deposito accountFilter) {
        logger.debug("Current balance executing : " + accountFilter.toString());
        return depositoRepository.findByBranchAndDepositoNumber(accountFilter)
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


}
