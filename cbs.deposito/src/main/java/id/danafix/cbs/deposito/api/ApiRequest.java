package id.danafix.cbs.deposito.api;

public class ApiRequest<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}