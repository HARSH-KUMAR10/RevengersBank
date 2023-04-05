package client;

import client.controller.ClientAccountController;
import models.SocketControllers;
import models.Utilities;

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
            exception.printStackTrace();
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
            exception.printStackTrace();
        }

    }
}
