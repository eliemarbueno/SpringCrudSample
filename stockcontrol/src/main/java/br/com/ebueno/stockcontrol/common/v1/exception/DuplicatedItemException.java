package br.com.ebueno.stockcontrol.common.v1.exception;

public class DuplicatedItemException extends Exception {
    public DuplicatedItemException(String message){
        super(message);
    }

    public DuplicatedItemException(String item, String field, String fieldValue){
        super(String.format("Exist another {0} with field {1} value as {2}", item, field, fieldValue));
    }
}
