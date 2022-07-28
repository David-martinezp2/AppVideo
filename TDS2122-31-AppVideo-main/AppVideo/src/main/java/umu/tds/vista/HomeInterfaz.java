package umu.tds.vista;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarculaLaf;

import tds.video.VideoWeb;
import umu.tds.componente.Video;
import umu.tds.controlador.ControladorUsuario;
import umu.tds.modelo.Usuario;

public class HomeInterfaz {

	private JFrame frame;
	private JTextField textFieldUsuario;
	private JPasswordField passwordFieldClave;
	private static VideoWeb videoweb;

	/**
	 * Launch the application.
	 */
	
	
	public void mostrarVentana() {
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					try {
						UIManager.setLookAndFeel(new FlatDarculaLaf());
					}
					catch (Exception e) {
						System.err.println( "Failed to initialize LaF" );
					}
					 videoweb = new VideoWeb();
					HomeInterfaz window = new HomeInterfaz();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HomeInterfaz() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(500, 500));
		frame.setBounds(100, 100, 858, 517);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_norte = new JPanel();
		panel_norte.setBorder(new EmptyBorder(25, 0, 0, 15));
		frame.getContentPane().add(panel_norte, BorderLayout.NORTH);
		panel_norte.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton_1 = new JButton("Registro");
		btnNewButton_1.addActionListener(new ActionListener() { //action listener del boton registro
			public void actionPerformed(ActionEvent e) {
				RegistroInterfaz registro= new RegistroInterfaz();
				registro.mostrarVentana();
				frame.dispose();
								
			}
		});
	
		panel_norte.add(btnNewButton_1, BorderLayout.EAST);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(HomeInterfaz.class.getResource("/resources/logo1.png")));
		panel_norte.add(lblNewLabel_1, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_gif = new JPanel();
		panel.add(panel_gif, BorderLayout.NORTH);
		GridBagLayout gbl_panel_gif = new GridBagLayout();
		gbl_panel_gif.columnWidths = new int[]{275, 1, 285, 0};
		gbl_panel_gif.rowHeights = new int[]{80, 0, 50, 0, 0};
		gbl_panel_gif.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_gif.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_gif.setLayout(gbl_panel_gif);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(HomeInterfaz.class.getResource("/resources/giphy-unscreen.gif")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		panel_gif.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panelLogin = new JPanel();
		panel.add(panelLogin, BorderLayout.CENTER);
		GridBagLayout gbl_panelLogin = new GridBagLayout();
		gbl_panelLogin.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panelLogin.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelLogin.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelLogin.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelLogin.setLayout(gbl_panelLogin);
		
		JLabel lblUsuario = new JLabel("Email:");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 1;
		panelLogin.add(lblUsuario, gbc_lblUsuario);
		
		textFieldUsuario = new JTextField();
		GridBagConstraints gbc_textFieldUsuario = new GridBagConstraints();
		gbc_textFieldUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUsuario.anchor = GridBagConstraints.NORTH;
		gbc_textFieldUsuario.gridx = 2;
		gbc_textFieldUsuario.gridy = 1;
		panelLogin.add(textFieldUsuario, gbc_textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setSize(new Dimension(6, 26));
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea.gridx = 1;
		gbc_lblContrasea.gridy = 2;
		panelLogin.add(lblContrasea, gbc_lblContrasea);
		
		passwordFieldClave = new JPasswordField();
		GridBagConstraints gbc_passwordFieldContraseña = new GridBagConstraints();
		gbc_passwordFieldContraseña.insets = new Insets(0, 0, 5, 5);
		gbc_passwordFieldContraseña.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldContraseña.gridx = 2;
		gbc_passwordFieldContraseña.gridy = 2;
		panelLogin.add(passwordFieldClave, gbc_passwordFieldContraseña);

		JLabel lblNoRegistrado = new JLabel("");
		GridBagConstraints gbc_lblNoRegistrado = new GridBagConstraints();
		gbc_lblNoRegistrado.gridwidth = 4;
		gbc_lblNoRegistrado.insets = new Insets(0, 0, 0, 5);
		gbc_lblNoRegistrado.gridx = 0;
		gbc_lblNoRegistrado.gridy = 4;
		panelLogin.add(lblNoRegistrado, gbc_lblNoRegistrado);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		//Acccion Login aceptar
			
				Usuario u = ControladorUsuario.getUnicaInstancia().loginUsuario(textFieldUsuario.getText(),
						String.copyValueOf(passwordFieldClave.getPassword()));
				if(u!=null){
					
					if(u.isPremium()) {
						ExplorarPremiumInterfaz explorar = new ExplorarPremiumInterfaz(u,videoweb, new ArrayList<umu.tds.modelo.Video>());
						frame.dispose();
						explorar.mostrarVentana();
					}
					else{
						ExplorarInterfaz explorar = new ExplorarInterfaz(u,videoweb, new ArrayList<umu.tds.modelo.Video>());
						frame.dispose();
						explorar.mostrarVentana();
					}
					
			
					
				
				}
				else {
					lblNoRegistrado.setText("El usuario introducidos o la contraseña no son correctos.");
					
				}
				
				
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.anchor = GridBagConstraints.EAST;
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 2;
		gbc_btnLogin.gridy = 3;
		panelLogin.add(btnLogin, gbc_btnLogin);
		
		
	}

}
