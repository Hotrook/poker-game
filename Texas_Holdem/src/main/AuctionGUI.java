package main;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.LineBorder;

public class AuctionGUI extends JFrame{

	private JPanel contentPane;
	public JTextField txtPula;
	public JTextField txtStawka;
	//public Auction globalAuction;
	public JButton check;
	public JButton bet;
	public JButton call;
	public JButton raise;
	public JButton fold;
	public JButton allin;
	public JTextField turn;
	
	public JLabel playerCard1;
	public JLabel playerCard2;
	public JLabel card1;
	public JLabel card2;
	public JLabel card3;
	public JLabel card4;
	public JLabel card5;
	private JLabel lblCurrentBet;
	private JLabel lblCurrentPot;
	private int currentPlayerBet;
	

	public JTextField textField;
	public JTextField textField1;
	private JPanel panel4;
	private JPanel panel3;
	private JLabel lblNewLabel;
	public JLabel playerTokens;
	public JTextArea player1;
	public JTextArea player2;
	public JTextArea player3;
	public JTextArea player4;
	public JTextArea player5;
	public JTextArea player6;
	public JTextArea player7;
	public JTextArea player8;
	public JTextArea player9;
	public JTextArea player10;
	
	public List<JTextArea> players;
	
	
	 public String actionName;
	 public CountDownLatch latch = new CountDownLatch(1);
	 
	 
	/**
	 * Create the frame.
	 */
	public AuctionGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 537);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(20, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBackground(new Color(128, 0, 0));
		contentPane.add(panel, BorderLayout.SOUTH);
		
		check = new JButton("check");
		//check.setBackground(new Color(139, 0, 0));
		//check.setContentAreaFilled(false);
		//check.setOpaque(true);
		panel.add(check);
		
		bet = new JButton("bet");
		//bet.setBackground(new Color(139, 0, 0));
		//bet.setContentAreaFilled(false);
		//bet.setOpaque(true);
		panel.add(bet);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(8);
		
		call = new JButton("call");
		//call.setBackground(new Color(139, 0, 0));
		//call.setContentAreaFilled(false);
		//call.setOpaque(true);
		panel.add(call);
		
		raise = new JButton("raise");
		//raise.setBackground(new Color(139, 0, 0));
		//raise.setContentAreaFilled(false);
		//raise.setOpaque(true);
		panel.add(raise);
		
		textField1 = new JTextField();
		panel.add(textField1);
		textField1.setColumns(8);
		
		fold = new JButton("fold");
		//fold.setBackground(new Color(139, 0, 0));
		//fold.setContentAreaFilled(false);
		//fold.setOpaque(true);
		panel.add(fold);
		
		
		allin = new JButton("allin");
		//allin.setBackground(new Color(139, 0, 0));
		//allin.setContentAreaFilled(false);
		//allin.setOpaque(true);
		panel.add(allin);
		
		JPanel panel1 = new JPanel();
		panel1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel1.setBackground(new Color(128, 0, 0));
		contentPane.add(panel1, BorderLayout.NORTH);
		
		lblCurrentBet = new JLabel("Current Bet");
		panel1.add(lblCurrentBet);
		
		txtStawka = new JTextField();
		txtStawka.setEditable(false);
		txtStawka.setText("BET");
		panel1.add(txtStawka);
		txtStawka.setColumns(10);
		
		lblCurrentPot = new JLabel("Current Pot");
		panel1.add(lblCurrentPot);
		
		txtPula = new JTextField();
		txtPula.setEditable(false);
		txtPula.setText("POT");
		panel1.add(txtPula);
		txtPula.setColumns(10);
		
		JPanel panel2 = new JPanel();
		panel2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel2.setBackground(new Color(0, 100, 0));
		contentPane.add(panel2, BorderLayout.CENTER);
		panel2.setLayout(null);
		
		turn = new JTextField();
		turn.setHorizontalAlignment(SwingConstants.CENTER);
		turn.setBounds(10, 11, 595, 16);
		turn.setText("WAITING FOR OTHER PLAYERS...");
		turn.setEditable(false);
		panel2.add(turn);
		
		card1 = new JLabel("");
		card1.setFont(new Font("Times New Roman", Font.BOLD, 25));
		card1.setBackground(Color.WHITE);
		card1.setOpaque(true);
		card1.setHorizontalAlignment(SwingConstants.CENTER);
		card1.setBounds(123, 126, 60, 66);
		card1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel2.add(card1);
		
		card2 = new JLabel("");
		card2.setFont(new Font("Times New Roman", Font.BOLD, 25));
		card2.setBackground(Color.WHITE);
		card2.setOpaque(true);
		card2.setHorizontalAlignment(SwingConstants.CENTER);
		card2.setBounds(195, 126, 60, 66);
		card2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel2.add(card2);
		
		card3 = new JLabel("");
		card3.setFont(new Font("Times New Roman", Font.BOLD, 25));
		card3.setBackground(Color.WHITE);
		card3.setOpaque(true);
		card3.setHorizontalAlignment(SwingConstants.CENTER);
		card3.setBounds(265, 126, 60, 66);
		card3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel2.add(card3);
		
		card4 = new JLabel("");
		card4.setFont(new Font("Times New Roman", Font.BOLD, 25));
		card4.setBackground(Color.WHITE);
		card4.setOpaque(true);
		card4.setHorizontalAlignment(SwingConstants.CENTER);
		card4.setBounds(353, 126, 60, 66);
		card4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel2.add(card4);
		
		card5 = new JLabel("");
		card5.setFont(new Font("Times New Roman", Font.BOLD, 25));
		card5.setBackground(Color.WHITE);
		card5.setOpaque(true);
		card5.setHorizontalAlignment(SwingConstants.CENTER);
		card5.setBounds(435, 126, 60, 66);
		card5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel2.add(card5);
		
		player9 = new JTextArea();
		player9.setBackground(new Color(192, 192, 192));
		player9.setBorder(new LineBorder(new Color(0, 0, 0)));
		player9.setFont(new Font("Times New Roman", Font.BOLD, 12));
		player9.setBounds(46, 76, 103, 32);
		panel2.add(player9);
		
		player10 = new JTextArea();
		player10.setBackground(new Color(192, 192, 192));
		player10.setBorder(new LineBorder(new Color(0, 0, 0)));
		player10.setFont(new Font("Times New Roman", Font.BOLD, 12));
		player10.setBounds(187, 62, 103, 32);
		panel2.add(player10);
		
		player1 = new JTextArea();
		player1.setBackground(new Color(192, 192, 192));
		player1.setBorder(new LineBorder(new Color(0, 0, 0)));
		player1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		player1.setBounds(326, 62, 103, 32);
		panel2.add(player1);
		
		player2 = new JTextArea();
		player2.setBackground(new Color(192, 192, 192));
		player2.setBorder(new LineBorder(new Color(0, 0, 0)));
		player2.setFont(new Font("Times New Roman", Font.BOLD, 12));
		player2.setBounds(467, 76, 103, 32);
		panel2.add(player2);
		
		player7 = new JTextArea();
		player7.setBackground(new Color(192, 192, 192));
		player7.setBorder(new LineBorder(new Color(0, 0, 0)));
		player7.setFont(new Font("Times New Roman", Font.BOLD, 12));
		player7.setBounds(46, 214, 103, 32);
		panel2.add(player7);
		
		player6 = new JTextArea();
		player6.setBackground(new Color(192, 192, 192));
		player6.setBorder(new LineBorder(new Color(0, 0, 0)));
		player6.setFont(new Font("Times New Roman", Font.BOLD, 12));
		player6.setBounds(187, 233, 103, 32);
		panel2.add(player6);
		
		player5 = new JTextArea();
		player5.setBackground(new Color(192, 192, 192));
		player5.setBorder(new LineBorder(new Color(0, 0, 0)));
		player5.setFont(new Font("Times New Roman", Font.BOLD, 12));
		player5.setBounds(326, 233, 103, 32);
		panel2.add(player5);
		
		player4 = new JTextArea();
		player4.setBackground(new Color(192, 192, 192));
		player4.setBorder(new LineBorder(new Color(0, 0, 0)));
		player4.setFont(new Font("Times New Roman", Font.BOLD, 12));
		player4.setBounds(467, 214, 103, 32);
		panel2.add(player4);
		
		panel4 = new JPanel();
		panel4.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel4.setBackground(new Color(128, 0, 0));
		panel4.setBounds(0, 301, 615, 120);
		panel2.add(panel4);
		panel4.setLayout(null);
		
		playerCard1 = new JLabel("");
		playerCard1.setBounds(421, 10, 80, 100);
		panel4.add(playerCard1);
		playerCard1.setFont(new Font("Times New Roman", Font.BOLD, 40));
		playerCard1.setBackground(Color.WHITE);
		playerCard1.setOpaque(true);
		playerCard1.setHorizontalAlignment(SwingConstants.CENTER);
		playerCard1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		playerCard2 = new JLabel("");
		playerCard2.setBounds(511, 10, 80, 100);
		panel4.add(playerCard2);
		playerCard2.setBackground(Color.WHITE);
		playerCard2.setOpaque(true);
		playerCard2.setFont(new Font("Times New Roman", Font.BOLD, 40));
		playerCard2.setHorizontalAlignment(SwingConstants.CENTER);
		playerCard2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		panel3 = new JPanel();
		panel3.setBackground(Color.LIGHT_GRAY);
		panel3.setBounds(23, 10, 388, 100);
		panel4.add(panel3);
		panel3.setLayout(null);
		
		lblNewLabel = new JLabel("Your Balance");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 368, 37);
		panel3.add(lblNewLabel);
		
		playerTokens = new JLabel("0");
		playerTokens.setFont(new Font("Tahoma", Font.PLAIN, 30));
		playerTokens.setHorizontalAlignment(SwingConstants.CENTER);
		playerTokens.setBounds(10, 59, 368, 30);
		panel3.add(playerTokens);
		
		player8 = new JTextArea();
		player8.setFont(new Font("Times New Roman", Font.BOLD, 12));
		player8.setBorder(new LineBorder(new Color(0, 0, 0)));
		player8.setBackground(Color.LIGHT_GRAY);
		player8.setBounds(10, 145, 103, 32);
		panel2.add(player8);
		
		player3 = new JTextArea();
		player3.setFont(new Font("Times New Roman", Font.BOLD, 12));
		player3.setBorder(new LineBorder(new Color(0, 0, 0)));
		player3.setBackground(Color.LIGHT_GRAY);
		player3.setBounds(502, 146, 103, 32);
		panel2.add(player3);
		
		check.addActionListener(new myHandler());
		bet.addActionListener(new myHandler());
		call.addActionListener(new myHandler());
		fold.addActionListener(new myHandler());
		allin.addActionListener(new myHandler());
		raise.addActionListener(new myHandler());	
		
		
		//create list of player's info note
		players = new ArrayList<JTextArea>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		players.add(player5);
		players.add(player6);
		players.add(player7);
		players.add(player8);
		players.add(player9);
		players.add(player10);
		
		MoveRestrictions.restrictAll(this);
	}

	
	
	private class myHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = e.getActionCommand();
			int value=0;
			
			if(name == "bet"){
				try{
					value = Integer.parseInt(textField.getText());
					actionName = name;
					latch.countDown();
					setCurrentPlayerBet(value);
				}
				catch(NumberFormatException ex){
					textField.setText("");
				}
			}
			else if(name == "raise"){
				try{
					value = Integer.parseInt(textField1.getText());
					actionName = name;
					latch.countDown();
					setCurrentPlayerBet(value);
				}
				catch(NumberFormatException ex){
					textField1.setText("");
				}
			}	
			else {
				latch.countDown();
				actionName = name;
			}
		}	
	
	}
	

	public int getCurrentPlayerBet() {
		return currentPlayerBet;
	}

	public void setCurrentPlayerBet(int currentPlayerBet) {
		this.currentPlayerBet = currentPlayerBet;
	}
}
