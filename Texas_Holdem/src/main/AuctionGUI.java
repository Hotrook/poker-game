package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

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
	
	
	/**
	 * Create the frame.
	 */
	public AuctionGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 0));
		contentPane.add(panel, BorderLayout.SOUTH);
		
		check = new JButton("check");
		panel.add(check);
		
		bet = new JButton("bet");
		panel.add(bet);
		
		call = new JButton("call");
		panel.add(call);
		
		raise = new JButton("raise");
		panel.add(raise);
		
		fold = new JButton("fold");
		panel.add(fold);
		
		
		allin = new JButton("allin");
		panel.add(allin);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(34, 139, 34));
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		lblCurrentBet = new JLabel("Current Bet");
		panel_1.add(lblCurrentBet);
		
		txtStawka = new JTextField();
		txtStawka.setText("STAWKA");
		panel_1.add(txtStawka);
		txtStawka.setColumns(10);
		
		lblCurrentPot = new JLabel("Current Pot");
		panel_1.add(lblCurrentPot);
		
		txtPula = new JTextField();
		txtPula.setText("PULA");
		panel_1.add(txtPula);
		txtPula.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.RED);
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		turn = new JTextField();
		turn.setBounds(10, 11, 404, 16);
		turn.setText("Ruch gracza nr: ");
		turn.setEditable(false);
		panel_2.add(turn);
		
		playerCard1 = new JLabel("7\u2663");
		playerCard1.setFont(new Font("Times New Roman", Font.BOLD, 40));
		playerCard1.setBackground(Color.WHITE);
		playerCard1.setOpaque(true);
		playerCard1.setHorizontalAlignment(SwingConstants.CENTER);
		playerCard1.setBounds(132, 117, 80, 60);
		playerCard1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel_2.add(playerCard1);
		
		playerCard2 = new JLabel("K\u2663");
		playerCard2.setBackground(Color.WHITE);
		playerCard2.setOpaque(true);
		playerCard2.setFont(new Font("Times New Roman", Font.BOLD, 40));
		playerCard2.setHorizontalAlignment(SwingConstants.CENTER);
		playerCard2.setBounds(222, 117, 80, 60);
		playerCard2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel_2.add(playerCard2);
		
		card1 = new JLabel("7\u2663");
		card1.setFont(new Font("Times New Roman", Font.BOLD, 25));
		card1.setBackground(Color.WHITE);
		card1.setOpaque(true);
		card1.setHorizontalAlignment(SwingConstants.CENTER);
		card1.setBounds(10, 55, 60, 40);
		card1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel_2.add(card1);
		
		card2 = new JLabel("7\u2663");
		card2.setFont(new Font("Times New Roman", Font.BOLD, 25));
		card2.setBackground(Color.WHITE);
		card2.setOpaque(true);
		card2.setHorizontalAlignment(SwingConstants.CENTER);
		card2.setBounds(80, 55, 60, 40);
		card2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel_2.add(card2);
		
		card3 = new JLabel("7\u2663");
		card3.setFont(new Font("Times New Roman", Font.BOLD, 25));
		card3.setBackground(Color.WHITE);
		card3.setOpaque(true);
		card3.setHorizontalAlignment(SwingConstants.CENTER);
		card3.setBounds(152, 55, 60, 40);
		card3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel_2.add(card3);
		
		card4 = new JLabel("7\u2663");
		card4.setFont(new Font("Times New Roman", Font.BOLD, 25));
		card4.setBackground(Color.WHITE);
		card4.setOpaque(true);
		card4.setHorizontalAlignment(SwingConstants.CENTER);
		card4.setBounds(249, 55, 60, 40);
		card4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel_2.add(card4);
		
		card5 = new JLabel("7\u2663");
		card5.setFont(new Font("Times New Roman", Font.BOLD, 25));
		card5.setBackground(Color.WHITE);
		card5.setOpaque(true);
		card5.setHorizontalAlignment(SwingConstants.CENTER);
		card5.setBounds(343, 55, 60, 40);
		card5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel_2.add(card5);
		
		check.addActionListener(new myHandler());
		bet.addActionListener(new myHandler());
		call.addActionListener(new myHandler());
		fold.addActionListener(new myHandler());
		allin.addActionListener(new myHandler());
		raise.addActionListener(new myHandler());	
		
		MoveRestrictions.RestrictAll(this);
	}

	public String actionName;
	public CountDownLatch latch = new CountDownLatch(1);
	
	private class myHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = e.getActionCommand();
			//System.out.println(e.getActionCommand() + " by " + globalAuction.getCurrentPlayer().getPlayerName());
			actionName = name;
			latch.countDown();
		}	
	}
}
