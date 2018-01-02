package model;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogIn {
	public static JTextField play1;
	public static JTextField play2;
	
	public void initFrame()
	{
		final JFrame myFrame=new JFrame();
		myFrame.setTitle("开始游戏");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(false);
		myFrame.setSize(600,400); 
		myFrame.setResizable(false);
		myFrame.setLocationRelativeTo(null);
		myFrame.getContentPane().setLayout(null);
		
		play1 = new JTextField();
		play1.setFont(new Font("宋体", Font.PLAIN, 24));
		play1.setBounds(281, 40, 178, 51);
		play1.setVisible(true);
		myFrame.getContentPane().add(play1);
		play1.setColumns(10);
		
		JLabel player1 = new JLabel("玩家一姓名：");
		player1.setFont(new Font("宋体", Font.PLAIN, 24));
		player1.setBounds(122, 40, 144, 51);
		myFrame.getContentPane().add(player1);
		
		JLabel player2 = new JLabel("玩家二姓名：");
		player2.setFont(new Font("宋体", Font.PLAIN, 24));
		player2.setBounds(122, 106, 144, 51);
		myFrame.getContentPane().add(player2);
		
		play2 = new JTextField();
		play2.setFont(new Font("宋体", Font.PLAIN, 24));
		play2.setColumns(10);
		play2.setBounds(281, 106, 178, 51);
		myFrame.getContentPane().add(play2);
		
		JButton startgame = new JButton("开始游戏");
		startgame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Game();
				myFrame.dispose();
			}
		});
		startgame.setFont(new Font("宋体", Font.PLAIN, 22));
		startgame.setBounds(194, 280, 168, 51);
		myFrame.getContentPane().add(startgame);
		
		myFrame.setVisible(true);
	}
	
}
