package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.toedter.calendar.JDateChooser;

import umu.tds.controlador.ControladorUsuario;
import java.awt.Color;
import java.awt.Font;

public class RegistroInterfaz {

	private JFrame frame;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JTextField textFieldEmail;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldRepetir;
	private JTextField textFieldUsuario;
	
	private Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                     + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");//patron para validar el correo electronico


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
					
					RegistroInterfaz window = new RegistroInterfaz();
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
	public RegistroInterfaz() {
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
		
		JButton btnNewButton_1 = new JButton("Inicio");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 				//Boton accion volver a Inicio
				HomeInterfaz home = new HomeInterfaz();
				home.mostrarVentana();
				frame.dispose();
				
			}
		});
		panel_norte.add(btnNewButton_1, BorderLayout.EAST);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(HomeInterfaz.class.getResource("/resources/logo1.png")));
		panel_norte.add(lblNewLabel_1, BorderLayout.WEST);
		
		JPanel panel_central = new JPanel();
		frame.getContentPane().add(panel_central, BorderLayout.CENTER);
		GridBagLayout gbl_panel_central = new GridBagLayout();
		gbl_panel_central.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_central.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_central.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_central.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_central.setLayout(gbl_panel_central);
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		panel_central.add(lblNombre, gbc_lblNombre);
		
		textFieldNombre = new JTextField();
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 2;
		gbc_textFieldNombre.gridy = 1;
		panel_central.add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		GridBagConstraints gbc_lblApellidos = new GridBagConstraints();
		gbc_lblApellidos.anchor = GridBagConstraints.EAST;
		gbc_lblApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidos.gridx = 1;
		gbc_lblApellidos.gridy = 2;
		panel_central.add(lblApellidos, gbc_lblApellidos);
		
		textFieldApellidos = new JTextField();
		GridBagConstraints gbc_textFieldApellidos = new GridBagConstraints();
		gbc_textFieldApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldApellidos.gridx = 2;
		gbc_textFieldApellidos.gridy = 2;
		panel_central.add(textFieldApellidos, gbc_textFieldApellidos);
		textFieldApellidos.setColumns(10);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de nacimiento:");
		GridBagConstraints gbc_lblFechaDeNacimiento = new GridBagConstraints();
		gbc_lblFechaDeNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaDeNacimiento.gridx = 1;
		gbc_lblFechaDeNacimiento.gridy = 3;
		panel_central.add(lblFechaDeNacimiento, gbc_lblFechaDeNacimiento);
		
		JDateChooser dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.anchor = GridBagConstraints.WEST;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 3;
		panel_central.add(dateChooser, gbc_dateChooser);
		
		JLabel lblEmail = new JLabel("Email:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 4;
		panel_central.add(lblEmail, gbc_lblEmail);
		
		textFieldEmail = new JTextField();
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.gridx = 2;
		gbc_textFieldEmail.gridy = 4;
		panel_central.add(textFieldEmail, gbc_textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblusuario = new JLabel("Usuario");
		GridBagConstraints gbc_lblusuario = new GridBagConstraints();
		gbc_lblusuario.anchor = GridBagConstraints.EAST;
		gbc_lblusuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblusuario.gridx = 1;
		gbc_lblusuario.gridy = 5;
		panel_central.add(lblusuario, gbc_lblusuario);
		
		textFieldUsuario = new JTextField();
		GridBagConstraints gbc_textFieldUsuario = new GridBagConstraints();
		gbc_textFieldUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUsuario.gridx = 2;
		gbc_textFieldUsuario.gridy = 5;
		panel_central.add(textFieldUsuario, gbc_textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contraseña:");
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.gridx = 1;
		gbc_lblContrasea.gridy = 6;
		panel_central.add(lblContrasea, gbc_lblContrasea);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 6;
		panel_central.add(passwordField, gbc_passwordField);
		
		JLabel lblRepetirContrasea = new JLabel("Repetir Contraseña:");
		GridBagConstraints gbc_lblRepetirContrasea = new GridBagConstraints();
		gbc_lblRepetirContrasea.anchor = GridBagConstraints.EAST;
		gbc_lblRepetirContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepetirContrasea.gridx = 1;
		gbc_lblRepetirContrasea.gridy = 7;
		panel_central.add(lblRepetirContrasea, gbc_lblRepetirContrasea);
		
		passwordFieldRepetir = new JPasswordField();
		GridBagConstraints gbc_passwordFieldRepetir = new GridBagConstraints();
		gbc_passwordFieldRepetir.insets = new Insets(0, 0, 5, 5);
		gbc_passwordFieldRepetir.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldRepetir.gridx = 2;
		gbc_passwordFieldRepetir.gridy = 7;
		panel_central.add(passwordFieldRepetir, gbc_passwordFieldRepetir);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		
		GridBagConstraints gbc_btnRegistrarse = new GridBagConstraints();
		gbc_btnRegistrarse.anchor = GridBagConstraints.EAST;
		gbc_btnRegistrarse.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistrarse.gridx = 2;
		gbc_btnRegistrarse.gridy = 8;
		panel_central.add(btnRegistrarse, gbc_btnRegistrarse);
		
		JLabel lblMensaje = new JLabel("");
		lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblMensaje.setForeground(new Color(255, 0, 0));
		GridBagConstraints gbc_lblMensaje = new GridBagConstraints();
		gbc_lblMensaje.gridwidth = 2;
		gbc_lblMensaje.insets = new Insets(0, 0, 0, 5);
		gbc_lblMensaje.gridx = 1;
		gbc_lblMensaje.gridy = 9;
		panel_central.add(lblMensaje, gbc_lblMensaje);
		
		btnRegistrarse.addActionListener(new ActionListener() {//accionListener boton registro
			public void actionPerformed(ActionEvent e) {
				
				//comprobamos si algun campo esta vacio y mostramos el mensaje
				//de error correspondiente.
				
				if(textFieldNombre.getText().isEmpty()) {
					lblMensaje.setText("El campo nombre esta vacio, debe completarlo.");
					return;
				}
				if(textFieldApellidos.getText().isEmpty()) {
					lblMensaje.setText("El campo apellidos esta vacio, debe completarlo.");
					return;
				}
				if(dateChooser.getDate()==null) {
					lblMensaje.setText("No se ha seleccionado la fecha de nacimiento.");
					return;
				}
				
				//comprobamos primero que el email no este vacio
				if(textFieldEmail.getText().isEmpty()) {
					lblMensaje.setText("El campo email esta vacio, debe completarlo.");
					return;
				}
				
				//comprobamos ahora que el formato del email sea correcto.
				Matcher mat = pattern.matcher(textFieldEmail.getText());//matcher para comprobar el formato del email.
				if(!mat.find()) {//en caso de no hacer match con el patron.
					lblMensaje.setText("El email introducido no tiene un formato válido, por favor, revíselo");
					return;
				}
				//usuario vacio
				if(textFieldUsuario.getText().isEmpty()) {
					lblMensaje.setText("El campo usuario esta vacio, debe completarlo.");
					return;
				}
				
				
				//comprobamos contraseñas no vacias y iguales.
				String clave=String.valueOf(passwordField.getPassword());
				String repetirClave=String.valueOf(passwordFieldRepetir.getPassword());
				if(clave.isEmpty()  || repetirClave.isEmpty() ) {
					lblMensaje.setText("El campo contraseña o repetir contraseña esta vacio.");
					return;
				}
				if(!clave.equals(repetirClave)) {
					lblMensaje.setText("Las claves no coinciden.");
					return; 
				}
				
				
				
				
				//en caso de cumplirse todos los requerimientos, registramos al usuario.	
				
				
				//primero comprobamos que el email no este ya registrado.
				if(ControladorUsuario.getUnicaInstancia().existeUsuarios(textFieldEmail.getText())) {
					lblMensaje.setText("El email introducido ya esta registrado.");
					return;
				}
				
				//en caso de no estar registrado procedemos al registro
				ControladorUsuario.getUnicaInstancia().registrarUsuario(
						textFieldNombre.getText(),
						textFieldApellidos.getText(),
						dateChooser.getDate(),
						textFieldEmail.getText(),
						String.valueOf(passwordField.getPassword()));
				
				
				//mostramos un mensaje de registro completado
				lblMensaje.setForeground(Color.green);
				lblMensaje.setText("El registro se ha completado correctamente.");
			}
		});
	}


}
