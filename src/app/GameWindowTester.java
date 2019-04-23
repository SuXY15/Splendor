package app;

import java.awt.Font;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GameWindowTester extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindowTester frame = new GameWindowTester();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameWindowTester() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(360, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel input = new JLabel("Input");
		input.setFont(new Font("Dialog", Font.BOLD, 14));
		input.setBounds(24, 30, 48, 16);
		contentPane.add(input);

		textField = new JTextField();
		textField.setBounds(80, 30, 64, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(160, 30, 144, 192);
		contentPane.add(textArea);

		String[] data = { "轩神", "健神", "狗蛋儿", "傻逼", "JAVA真好用(:" };
		@SuppressWarnings("unchecked")
		JList list = new JList(data);
		list.setBounds(24, 60, 120, 120);
		contentPane.add(list);

		JButton button = new JButton("确定");
		button.setBounds(40, 192, 80, 24);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("Input is:" + textField.getText() + "\nSelection is:" + list.getSelectedValue());
			}
		});
		contentPane.add(button);
	}
}
