package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);                  // valid; testing for middle case

        BankAccount bankAccount2 = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount2.getBalance(), 0.001);                   // valid; testing for no balance

        BankAccount bankAccount3 = new BankAccount("a@b.com", -50);
        assertEquals(-50, bankAccount3.getBalance(), 0.001);                          // invalid; (can't start with negative balance and can't withdraw into negatives)
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance(), 0.001);      // valid; testing for middle case

        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance(), 0.001);       // valid; edge case (withdraw all money)

        bankAccount.withdraw(0);
        assertEquals(0, bankAccount.getBalance(), 0.001);       // valid; edge case (withdraw nothing)

        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));  // invalid; testing error (withdraw more than in account)
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100));          // invalid; testing error (withdraw negative amount) 
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertTrue(BankAccount.isEmailValid("cool_guy@aaa.org")); // checks allowed special character (_)
        assertFalse(BankAccount.isEmailValid(""));         // empty string
        assertFalse(BankAccount.isEmailValid("hello..@b.com")); // checks allowed special character in not allowed scenario (must be followed by letter or number)
        assertFalse(BankAccount.isEmailValid("okay#notcool@womp.edu")); // checks not allowed special character
        assertFalse(BankAccount.isEmailValid("everythinggoodhere@.a")); // missing email domain
        assertFalse(BankAccount.isEmailValid("@gmail.com")); //missing prefix
        
    }

    @Test
    void isAmountValid(){
        // List in comments the equivalence classes that are present.
        // Write individual checks for middle and border cases of each equivalence class

        assertTrue(BankAccount.isAmountValid(0));      // border
        assertTrue(BankAccount.isAmountValid(4.05));   
        assertTrue(BankAccount.isAmountValid(7.2));

        assertFalse(BankAccount.isAmountValid(-5.00));
        assertFalse(BankAccount.isAmountValid(10.098));

    }
    

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}