package bankserver.accounts;

import models.Account;

import java.util.concurrent.ConcurrentHashMap;

public class Accounts
{
    public static ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    public static boolean setAccount(String name, int age, String gender, String email, String pin, double balance)
    {
        try
        {
            Account account = new Account(name, age, gender, email, pin, balance);

            return accounts.putIfAbsent(account.getEmail(), account)==null;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }

    public static Account getAccount(String email, String pin)
    {
        try
        {
            Account account = accounts.get(email);

            if (account.checkPassword(pin))
            {
                return account;
            }
            else
            {
                return new Account();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return new Account();
        }
    }

    public static String depositAmount(String userSessionId, String userSessionEmail, String amount)
    {
        try
        {
            Account account = accounts.get(userSessionEmail);

            if (account.getSessionId().equalsIgnoreCase(userSessionId))
            {
                account.deposit(Double.parseDouble(amount));


                return "Deposited amount. Updated balance: " + account.getBalance();
            }
            else
            {
                return "Authentication failed";
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return "Server error: Unable to process request";
        }
    }
    public static String withdrawAmount(String userSessionId, String userSessionEmail, String amount)
    {
        try
        {
            Account account = accounts.get(userSessionEmail);

            if (account.getSessionId().equalsIgnoreCase(userSessionId))
            {

                if(account.getBalance()>=Double.parseDouble(amount))
                {

                    account.withdraw(Double.parseDouble(amount));

                    System.out.println("================ Balance Update ================");

                    System.out.println("Updated balance: " + account.getBalance());

                    System.out.println("================================================");

                    return "Withdrawn amount. Updated balance: " + account.getBalance();

                }else{
                    return "Insufficient balance.";
                }
            }
            else
            {
                return "Authentication failed";
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return "Server error: Unable to process request";
        }
    }

    public static String getDetails(String userSessionId, String userSessionEmail){
        try{
            Account account = accounts.get(userSessionEmail);

            if (account.getSessionId().equalsIgnoreCase(userSessionId))
            {
                return "Account Details\nAccount Number: "+account.getAccountNumber()
                       +"\nName: "+account.getName()+"\nEmail: "+account.getEmail()
                       +"\nBalance : "+account.getBalance()+"\n;;";
            }
            else
            {
                return "Authentication failed";
            }
        }catch (Exception exception){
            exception.printStackTrace();
            return "Server error: Unable to process request";
        }
    }

    public static String transfer(String userSessionId, String userSessionEmail,String receiverAccountNumber,String receiverEmail, String amount)
    {
        try
        {
            Account fromAccount = accounts.get(userSessionEmail);
            Account toAccount = accounts.get(receiverEmail);
            double amountTransfer = Double.parseDouble(amount);

            double fromAccountBalance = fromAccount.getBalance();
            double toAccountBalance = toAccount.getBalance();

            fromAccountBalance -= amountTransfer;
            toAccountBalance += amountTransfer;

            fromAccount.setBalance(fromAccountBalance);
            toAccount.setBalance(toAccountBalance);

            return "Funds transfer update:\nFrom:"+userSessionEmail+"\nTo:"+receiverEmail+"\nAmount:"+amount+"\n;;";

        }catch (Exception exception){
            exception.printStackTrace();
            return "Server error: Unable to process request";
        }
    }
}
