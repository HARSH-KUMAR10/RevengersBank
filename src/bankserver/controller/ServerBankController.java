package bankserver.controller;

import bankserver.accounts.Accounts;

public class ServerBankController
{
    public static String deposit(String valuesString)
    {
        try
        {
            String[] values = valuesString.split(";");

            if (values.length == 3)
            {
                return Accounts.depositAmount(values[0], values[1], values[2]);
            }
            else
            {
                return "Unable to process request: check params";
            }
        }
        catch (Exception exception)
        {

            exception.printStackTrace();

            return "Server error: please try again.";

        }
    }

    public static String withdrawal(String valuesString)
    {
        try
        {
            String[] values = valuesString.split(";");

            if (values.length == 3)
            {
                return Accounts.withdrawAmount(values[0], values[1], values[2]);
            }
            else
            {
                return "Unable to process request: check params";
            }
        }
        catch (Exception exception)
        {

            exception.printStackTrace();

            return "Server error: please try again.";

        }
    }

    public static String details(String valuesString)
    {
        try
        {
            String[] values = valuesString.split(";");

            if (values.length == 2)
            {
                return Accounts.getDetails(values[0], values[1]);
            }
            else
            {
                return "Unable to process request: check params;;";
            }
        }
        catch (Exception exception)
        {

            exception.printStackTrace();

            return "Server error: please try again.;;";

        }
    }

    public static String fundTransfer(String valuesString)
    {
        try
        {
            String[] values = valuesString.split(";");

            if (values.length == 5)
            {
                return Accounts.transfer(values[0], values[1], values[2], values[3], values[4]);
            }
            else
            {
                return "Unable to process request: check params";
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return "Server error: please try again.";
        }
    }
}
