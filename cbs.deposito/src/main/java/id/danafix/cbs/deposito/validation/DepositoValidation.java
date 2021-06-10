package id.danafix.cbs.deposito.validation;

import id.danafix.cbs.deposito.api.ApiErrorResponse;
import id.danafix.cbs.deposito.entity.Deposito;
import id.danafix.cbs.deposito.enumeration.EValidationResponse;
import id.danafix.cbs.deposito.exceptions.CoreException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class DepositoValidation {
    public DepositoValidation() {
    }
    public Mono<Deposito> validate(Deposito deposito) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        if (!this.validateAccount(deposito)) {
            apiErrorResponse.setError(EValidationResponse.VALIDATION_ERROR_ACCOUNT_PARAM);
            return Mono.error(new CoreException(apiErrorResponse));
        }
        return Mono.just(deposito);
    }

    public Mono<Deposito> validateAll(Deposito deposito) {
        return validate(deposito)
                .flatMap(obj -> {
                    if (!this.validateId(obj)) {
                        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
                        apiErrorResponse.setError(EValidationResponse.VALIDATION_ERROR_ID_PARAM);
                        return Mono.error(new CoreException(apiErrorResponse));
                    }
                    return Mono.just(obj);
                });
    }

    private boolean validateAccount(Deposito deposito) {
        return !(deposito.getBranchNumber().isEmpty() || deposito.getAccountNumber().isEmpty() || deposito.getDueDate().before(new Date())
        ||deposito.getBalance().equals(new BigDecimal(0)));
    }

    private boolean validateId(Deposito deposito){
        return !(deposito.getId().isEmpty());
    }
}
