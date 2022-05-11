package org.banking.mbankingbackend.services.exception;

public class BalanceNotSufficentException extends Exception{
    public BalanceNotSufficentException(String message) {
        super(message);
    }
}
