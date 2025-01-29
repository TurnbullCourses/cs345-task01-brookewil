package edu.ithaca.dturnbull.bank;

import java.math.BigDecimal;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if it is larger than the balance
     * @throws IllegalArgumentException if amount is negative
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        else {
            String emailRegex = "^(?!.*[._%+-]{2})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            return email.matches(emailRegex);
        }
    }

    /*
     * returns true if the amount is positive and has two decimal points or less, and false otherwise. 
     */
    public static boolean isAmountValid(double amount){
        if (amount < 0 || BigDecimal.valueOf(amount).scale() > 2){
            return false;
        }
        return true;
    }

    /**
     * @post adds to the balance by the specified amount
     * @throws IllegalArgumentException if amount is negative
     */
    public void deposit(double amount){
        if (amount > 0 && BigDecimal.valueOf(amount).scale() < 2){
            balance += amount;
        }
        else {
            throw new IllegalArgumentException("Amount must be positive");
        }
        
    }

    /**
     * @post transfers the specified amount from this account to another account
     * @throws InsufficientFundsException if it is larger than the balance
     * @throws IllegalArgumentException if amount is negative
     */
    public void transfer(BankAccount account, double amount) throws InsufficientFundsException{
        if (amount <= balance && BigDecimal.valueOf(amount).scale() < 2){
            balance -=amount;
            account.deposit(amount);
        }
        else if (amount < 0 || BigDecimal.valueOf(amount).scale() > 2) {
            throw new IllegalArgumentException("Amount must be positive");
        } else {
            throw new InsufficientFundsException("Not enough money");
        }
    }
}