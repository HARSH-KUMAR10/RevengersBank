package bankserver.controller;

import bankserver.account.Accounts;
import model.Utilities;
import validation.AccountValidation;
import validation.BankValidation;

public class ServerBankController
{


    // Bank::Deposit -> /10.20.40.194:91729==Bank::Deposit->sessionId;email;amount
    public static String deposit(String valuesString)
    {
        try
        {
            String[] values = valuesString.split(Utilities.SEMI_COLON_DELEMITER);

            if (values.length == 3 && AccountValidation.validateSessionId(values[0])
                && !AccountValidation.validateEmail(values[1]) && BankValidation.validateAmount(values[2]))
            {
                return Accounts.depositAmount(values[0], values[1], values[2]);
            }
            else
            {
                return Utilities.WRONG_INPUT;
            }
        }
        catch (Exception exception)
        {
            return Utilities.SERVER_ERROR;
        }
    }

    // Bank::Withdraw -> /10.20.40.194:89174==Bank::Withdrawal->sessionId;email;amount
    public static String withdrawal(String valuesString)
    {
        try
        {
            String[] values = valuesString.split(Utilities.SEMI_COLON_DELEMITER);

            if (values.length == 3 && AccountValidation.validateSessionId(values[0])
                && !AccountValidation.validateEmail(values[1]) && BankValidation.validateAmount(values[2]))
            {
                return Accounts.withdrawAmount(values[0], values[1], values[2]);
            }
            else
            {
                return Utilities.WRONG_INPUT;
            }
        }
        catch (Exception exception)
        {
            return Utilities.SERVER_ERROR;
        }
    }

    // Bank::Details -> /10.20.40.194:89102==Bank::Details->sessionId;email
    // Response needs to be ended with ;;
    public static String details(String valuesString)
    {
        try
        {
            String[] values = valuesString.split(Utilities.SEMI_COLON_DELEMITER);

            if (values.length == 2 && AccountValidation.validateSessionId(values[0])
                && !AccountValidation.validateEmail(values[1]))
            {
                return Accounts.getDetails(values[0], values[1]);
            }
            else
            {
                return Utilities.WRONG_INPUT + Utilities.DOUBLE_SEMI_COLON_DELEMITER;
            }
        }
        catch (Exception exception)
        {
            return Utilities.SERVER_ERROR + Utilities.DOUBLE_SEMI_COLON_DELEMITER;
        }
    }


    // Bank::Transfer -> /10.20.40.194:89213==Bank::Transfer->sessionId;email;receiverAccNo;receiverEmail;amount
    public static String fundTransfer(String valuesString)
    {
        try
        {
            String[] values = valuesString.split(Utilities.SEMI_COLON_DELEMITER);

            if (values.length == 5 && AccountValidation.validateSessionId(values[0])
                && !AccountValidation.validateEmail(values[1]) && BankValidation.validateAccountNumber(values[2])
                && !AccountValidation.validateEmail(values[3]) && BankValidation.validateAmount(values[4]))
            {
                return Accounts.transfer(values[0], values[1], values[2], values[3], values[4]);
            }
            else
            {
                return Utilities.WRONG_INPUT;
            }
        }
        catch (Exception exception)
        {
            return Utilities.SERVER_ERROR;
        }
    }
}
