package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class RecientesInterfaz {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;


	/**
	 * Launch the application.
	 */
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
					RecientesInterfaz window = new RecientesInterfaz();
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
	public RecientesInterfaz() {
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
	
		panel_norte.add(btnNewButton_1, BorderLayout.EAST);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(HomeInterfaz.class.getResource("/resources/logo1.png")));
		panel_norte.add(lblNewLabel_1, BorderLayout.WEST);
		
		JPanel panel_sur = new JPanel();
		panel_sur.setBorder(new EmptyBorder(0, 0, 0, 0));
		frame.getContentPane().add(panel_sur, BorderLayout.SOUTH);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"asdf", "as", "df", "asdf", "as", "dfs", "adf", "as", "f", "asd", "fas", "df", "asf", "as", "fas", "df", "as", "fa", "sfd", "f", "f", "df", "", "fa", "sdf"}));
		panel_sur.add(comboBox);
		
		JPanel panel_centro = new JPanel();
		panel_centro.setBorder(new EmptyBorder(20, 0, 0, 0));
		frame.getContentPane().add(panel_centro, BorderLayout.CENTER);
		panel_centro.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_norte2 = new JPanel();
		panel_centro.add(panel_norte2, BorderLayout.NORTH);
		
		JMenuBar menuBar = new JMenuBar();
		panel_norte2.add(menuBar);
		menuBar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setBorder(new EmptyBorder(0, 10, 0, 10));
		lblBuscar.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(lblBuscar);
		
		textField_1 = new JTextField();
		textField_1.setName("");
		menuBar.add(textField_1);
		textField_1.setColumns(20);
		
		JButton buscarBoton = new JButton("");
		buscarBoton.setIcon(new ImageIcon(RecientesInterfaz.class.getResource("/resources/lupa.png")));
		menuBar.add(buscarBoton);
		
		
		JMenu filtros = new JMenu("Filtros");
		menuBar.add(filtros);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Intriga");
		filtros.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxSerie = new JCheckBox("Serie");
		filtros.add(chckbxSerie);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Clásico");
		filtros.add(chckbxNewCheckBox);
		
		JCheckBox chckbxPel = new JCheckBox("Película");
		filtros.add(chckbxPel);
		
		JCheckBox chckbxTerror = new JCheckBox("Terror");
		filtros.add(chckbxTerror);
		
		JCheckBox chckbxDibujadosAnimados = new JCheckBox("Dibujos  animados");
		filtros.add(chckbxDibujadosAnimados);
		
		JCheckBox chckbxAdultos = new JCheckBox("Adultos");
		filtros.add(chckbxAdultos);
		
		JPanel panel_centro2 = new JPanel();
		
		panel_centro.add(panel_centro2, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(panel_centro2);
		
		//creamos las constraints para el gridbag
		
	
		GridBagLayout gbl_panel_centro2 = new GridBagLayout();
		gbl_panel_centro2.columnWidths = new int[] {200, 200, 200};
		gbl_panel_centro2.rowHeights = new int[] {0, 0, 0};
		gbl_panel_centro2.columnWeights = new double[]{0.0};
		gbl_panel_centro2.rowWeights = new double[]{0.0, 0.0, 0.0};
		panel_centro2.setLayout(gbl_panel_centro2);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setPreferredSize(new Dimension(200, 200));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_centro2.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_2.setPreferredSize(new Dimension(200, 200));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 0;
		panel_centro2.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_3.setPreferredSize(new Dimension(200, 200));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 0;
		panel_centro2.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_4.setPreferredSize(new Dimension(200, 200));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 1;
		panel_centro2.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_5.setPreferredSize(new Dimension(200, 200));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 1;
		gbc_lblNewLabel_5.gridy = 1;
		panel_centro2.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		lblNewLabel_6.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_6.setPreferredSize(new Dimension(200, 200));
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_6.gridx = 2;
		gbc_lblNewLabel_6.gridy = 1;
		panel_centro2.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_7.setPreferredSize(new Dimension(200, 200));
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 2;
		panel_centro2.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("New label");
		lblNewLabel_8.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_8.setPreferredSize(new Dimension(200, 200));
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_8.gridx = 1;
		gbc_lblNewLabel_8.gridy = 2;
		panel_centro2.add(lblNewLabel_8, gbc_lblNewLabel_8);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

	}

	
}
