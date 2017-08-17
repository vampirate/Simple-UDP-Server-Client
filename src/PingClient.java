import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.Timestamp;

public class PingClient {

    public static void main(String[] args) throws Exception {

    	if (args.length != 2) {
    		System.out.println("Required more arguments!");
    		return;
	}

	int port = Integer.parseInt(args[1]);	
	InetAddress IPAddress = InetAddress.getByName(args[0]);

	DatagramSocket clientSocket = new DatagramSocket(); 
	clientSocket.setSoTimeout(1000);

	byte[] sendData = new byte[1024]; 
	byte[] receiveData = new byte[1024];

	for (int count = 1; count < 11; count++) {
		long startTime = System.currentTimeMillis();
		String sentence = "PING " + count + startTime + "\r\n"; 
		sendData = sentence.getBytes();         
		DatagramPacket sendPacket = 
		new DatagramPacket(sendData, sendData.length, IPAddress, port); 
	
		clientSocket.send(sendPacket); 

		try {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
			clientSocket.receive(receivePacket); 
			long endTime = System.currentTimeMillis();
			System.out.println("ping to " + args[0] + ", seq = " + count + ", rtt = " + (endTime - startTime) + " ms");
		} catch (SocketTimeoutException e) {
			System.out.println("ping to " + args[0] + ", seq = " + count + ", rtt = timeout");
		}

		}
	clientSocket.close(); 
	} 
}