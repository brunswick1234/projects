import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by dell on 4/7/2015.
 */
public class Broker
{
    private static ServerSocket brokerSocket;
    private static final int PORT = 1234;



    public static void main(String[] args) throws IOException
    {
        try
        {
            brokerSocket = new ServerSocket(PORT);
        } catch (IOException e)
        {
            e.printStackTrace();
        }





        do
        {
            Socket client = brokerSocket.accept();
            System.out.println("New client accepted");
            HashMap<String,String[]> hotelAddressMap = readHotelAddress("hotel.txt");
            ClientHandler handler = new ClientHandler(client,hotelAddressMap);
            handler.start();
        } while (true);
    }



    private static HashMap<String,String[]> readHotelAddress(String fileName)
    {

        HashMap<String,String[]> hotelAddressMap = new HashMap<>();
        try
        {
            FileReader fileReader = new FileReader("hotel.txt");
            Scanner parser = new Scanner(fileReader);
            while (parser.hasNext())
            {
                String[] words = parser.nextLine().split(",");
                String[] hostAndPort = new String[2];
                hostAndPort[0] = words[1];
                System.out.println(words[1]);
                hostAndPort[1] = words[2];
                System.out.println(words[2]);
                hotelAddressMap.put(words[0],hostAndPort);
            }
        } catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        for(String[] address: hotelAddressMap.values())
        {
            System.out.println(address[0] + address[1]);
        }


            return hotelAddressMap;
    }




}
