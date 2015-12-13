package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AuctionGUI extends JFrame{

	private JPanel contentPane;
	private JTextField txtPula;
	private JTextField txtStawka;
	public Auction globalAuction;
	public JButton check;
	public JButton bet;
	public JButton call;
	public JButton raise;
	public JButton fold;
	public JButton allin;
	
	/**
	 * Create the frame.
	 */
	public AuctionGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
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
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		txtStawka = new JTextField();
		txtStawka.setText("STAWKA");
		panel_1.add(txtStawka);
		txtStawka.setColumns(10);
		
		txtPula = new JTextField();
		txtPula.setText("PULA");
		panel_1.add(txtPula);
		txtPula.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		
		JTextArea turn = new JTextArea();
		turn.setText("Ruch gracza nr: ");
		panel_2.add(turn);
		
		check.addActionListener(new myHandler());
		bet.addActionListener(new myHandler());
		call.addActionListener(new myHandler());
		fold.addActionListener(new myHandler());
		allin.addActionListener(new myHandler());
		raise.addActionListener(new myHandler());	
		
		MoveRestrictions.RestrictAll(this);
	}
	
	private class myHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = e.getActionCommand();
			//globalAuction.getCurrentPlayer().setName(name); 
			//System.out.println(name + " by " + globalAuction.getCurrentPlayer().getPlayerName());
			System.out.println(e.getActionCommand() + " by " + globalAuction.getCurrentPlayer().getPlayerName());
			txtPula.setText(globalAuction.getCurrentPot()+"");
			txtStawka.setText(globalAuction.getCurrentBet() + "");
			globalAuction.getCurrentPlayer().setName(name); 
		}
		
	}

}
