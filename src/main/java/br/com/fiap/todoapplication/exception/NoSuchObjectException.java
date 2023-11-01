package br.com.fiap.todoapplication.exception;

public class NoSuchObjectException extends Exception{
    public NoSuchObjectException() {
        super();
    }

    public NoSuchObjectException(String message) {
        super(message);
    }
}
