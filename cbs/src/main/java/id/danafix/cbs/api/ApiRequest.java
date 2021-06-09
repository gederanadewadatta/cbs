package id.danafix.cbs.api;

public class ApiRequest<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}