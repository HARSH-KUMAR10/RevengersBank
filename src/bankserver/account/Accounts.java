package bankserver.account;

import model.Account;

import java.util.concurrent.ConcurrentHashMap;

public class Accounts
{
    public static ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    public static String setAccount(String name, int age, String gender, String email, String pin, double balance)
    {
        try
        {
            Account account = new Account(name, age, gender, email, pin, balance);

            return accounts.putIfAbsent(account.getEmail(), account) == null ?
                    "Account created successfully" : "Account with this email already create, please login";
        }
        catch (Exception exception)
        {
            return "Server error: Unable to create account.";
        }
    }

    public static Account getAccount(String email, String pin)
    {
        try
        {
            Account account = accounts.get(email);

            if (account != null && account.checkPassword(pin))
            {
                return account;
            }
            else
            {
                return null;
            }
        }
        catch (Exception exception)
        {
            return null;
        }
    }

    public static String depositAmount(String userSessionId, String userSessionEmail, String amount)
    {
        try
        {
            Account account = accounts.get(userSessionEmail);

            if (account.getSessionId().equalsIgnoreCase(userSessionId))
            {
                if (account.deposit(Double.parseDouble(amount)))
                {
                    return "Deposited amount. Updated balance: " + account.getBalance();
                }
                else
                {
                    return "Server error: unable to update balance";
                }
            }
            else
            {
                return "Authentication failed";
            }
        }
        catch (Exception exception)
        {
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

                if (account.getBalance() >= Double.parseDouble(amount))
                {

                    if (account.withdraw(Double.parseDouble(amount)))
                    {
                        return "Withdrawn amount. Updated balance: " + account.getBalance();
                    }
                    else
                    {
                        return "Server error: unable to update balance";
                    }

                }
                else
                {
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
            return "Server error: Unable to process request";
        }
    }

    public static String getDetails(String userSessionId, String userSessionEmail)
    {
        try
        {
            Account account = accounts.get(userSessionEmail);

            if (account.getSessionId().equalsIgnoreCase(userSessionId))
            {
                return "Account Details\nAccount Number: " + account.getAccountNumber()
                       + "\nName: " + account.getName() + "\nEmail: " + account.getEmail()
                       + "\nAge: " + account.getAge() + "\nGender: " + account.getGender()
                       + "\nBalance : " + account.getBalance() + "\n;;";
            }
            else
            {
                return "Authentication failed;;";
            }
        }
        catch (Exception exception)
        {
            return "Server error: Unable to process request;;";
        }
    }

    public static String transfer(String userSessionId, String userSessionEmail,
                                  String receiverAccountNumber, String receiverEmail, String amount)
    {
        try
        {
            Account fromAccount = accounts.get(userSessionEmail);
            Account toAccount = accounts.get(receiverEmail);

            if (fromAccount != null && toAccount != null
                && fromAccount.getSessionId().equals(userSessionId)
                && toAccount.getAccountNumber() == Integer.parseInt(receiverAccountNumber))
            {
                double amountTransfer = Double.parseDouble(amount);

                double fromAccountBalance = fromAccount.getBalance();

                double toAccountBalance = toAccount.getBalance();

                if (fromAccountBalance >= amountTransfer)
                {

                    fromAccountBalance -= amountTransfer;

                    toAccountBalance += amountTransfer;

                    fromAccount.setBalance(fromAccountBalance);

                    toAccount.setBalance(toAccountBalance);

                    return "Funds transfer update:\nFrom:" + userSessionEmail + "\nTo:" + receiverEmail + "\nAmount:" + amount + "\n;;";
                }
                else
                {
                    return "Insufficient balance\n;;";
                }
            }
            else
            {
                return "Transfer failed, ";
            }

        }
        catch (Exception exception)
        {
            return "Server error: Unable to process request";
        }
    }
}
