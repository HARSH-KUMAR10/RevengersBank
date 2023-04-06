package client.controller;

import client.validations.AccountValidation;
import client.validations.BankValidation;
import models.SocketControllers;
import models.UserSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientBankController
{

    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    private static String getAmount(){
        try
        {

            System.out.print("Enter amount: ");

            String amount = bufferedReader.readLine();

            if (!amount.contains("."))
            {
                amount += ".00";
            }

            while (!BankValidation.validateAmount(amount))
            {
                System.out.print("Wrong input, please enter amount(ex: 10000.00) again: ");

                amount = bufferedReader.readLine();

                if (!amount.contains("."))
                {
                    amount += ".00";
                }
            }
            return amount;
        }catch (Exception exception){
            exception.printStackTrace();
            return "";
        }
    }

    public static void deposit(UserSession userSession, SocketControllers socketControllers)
    {
        try
        {

            socketControllers.writer.println(socketControllers.socket.getLocalSocketAddress().toString()
                                             + "==Bank::Deposit->" + userSession.getSessionId() + ";"
                                             + userSession.getEmail() + ";" + getAmount());

            String response = socketControllers.reader.readLine();

            System.out.println(response);

        }
        catch (Exception exception)
        {

            exception.printStackTrace();

        }
    }

    public static void withdrawal(UserSession userSession, SocketControllers socketControllers)
    {
        try
        {

            socketControllers.writer.println(socketControllers.socket.getLocalSocketAddress().toString()
                                             + "==Bank::Withdrawal->" + userSession.getSessionId() + ";"
                                             + userSession.getEmail() + ";" + getAmount());

            String response = socketControllers.reader.readLine();

            System.out.println("==================================");

            System.out.println(response);

            System.out.println("==================================");


        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void getAccountDetail(UserSession userSession, SocketControllers socketControllers){
        try{
            socketControllers.writer
                    .println(socketControllers.socket.getLocalSocketAddress().toString()
                             + "==Bank::Details->" + userSession.getSessionId() + ";"
                             + userSession.getEmail());

            String response ;

            System.out.println("==================================");

            while(!(response = socketControllers.reader.readLine()).equalsIgnoreCase(";;"))
            {
                System.out.println(response);
            }

            System.out.println("==================================");

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void fundTransfer(UserSession userSession, SocketControllers socketControllers){
        try
        {
            System.out.println("Enter receiver AccNo.: ");
            String receiverAccountNo = bufferedReader.readLine();
            while(!BankValidation.validateAccountNumber(receiverAccountNo)){
                System.out.println("Wrong input, please enter AccNo. again: ");
                receiverAccountNo = bufferedReader.readLine();
            }

            System.out.println("Enter receiver email: ");
            String receiverEmail = bufferedReader.readLine();
            while(AccountValidation.validateEmail(receiverEmail)){
                System.out.println("Wrong input, please enter email again: ");
                receiverEmail = bufferedReader.readLine();
            }

            socketControllers.writer.println(socketControllers.socket.getLocalSocketAddress().toString()
                                             + "==Bank::Transfer->" + userSession.getSessionId() + ";"
                                             + userSession.getEmail() + ";" + receiverAccountNo + ";"
                                             + receiverEmail + ";" + getAmount());

            String response;

            System.out.println("==================================");

            while(!(response = socketControllers.reader.readLine()).equalsIgnoreCase(";;"))
            {
                System.out.println(response);
            }

            System.out.println("==================================");


        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
