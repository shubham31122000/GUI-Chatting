import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class ChatServer
{
	ServerSocket server;
	public ChatServer()
	{
		try
		{
			server=new ServerSocket(2009);
			ArrayList <Socket> soclist=new ArrayList<Socket>();
			System.out.println("Server started..");
			while(true)
			{
				Socket soc=server.accept();
				soclist.add(soc);
				System.out.println("Client connected..");
				new ChatThread(soc,soclist).start();
			}
		}catch(Exception ex) { System.out.println(ex); }
	}
	public static void main(String args[])
	{
		new ChatServer();
	}
}
class ChatThread extends Thread
{
	Socket clientsoc;
	ArrayList <Socket> list;
	public ChatThread(Socket soc, ArrayList <Socket> list)
	{
		clientsoc=soc;
		this.list=list;
		System.out.println("Thread is created for this client...");
	}
	public void run()
	{
		while(true)
		{
			try
			{
				DataInputStream dis=new DataInputStream(clientsoc.getInputStream());
				String msg=dis.readUTF();
				for(Socket soc:list)
				{
					DataOutputStream dos=new DataOutputStream(soc.getOutputStream());
					if(soc==clientsoc)
					{
						msg=msg.substring(msg.indexOf(':'));
						msg="You said"+msg;
					}
					dos.writeUTF(msg);
				} 
			}catch(Exception ex) { System.out.println(ex); }
		}
	}
}