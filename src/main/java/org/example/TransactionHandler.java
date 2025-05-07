package org.example;

public interface TransactionHandler {
    boolean processTransaction(Buyer buyer, Product item);
}
