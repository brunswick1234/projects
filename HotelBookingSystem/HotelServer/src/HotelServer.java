import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by dell on 4/7/2015.
 */
public class HotelServer
{
    private static ServerSocket hotelServerSocket;
    private static String hotelName;
    private static String puName;


    public static void main(String[] args) throws IOException
    {
                

        int port = Integer.parseInt(args[0]);
        hotelName = args[1];
        puName = args[2];
        try
        {
            hotelServerSocket = new ServerSocket(port);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        do
        {
            Socket client = hotelServerSocket.accept();
            System.out.println("new client accepted");
            BrokerHandler brokerHandler = new BrokerHandler(client,hotelName,puName);
            brokerHandler.start();
        } while (true);
    }





}

