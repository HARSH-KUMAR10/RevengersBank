package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketControllers
{
    public Socket socket;

    public BufferedReader reader;

    public PrintWriter writer;


    public SocketControllers(Socket socket)
    {
        try
        {
            this.socket = socket;

            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.writer = new PrintWriter(socket.getOutputStream(),true);

            System.out.println("reader-writer connected for "+socket.getRemoteSocketAddress());
        }
        catch (Exception exception)
        {
            System.out.println("Error occured: please restart.");
        }
    }
}
