import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.net.*;

public class ChatClient extends JFrame {
	JButton bt=new JButton("SEND");
	JTextField tb=new JTextField();
	JTextArea ta=new JTextArea();
	Socket soc;
	String name="Anonymous";
	
	public ChatClient() {
		super("Client Chat");
		setSize(440,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		addComp();
		createConnection();
		setVisible(true);
	}
	
	private void addComp() {
		JScrollPane pa=new JScrollPane(ta);
		pa.setBounds(20,20,390,320);
		add(pa);
		tb.setBounds(20,370,240,35);
		add(tb);
		bt.setBounds(275,370,120,35);
		add(bt);
		bt.addActionListener(new SendListener());
	}
	
	private void createConnection() {
		try {
			soc=new Socket("localhost",2009);
			JOptionPane.showInputDialog(ChatClient.this,"Enter your name:");
			new ReadMessage().start();
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	class SendListener implements ActionListener {
		
		public void actionPerformed(ActionEvent evt) {
			try {
				DataOutputStream dos=new DataOutputStream(soc.getOutputStream());
				dos.writeUTF(name+" said:"+tb.getText());
				tb.setText("");
			}
			catch(Exception ex) {
				System.out.println(ex);
			}
		}
	}
	
	class ReadMessage extends Thread {
		
		public void run() {
			try {
				DataInputStream dis=new DataInputStream(soc.getInputStream());
				while(true) {
					String message=dis.readUTF();
					ta.append(message+"\n");
				}
			}
			catch(Exception ex) {
				System.out.println(ex);
			}
		}
	}
	public static void main(String[] args) {
		new ChatClient();
	}

}
