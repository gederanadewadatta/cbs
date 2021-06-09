package id.danafix.cbs.validation;

import id.danafix.cbs.api.ApiErrorResponse;
import id.danafix.cbs.entity.Account;
import id.danafix.cbs.enumeration.EValidationResponse;
import id.danafix.cbs.exceptions.CoreException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AccountValidation  {

    public AccountValidation(){}

    public Mono<Account> validate(Account account) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        if (!this.validateAccount(account)) {
            apiErrorResponse.setError(EValidationResponse.VALIDATION_ERROR_ACCOUNT_PARAM);
            return Mono.error(new CoreException(apiErrorResponse));
        }
        return Mono.just(account);
    }

    public Mono<Account> validateAll(Account account) {
        return validate(account)
                .flatMap(obj -> {
                    if (!this.validateId(obj)) {
                        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
                        apiErrorResponse.setError(EValidationResponse.VALIDATION_ERROR_ID_PARAM);
                        return Mono.error(new CoreException(apiErrorResponse));
                    }
                    return Mono.just(obj);
                });
    }

    private boolean validateAccount(Account account) {
        return !(account.getBranchNumber().isEmpty() || account.getAccountNumber().isEmpty() || account.getAccountType().isEmpty());
    }

    private boolean validateId(Account account){
        return !(account.getId().isEmpty());
    }

}
