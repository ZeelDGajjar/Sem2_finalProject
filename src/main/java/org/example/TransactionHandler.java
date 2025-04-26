package org.example;

public interface TransactionHandler {
    public boolean processTransaction(Buyer buyer, Product item);
}
