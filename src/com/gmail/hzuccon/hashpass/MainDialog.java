package com.gmail.hzuccon.hashpass;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFormattedTextField;

public class MainDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField serviceField;
	private JPasswordField secretField;
	private final Action action = new SwingAction();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainDialog frame = new MainDialog();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainDialog() {
		setTitle("HashPass");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 354, 178);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		secretField = new JPasswordField();
		secretField.setBounds(10, 21, 320, 23);
		contentPane.add(secretField);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnGenerate.setAction(action);
		btnGenerate.setBounds(142, 114, 89, 23);
		contentPane.add(btnGenerate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancel.setBounds(241, 114, 89, 23);
		contentPane.add(btnCancel);
		
		JLabel lblSecret = new JLabel("Secret");
		lblSecret.setBounds(10, 4, 46, 14);
		contentPane.add(lblSecret);
		
		JLabel lblService = new JLabel("Service");
		lblService.setBounds(10, 55, 46, 14);
		contentPane.add(lblService);
		
		serviceField = new JFormattedTextField();
		serviceField.setToolTipText("service");
		serviceField.setBounds(10, 82, 318, 23);
		contentPane.add(serviceField);
	}
	
	
	
	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2L;
		public SwingAction() {
			putValue(NAME, "Generate");
			putValue(SHORT_DESCRIPTION, "Start generating pssword");
			
		}
		public void actionPerformed(ActionEvent e) {
			hash(new String(secretField.getPassword()), serviceField.getText());
			System.exit(0);
		}
		
		public void hash(String secret, String service) {
			  
			try { 
				String combined = secret + "-" + service;
		
			    byte[] hash = combined.getBytes(StandardCharsets.UTF_8);
			    MessageDigest digest = MessageDigest.getInstance("SHA-256");

			    digest.reset();
			    
				for (int i = 0; i < 65536; i++) {
				    hash = digest.digest(hash);
				}
				StringBuilder sb = new StringBuilder(byteArrayToHex(hash));
				sb.replace(0, 1, "z");
				sb.replace(3, 4, "H");
				sb.replace(10, 11, String.valueOf(7));
				sb.replace(15, 16, "!");
				
				String pass = sb.substring(0, 16);
				
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(pass), null);

		    
			 } catch (NoSuchAlgorithmException e) {
			    
			}
		}

		public String byteArrayToHex(byte[] a) {
		    StringBuilder sb = new StringBuilder(a.length * 2);
		        for(byte b: a)
		            sb.append(String.format("%02x", b));
		        return sb.toString();
		}
		
	}
}
