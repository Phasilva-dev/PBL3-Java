package model.exception;

public class LoginFalhouException extends Exception{
    public LoginFalhouException (String mensagem){
        super(mensagem);
    }
}
