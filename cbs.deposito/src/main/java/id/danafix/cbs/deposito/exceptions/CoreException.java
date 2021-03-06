package id.danafix.cbs.deposito.exceptions;


import id.danafix.cbs.deposito.api.ApiErrorResponse;

public class CoreException extends RuntimeException {

    private ApiErrorResponse errorResponse;

    public CoreException(String message) {
        this.errorResponse = new ApiErrorResponse<>(message);
    }

    public CoreException(ApiErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ApiErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ApiErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

}
