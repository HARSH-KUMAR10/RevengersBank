package models;


public class Account
{
    private static int uniqueAccountNumber = 1000;

    private int accountNumber = -1;

    private String name;

    private int age;

    private String gender;

    private String email;

    private String pin = "0000";

    private double balance = 0;

    public int getAccountNumber()
    {
        return accountNumber;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public String getGender()
    {
        return gender;
    }

    public String getEmail()
    {
        return email;
    }

    public double getBalance()
    {
        return balance;
    }

    public boolean checkPassword(String pin){
        return this.pin.equals(pin);
    }

    public boolean deposit(Double amountToAdd){
        try{
            balance+=amountToAdd;
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(Double amountToSubtract){
        try{
            balance-=amountToSubtract;
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }


    public Account(){}

    public Account(String name, int age, String gender, String email, String pin, double balance)
    {
        try
        {
            this.accountNumber = uniqueAccountNumber++;

            this.name = name;

            this.age = age;

            this.gender = gender;

            this.email = email;

            this.pin = pin;

            this.balance = balance;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
