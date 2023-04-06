package client;

import client.controller.ClientAccountController;
import model.SocketControllers;
import model.Utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client
{
    private SocketControllers socketControllers;

    public Client()
    {
        try
        {
            socketControllers = new SocketControllers(new Socket(Utilities.IP, Utilities.PORT));

            start();
        }
        catch (Exception exception)
        {
            System.out.println("Unable to connect to server, please restart.");
        }
    }

    private void start()
    {
        try
        {
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

            while (true)
            {
                System.out.print("1. Login\n2. Create Account\nEnter your choice: ");

                switch (userInputReader.readLine())
                {
                    case "1" -> ClientAccountController.login(socketControllers);

                    case "2" -> ClientAccountController.signUp(socketControllers);

                    default -> System.out.println("Wrong input");
                }
            }
        }
        catch (Exception exception)
        {
            System.out.println("Error occurred, please restart.");
        }

    }
}
