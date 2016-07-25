package com.client.tools;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main extends JFrame implements ActionListener
{
	private JTextField dict = new JTextField();
	private JTextField connectionField = new JTextField();
	private JTextField dbName = new JTextField();
	private JTextField packageField = new JTextField();
	private JTextField pathField = new JTextField();
	private JTextArea outputArea = new JTextArea();
	private JButton button;
	private JTextField userName = new JTextField();
	private JTextField password = new JTextField();
	private DataGenerator generator;
	public Main()
	{
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		this.getContentPane().add("North", topPanel);
		
		topPanel.setPreferredSize(new Dimension(600, 220));
		
		
		int y = 10;
		JLabel label = new JLabel("driver:");
		topPanel.add(label);		
		label.setBounds(10,  y, 120, 30);
		topPanel.add(this.dict);
		this.dict.setBounds(130, y, 300, 25);
		y+= 30;
		
		label = new JLabel("connection:");
		topPanel.add(label);		
		label.setBounds(10,  y, 120, 30);
		topPanel.add(this.connectionField);
		this.connectionField.setBounds(130, y, 300, 25);
		y+= 30;
		
		label = new JLabel("user name:");
		topPanel.add(label);		
		label.setBounds(10,  y, 120, 30);
		topPanel.add(this.userName);
		this.userName.setBounds(130, y, 300, 25);
		y+= 30;
		
		label = new JLabel("password:");
		topPanel.add(label);		
		label.setBounds(10,  y, 120, 30);
		topPanel.add(this.password);
		this.password.setBounds(130, y, 300, 25);
		y+= 30;
		
		label = new JLabel("db name:");
		topPanel.add(label);		
		label.setBounds(10,  y, 120, 30);
		topPanel.add(this.dbName);
		this.dbName.setBounds(130, y, 300, 25);
		y+= 30;		
		
		label = new JLabel("package name:");
		topPanel.add(label);		
		label.setBounds(10,  y, 120, 30);
		topPanel.add(this.packageField);
		this.packageField.setBounds(130, y, 300, 25);
		label = new JLabel("save path:");
		topPanel.add(label);
		y += 30;
		label.setBounds(10,  y,  120,  30);
		pathField = new JTextField();
		topPanel.add(pathField);
		pathField.setBounds(130,  y,  300, 25);
		button = new JButton("Run");
		topPanel.add(button);
		button.setBounds(450,  y, 120, 30);
		JScrollPane sp = new JScrollPane(this.outputArea);
		this.getContentPane().add("Center", sp);
		
		button.addActionListener(this);
		this.pathField.setText("D:/autocode/zrldemo");
		this.packageField.setText("com.server");
		//this.connectionField.setText("jdbc:oracle:thin:@192.168.70.17:1521:WIGDBRD");
		this.connectionField.setText("jdbc:oracle:thin:@127.0.0.1:1521:WIGDBRD");
		this.dbName.setText("0");
		this.userName.setText("ZRLDEMO");
		this.password.setText("ZRLDEMO");
		this.dict.setText("oracle.jdbc.driver.OracleDriver");
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.button)
		{
			generate();
		}		
	}
	
	private void generate()
	{
		this.generator = new DataGenerator(this.dict.getText(), this.packageField.getText(), this.pathField.getText(), this.connectionField.getText(), this.dbName.getText(), this.userName.getText(), this.password.getText());
		generator.loadFromDbMetas();
		generator.create("poco","poco","Poco.java","poco.ftl");
		generator.create("pojo","pojo",".java","pojo.ftl");
		generator.create("dao","dao","Dao.java","dao.ftl");
		if("0".equals(this.dbName.getText()))
			generator.create("action","action","Action.java","action.ftl");
		else
			generator.create("action","action","Action.java","viewaction.ftl");
		generator.create("pages","pages",".jsp","jsp.ftl");
		generator.create("pages","pages",".js","js.ftl");
	}

	public static void main(String args[])
	{
		Main main = new Main();
		main.setVisible(true);
	}

}
