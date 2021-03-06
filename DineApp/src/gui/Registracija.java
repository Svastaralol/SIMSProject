package gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Administrator;
import model.DineApp;
import model.Korisnik;

import java.awt.GridLayout;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Registracija extends JFrame{
	
	private int width = 410;
	private int height = 260;
	private JPanel panel;
	private JTextField unosIme;
	private JTextField unosPrezime;
	private JTextField unosUsername;
	private JPasswordField unosLozinka;
	private JTextField unosEmail;
	
	public Registracija() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
		MainWindow.changeFont(this);
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblIme = new JLabel("Ime:");
		lblIme.setBounds(12, 13, 68, 16);
		panel.add(lblIme);
		
		unosIme = new JTextField();
		unosIme.setBounds(92, 10, 290, 22);
		panel.add(unosIme);
		unosIme.setColumns(10);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setBounds(12, 42, 83, 16);
		panel.add(lblPrezime);
		
		unosPrezime = new JTextField();
		unosPrezime.setBounds(92, 40, 290, 22);
		panel.add(unosPrezime);
		unosPrezime.setColumns(10);
		
		JLabel lblKorisnickoIme = new JLabel("Username:");
		lblKorisnickoIme.setBounds(12, 71, 83, 16);
		panel.add(lblKorisnickoIme);
		
		unosUsername = new JTextField();
		unosUsername.setBounds(92, 70, 290, 22);
		panel.add(unosUsername);
		unosUsername.setColumns(10);
		
		JLabel lblLozinka = new JLabel("Lozinka:");
		lblLozinka.setBounds(12, 135, 68, 16);
		panel.add(lblLozinka);
		
		unosLozinka = new JPasswordField();
		unosLozinka.setBounds(92, 130, 290, 22);
		panel.add(unosLozinka);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(12, 103, 68, 16);
		panel.add(lblEmail);
		
		unosEmail = new JTextField();
		unosEmail.setBounds(92, 100, 290, 22);
		panel.add(unosEmail);
		unosEmail.setColumns(10);
		
		JButton btnRegistrujSe = new JButton("Registruj se");
		btnRegistrujSe.setBounds(12, 186, 174, 25);
		btnRegistrujSe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				if (unosIme.getText().isEmpty() || unosPrezime.getText().isEmpty() || unosEmail.getText().isEmpty() 
						|| unosUsername.getText().isEmpty() || unosLozinka.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Sva polja moraju biti popunjena.", "Registracija", NORMAL);
				} else {
					Korisnik k = null;
					try {
						BufferedReader reader = new BufferedReader(new FileReader("./data/files/administratori.txt"));
						String line = "";
						boolean admin = false;
						while ((line = reader.readLine()) != null) {
							if (unosEmail.getText().equals(line)) {
								admin = true;
							}
						}
						
						if (admin) {
							k = new Administrator();
						}
						else {
							k = new Korisnik();
						}
						
						reader.close();
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for (int i = 0; i < DineApp.korisnici.size(); i++) {
						if (DineApp.korisnici.get(i).getKorIme().equals(unosUsername.getText())) {
							JOptionPane.showMessageDialog(null, "Vec postoji takvo korisnicko ime.", "Registracija", NORMAL);
							return;
						}
						else if (DineApp.korisnici.get(i).geteMail().equals(unosEmail.getText())) {
							JOptionPane.showMessageDialog(null, "Vec postoji takav E-Mail.", "Registracija", NORMAL);
							return;
						}
					}
					
					k.setIme(unosIme.getText());
					k.setPrezime(unosPrezime.getText());
					k.seteMail(unosEmail.getText());
					k.setKorIme(unosUsername.getText());
					k.setLozinka(unosLozinka.getText());
					
					DineApp.korisnici.add(k);
					
					try {
						ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./data/files/korisnici.sims"));
						out.writeObject(DineApp.korisnici);
						JOptionPane.showMessageDialog(null, "Uspesna registracija.", "Registracija", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					Registracija.this.dispose();
				}
			}
			
		});
		panel.add(btnRegistrujSe);
		
		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.setBounds(196, 186, 174, 25);
		btnOdustani.addActionListener(new ActionListener( ) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Registracija.this.dispose();
			}
			
		});
		panel.add(btnOdustani);
		
		MainWindow.changeFont(this);
	}
}
