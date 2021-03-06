package id.danafix.cbs.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.danafix.cbs.enumeration.EValidationResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse<T> extends ApiResponse<T> {

    private EValidationResponse error;

    public ApiErrorResponse() {
        super(null);
        this.error = EValidationResponse.VALIDATION_ERROR_GENERIC;
    }

    public ApiErrorResponse(String error) {
        super(null);
        this.error = EValidationResponse.VALIDATION_ERROR_GENERIC;
        this.error.setMsg(error);
    }

    public ApiErrorResponse(EValidationResponse error) {
        super(null);
        this.error = error;
    }

    public EValidationResponse getError() {
        return error;
    }

    public void setError(EValidationResponse error) {
        this.error = error;
    }

    public void setErrorMessage(String message) {
        this.error.setMsg(message);
    }
}

