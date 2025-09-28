package com.MposGlobal.Inventario_JuanNavarro;

public class BusinessException extends Exception {

    private final int status;

    public BusinessException(String mensaje, int status) {
        super(mensaje);
        this.status = status;
    }

    public BusinessException(Throwable t, String mensaje, int status) {
        super(mensaje,t);
        this.status = status;
    }

    public int getStatus(){
        return status;
    }

}
