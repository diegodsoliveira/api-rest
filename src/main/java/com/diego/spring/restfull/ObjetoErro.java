package com.diego.spring.restfull;

public class ObjetoErro {

    private String error;
    private String code;

    public ObjetoErro(String error, String code) {
        this.error = error;
        this.code = code;
    }

    public ObjetoErro() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
