package client.controller;

import model.Utilities;
import validation.AccountValidation;
import validation.BankValidation;
import model.SocketControllers;
import model.UserSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientBankController
{

    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    private static final String DOT = ".";

    private static final String DEFAULT_DECIMAL = ".00";

    private static String getAmount()
    {
        try
        {

            System.out.print("Enter amount: ");

            String amount = bufferedReader.readLine();

            if (!amount.contains(DOT))
            {
                amount += DEFAULT_DECIMAL;
            }

            while (!BankValidation.validateAmount(amount))
            {
                System.out.print("Wrong input, please enter amount(ex: 10000.00) again: ");

                amount = bufferedReader.readLine();

                if (!amount.contains(DOT))
                {
                    amount += DEFAULT_DECIMAL;
                }
            }
            return amount;
        }
        catch (Exception exception)
        {
            return "0.0";
        }
    }

    public static void deposit(UserSession userSession, SocketControllers socketControllers)
    {
        try
        {

            socketControllers.writer.println(socketControllers.socket.getLocalSocketAddress().toString()
                                             + Utilities.DOUBLE_EQUAL_DELIMITER + Utilities.API_ACTION_BANK
                                             + Utilities.DOUBLE_COLON_DELIMITER + Utilities.DEPOSIT
                                             + Utilities.ARROW_DELIMITER + userSession.getSessionId()
                                             + Utilities.SEMI_COLON_DELEMITER + userSession.getEmail()
                                             + Utilities.SEMI_COLON_DELEMITER + getAmount());

            String response = socketControllers.reader.readLine();

            System.out.println(Utilities.OUTPUT_DIVIDER);

            System.out.println(response);

            System.out.println(Utilities.OUTPUT_DIVIDER);

        }
        catch (Exception exception)
        {
            System.out.println("Error occurred, please restart.");
        }
    }

    public static void withdrawal(UserSession userSession, SocketControllers socketControllers)
    {
        try
        {

            socketControllers.writer.println(socketControllers.socket.getLocalSocketAddress().toString()
                                             + Utilities.DOUBLE_EQUAL_DELIMITER + Utilities.API_ACTION_BANK
                                             + Utilities.DOUBLE_COLON_DELIMITER + Utilities.WITHDRAWAL
                                             + Utilities.ARROW_DELIMITER + userSession.getSessionId()
                                             + Utilities.SEMI_COLON_DELEMITER + userSession.getEmail()
                                             + Utilities.SEMI_COLON_DELEMITER + getAmount());

            String response = socketControllers.reader.readLine();

            System.out.println(Utilities.OUTPUT_DIVIDER);

            System.out.println(response);

            System.out.println(Utilities.OUTPUT_DIVIDER);


        }
        catch (Exception exception)
        {
            System.out.println("Error occurred, please restart.");
        }
    }

    public static void getAccountDetail(UserSession userSession, SocketControllers socketControllers)
    {
        try
        {
            socketControllers.writer
                    .println(socketControllers.socket.getLocalSocketAddress().toString()
                             + Utilities.DOUBLE_EQUAL_DELIMITER + Utilities.API_ACTION_BANK
                             + Utilities.DOUBLE_COLON_DELIMITER + Utilities.DETAILS
                             + Utilities.ARROW_DELIMITER + userSession.getSessionId()
                             + Utilities.SEMI_COLON_DELEMITER + userSession.getEmail());

            String response;

            System.out.println(Utilities.OUTPUT_DIVIDER);

            while (!(response = socketControllers.reader.readLine()).equalsIgnoreCase(Utilities.DOUBLE_SEMI_COLON_DELEMITER))
            {
                System.out.println(response);
            }

            System.out.println(Utilities.OUTPUT_DIVIDER);

        }
        catch (Exception exception)
        {
            System.out.println("Error occurred, please restart.");
        }
    }

    public static void fundTransfer(UserSession userSession, SocketControllers socketControllers)
    {
        try
        {
            System.out.print("Enter Receiver AccNo.: ");

            String receiverAccountNo = bufferedReader.readLine();

            while (!BankValidation.validateAccountNumber(receiverAccountNo))
            {

                System.out.print("Wrong input, Please enter AccNo. again: ");

                receiverAccountNo = bufferedReader.readLine();

            }

            System.out.print("Enter receiver email: ");

            String receiverEmail = bufferedReader.readLine();

            while (AccountValidation.validateEmail(receiverEmail))
            {

                System.out.print("Wrong input, please enter email again: ");

                receiverEmail = bufferedReader.readLine();

            }

            socketControllers.writer.println(socketControllers.socket.getLocalSocketAddress().toString()
                                             + Utilities.DOUBLE_EQUAL_DELIMITER + Utilities.API_ACTION_BANK
                                             + Utilities.DOUBLE_COLON_DELIMITER + Utilities.TRANSFER
                                             + Utilities.ARROW_DELIMITER + userSession.getSessionId()
                                             + Utilities.SEMI_COLON_DELEMITER + userSession.getEmail()
                                             + Utilities.SEMI_COLON_DELEMITER + receiverAccountNo
                                             + Utilities.SEMI_COLON_DELEMITER + receiverEmail
                                             + Utilities.SEMI_COLON_DELEMITER + getAmount());

            String response;

            System.out.println(Utilities.OUTPUT_DIVIDER);

            while (!(response = socketControllers.reader.readLine()).equalsIgnoreCase(Utilities.DOUBLE_SEMI_COLON_DELEMITER))
            {
                System.out.println(response);
            }

            System.out.println(Utilities.OUTPUT_DIVIDER);


        }
        catch (Exception exception)
        {
            System.out.println("Error occurred, please restart.");
        }
    }
}
