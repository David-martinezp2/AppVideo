package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import java.io.File;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarculaLaf;
import pulsador.IEncendidoListener;
import pulsador.Luz;
import tds.video.VideoWeb;
import umu.tds.controlador.ControladorUsuario;
import umu.tds.controlador.ControladorVideo;
import umu.tds.modelo.ListaVideo;
import umu.tds.modelo.Usuario;
import umu.tds.modelo.Video;

public class ExplorarPrueba {

	private Usuario usuario;
	private JFrame frame;
	private JPanel panel_explorar;
	private JPanel panel_recientes;
	private JPanel panel_centro2;
	private JPanel panel_busquedas;

	private JTextField textField_buscar;
	
	private static VideoWeb videoweb;
	
	private JDialog reproductorVideo;
	private JDialog dialognuevaLista;
	private JDialog info;
	private List<Video> videos;
	private ArrayList<Video> videosRecientes;

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
					} catch (Exception e) {
						System.err.println("Failed to initialize LaF");
					}

					ExplorarPrueba window = new ExplorarPrueba(null,new VideoWeb(), new ArrayList<Video>());
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
	public ExplorarPrueba(Usuario usuario, VideoWeb video, ArrayList<Video> arrayList) {
		this.usuario = usuario;
	
		videoweb = video;

		inicializarJdialog();
		panel_recientes = new JPanel();
		panel_busquedas = new JPanel();
		videosRecientes = arrayList;
		videos = ControladorVideo.getUnicaInstancia().obtenerVideos();
		initialize();

	}

	private void inicializarJdialog() {
		//inicializamos el dialogo del reproductor
		reproductorVideo = new JDialog(reproductorVideo, "Reproduciendo video");
		reproductorVideo.getContentPane().add(videoweb);
		reproductorVideo.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		reproductorVideo.setMinimumSize(new Dimension(400, 260));
		
		// añadimos el action listener al cerrar el dialog.
				reproductorVideo.addComponentListener(new ComponentListener() {

					@Override
					public void componentShown(ComponentEvent e) {

					}

					@Override
					public void componentResized(ComponentEvent e) {

					}

					@Override
					public void componentMoved(ComponentEvent e) {

					}

					@Override
					public void componentHidden(ComponentEvent e) {
						videoweb.cancel();

					}

				});
		
		//inicializamos el dialogo de nuevas listas.
		dialognuevaLista = new JDialog(dialognuevaLista, "Añadir nueva playlist");
		dialognuevaLista.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		dialognuevaLista.setLocationRelativeTo(frame);
		dialognuevaLista.setMinimumSize(new Dimension(400, 200));
		dialognuevaLista.setResizable(false);
		dialognuevaLista.getContentPane().setLayout(null);
		
		JPanel panelNuevaLista = new JPanel();
		panelNuevaLista.setLayout(new BoxLayout(panelNuevaLista, BoxLayout.Y_AXIS));
		panelNuevaLista.setSize(400, 200);
		panelNuevaLista.setBorder(new EmptyBorder(30,0,20,0));
		
		
		info = new JDialog(info,"Aviso");
		info.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		info.setLocationRelativeTo(frame);
		info.setMinimumSize(new Dimension(300, 100));
		info.setResizable(false);
		info.getContentPane().setLayout(null);
		info.setModal(true);
		
		JPanel panelInfo = new JPanel();
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
		panelInfo.setSize(300, 100);
		panelInfo.setBorder(new EmptyBorder(30,0,20,0));
		
		JTextField nombreNuevaLista = new JTextField();
		nombreNuevaLista.setSize(380, 70);
		nombreNuevaLista.setMaximumSize(new Dimension(350, 40));
		nombreNuevaLista.setAlignmentX( Component.CENTER_ALIGNMENT );
		
		JButton aceptar_nuevLista= new JButton();
		aceptar_nuevLista.setText("Crear playlist");
		aceptar_nuevLista.setSize(200, 30);
		aceptar_nuevLista.setAlignmentX( Component.CENTER_ALIGNMENT );
		
		JLabel nuevaListaMsg = new JLabel();
		nuevaListaMsg.setText("Nueva lista creada correctamente");

		nuevaListaMsg.setAlignmentX( Component.CENTER_ALIGNMENT );
		
		panelNuevaLista.add(nombreNuevaLista);
		panelNuevaLista.add(aceptar_nuevLista);
		panelInfo.add(nuevaListaMsg);
		dialognuevaLista.getContentPane().add(panelNuevaLista);
		info.getContentPane().add(panelInfo);
		
		//listener del boton añadir playlist
		aceptar_nuevLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//creamos una nueva lista de videos vacia.
				ListaVideo lista = new ListaVideo(nombreNuevaLista.getText());
				ControladorUsuario.getUnicaInstancia().
					añadirPlaylistUsuario(usuario.getEmail(), lista);
				
				dialognuevaLista.setVisible(false);
				
				
				info.setVisible(true);
				
			}
		});
		

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(860, 740));
		frame.setResizable(false);
		frame.setBounds(100, 100, 858, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel_norte = new JPanel();
		panel_norte.setBorder(new EmptyBorder(25, 0, 0, 15));
		frame.getContentPane().add(panel_norte, BorderLayout.NORTH);
		panel_norte.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(HomeInterfaz.class.getResource("/resources/logo1.png")));
		panel_norte.add(lblNewLabel_1, BorderLayout.WEST);
		
		JButton btnPremium = new JButton("Hazte Premium");
		btnPremium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_norte.add(btnPremium, BorderLayout.EAST);

		JPanel panel_sur = new JPanel();
		panel_sur.setBorder(new EmptyBorder(0, 0, 0, 0));
		frame.getContentPane().add(panel_sur, BorderLayout.SOUTH);

		JButton btnExplorar = new JButton("Explorar");

		panel_sur.add(btnExplorar);

		JButton btnRecientes = new JButton("Recientes");
		panel_sur.add(btnRecientes);

		JButton btnMisListas = new JButton("Mis listas");

		panel_sur.add(btnMisListas);

		JButton btnNuevaLista = new JButton("Nueva lista");
		
		btnNuevaLista.setForeground(Color.BLACK);
		btnNuevaLista.setBackground(Color.LIGHT_GRAY);
		panel_sur.add(btnNuevaLista);

		Luz luz = new Luz();
		luz.setColor(Color.ORANGE);

		panel_sur.add(luz);

		panel_explorar = new JPanel();
		panel_explorar.setBorder(new EmptyBorder(20, 0, 0, 0));
		frame.getContentPane().add(panel_explorar, BorderLayout.CENTER);
		panel_explorar.setLayout(new BorderLayout(0, 0));

		JPanel panel_norte2 = new JPanel();
		panel_explorar.add(panel_norte2, BorderLayout.NORTH);

		

		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setBorder(new EmptyBorder(0, 10, 0, 10));
		lblBuscar.setHorizontalAlignment(SwingConstants.LEFT);
		panel_norte2.add(lblBuscar);

		textField_buscar = new JTextField();
		textField_buscar.setName("");
		panel_norte2.add(textField_buscar);
		textField_buscar.setColumns(20);

		JButton buscarBoton = new JButton("");

		buscarBoton.setIcon(new ImageIcon(ExplorarPrueba.class.getResource("/resources/lupa.png")));
		panel_norte2.add(buscarBoton);

		


		panel_centro2 = new JPanel();

		panel_explorar.add(panel_centro2, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane(panel_centro2);
		panel_explorar.add(scrollPane, BorderLayout.CENTER);

		// creamos las constraints para el gridbag

		GridBagLayout gbl_panel_centro2 = new GridBagLayout();
		gbl_panel_centro2.columnWidths = new int[] { 200, 200, 200 };
		gbl_panel_centro2.rowHeights = new int[] { 180, 180, 180 };
		gbl_panel_centro2.columnWeights = new double[] { 0.0 };
		gbl_panel_centro2.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		panel_centro2.setLayout(gbl_panel_centro2);

		mostrarVideos(panel_centro2);

//--------------------ACTION LISTENERS----------------------//

		// action listener componente luz
		luz.addEncendidoListener(new IEncendidoListener() {

			@Override
			public void enteradoCambioEncendido(EventObject arg0) {
				JFileChooser seleccionXML = new JFileChooser();
				int fichXML = seleccionXML.showOpenDialog(frame);

				if (fichXML == JFileChooser.APPROVE_OPTION) {
					File selectedFile = seleccionXML.getSelectedFile();
					ControladorVideo.getUnicaInstancia().cargarVideos(selectedFile);

					frame.dispose();
					ExplorarPrueba e = new ExplorarPrueba(usuario, videoweb, videosRecientes);
					e.mostrarVentana();
					

				} else {
					System.err.println("El fichero XML no se ha leido correctamente");
				}

			}
		});

		// action listener boton realizar busqueda

		buscarBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				actualizarPanelBusquedas(textField_buscar.getText());
//				frame.getContentPane().remove(panel_explorar);
//				frame.getContentPane().remove(panel_recientes);
//				frame.getContentPane().add(panel_busquedas, BorderLayout.CENTER);
//				//cambia el footer
//				cambiarFooterABusqueda(btnExplorar,btnMisListas,btnNuevaLista,btnRecientes,luz);
//				frame.getContentPane().revalidate();
//				frame.getContentPane().repaint();
//				frame.validate();
				
				JPanel panel_centro2 = new JPanel();
				scrollPane.setViewportView(panel_centro2);
				panel_centro2.setLayout(gbl_panel_centro2);
				
				actualizarPanelBusquedas(textField_buscar.getText(),panel_centro2);
				
				panel_centro2.repaint();
			}
		});

		// action listener cambio de panel a recientes.
		btnRecientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// actualizamos primero el panel recientes.
				actualizarPanelRecientes();
				frame.getContentPane().remove(panel_explorar);
				frame.getContentPane().remove(panel_busquedas);
				frame.getContentPane().add(panel_recientes, BorderLayout.CENTER);
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				frame.validate();
			}
		});

		// action listener cambio de panel a explorar.
		btnExplorar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(panel_recientes);
				frame.getContentPane().remove(panel_busquedas);
				cambiarFooterAExplorar(btnExplorar,btnMisListas,btnNuevaLista,btnRecientes,luz);
				frame.getContentPane().add(panel_explorar, BorderLayout.CENTER);
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
				frame.validate();
			}
		});
		
		//action listener crear nueva lista.
		btnNuevaLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialognuevaLista.setVisible(true);
			}
		});
		
		//action listener mis playlist.
		btnMisListas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MisListasInterfaz listas = new MisListasInterfaz(usuario,videoweb,videosRecientes);
				frame.dispose();
				listas.mostrarVentana();
				
			}
		});
		
		
		//action listener premium
		//no tenemos en cuenta para el proyecto la plataforma de pagos.
		btnPremium.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ControladorUsuario.getUnicaInstancia().hacerPremiumUsuario(usuario.getEmail());
				frame.dispose();
				ExplorarPremiumInterfaz explorarPremium = new ExplorarPremiumInterfaz(usuario,
						videoweb, videosRecientes);
				explorarPremium.mostrarVentana();
						
			}
		});

	}

//--------------------METODOS AUXILIARES----------------------//

	// metodo auxiliar mostrar videos en el grid

	private void mostrarVideos(JPanel panel) {
		int contx = 0;
		int conty = 0;
		int indice = 0;

		for (Video vid : videos) {

			// cremos el panel para cada video.
			JPanel panelNuevo = new JPanel();
			panelNuevo.setLayout(new BoxLayout(panelNuevo, BoxLayout.Y_AXIS));
			panelNuevo.setPreferredSize(new Dimension(200, 140));

			JButton nueva = new JButton();
			nueva.setAlignmentX(Component.CENTER_ALIGNMENT);
			nueva.setPreferredSize(new Dimension(200, 200));
			nueva.setIcon(videoweb.getThumb(vid.getUrl()));
			nueva.setName(String.valueOf(indice));
			nueva.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					//actualizamos el contador de reproducciones del video.
					ControladorVideo.getUnicaInstancia().añadirReproduccionVideo(vid);
					
					
					videoweb.playVideo(vid.getUrl());
					// añadimos el video a recientes.

					if (videosRecientes.size() == 5) {
						videosRecientes.remove(0);
						videosRecientes.add(vid);
					} else {
						videosRecientes.add(vid);
					}

					// hacemos el dialogo visible
					reproductorVideo.setVisible(true);

				}
			});

			JLabel titulo = new JLabel(vid.getTitulo());
			titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

			// añadimos los componentes al nuevo panel
			panelNuevo.add(nueva);
			panelNuevo.add(titulo);
			panelNuevo.add(Box.createRigidArea(new Dimension(0, 25)));
			// creamos los constraints
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = contx;
			gbc.gridy = conty;
			// lo añadimos al panel del parametro.
			panel.add(panelNuevo, gbc);

			// actualizamos los posicionadores del gridbag
			if (contx < 3) {
				contx++;
			} else {
				contx = 0;
				conty++;
			}
			indice++;

		}

	}

	private void actualizarPanelRecientes() {

		panel_recientes = new JPanel();
		GridBagLayout gbl_recientes = new GridBagLayout();
		gbl_recientes.columnWidths = new int[] { 200, 200, 200 };
		gbl_recientes.rowHeights = new int[] { 180, 180, 180 };
		gbl_recientes.columnWeights = new double[] { 0.0 };
		gbl_recientes.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		panel_recientes.setLayout(gbl_recientes);

		int indice = 0;
		int contx = 0;
		int conty = 0;
		for (Video vid : videosRecientes) {

			// cremos el panel para cada video.
			JPanel panelNuevo = new JPanel();
			panelNuevo.setLayout(new BoxLayout(panelNuevo, BoxLayout.Y_AXIS));
			panelNuevo.setPreferredSize(new Dimension(200, 140));

			JButton nueva = new JButton();
			nueva.setAlignmentX(Component.CENTER_ALIGNMENT);
			nueva.setPreferredSize(new Dimension(200, 200));
			nueva.setIcon(videoweb.getThumb(vid.getUrl()));
			nueva.setName(String.valueOf(indice));
			nueva.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					//actualizamos el contador de reproducciones del video.
					ControladorVideo.getUnicaInstancia().añadirReproduccionVideo(vid);
					
					videoweb.playVideo(vid.getUrl());

					if (videosRecientes.size() == 5) {
						videosRecientes.remove(0);
						videosRecientes.add(vid);
					} else {
						videosRecientes.add(vid);
					}
					// hacemos el dialogo visible
					reproductorVideo.setVisible(true);

				}
			});

			JLabel titulo = new JLabel(vid.getTitulo());
			titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

			// añadimos los componentes al nuevo panel
			panelNuevo.add(nueva);
			panelNuevo.add(titulo);
			panelNuevo.add(Box.createRigidArea(new Dimension(0, 25)));
			// creamos los constraints
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = contx;
			gbc.gridy = conty;
			// lo añadimos al panel del parametro.
			panel_recientes.add(panelNuevo, gbc);

			// actualizamos los posicionadores del gridbag
			if (contx < 3) {
				contx++;
			} else {
				contx = 0;
				conty++;
			}
			indice++;

		}

		panel_recientes.revalidate();
		panel_recientes.repaint();
		panel_recientes.validate();
	}
	
	
	private void actualizarPanelBusquedas(String busqueda, JPanel panel) {

		int indice = 0;
		int contx = 0;
		int conty = 0;
		
		for (Video vid : videos) {

			if(vid.getTitulo().contains(busqueda)) {
				
			
			// cremos el panel para cada video.
			JPanel panelNuevo = new JPanel();
			panelNuevo.setLayout(new BoxLayout(panelNuevo, BoxLayout.Y_AXIS));
			panelNuevo.setPreferredSize(new Dimension(200, 140));

			JButton nueva = new JButton();
			nueva.setAlignmentX(Component.CENTER_ALIGNMENT);
			nueva.setPreferredSize(new Dimension(200, 200));
			nueva.setIcon(videoweb.getThumb(vid.getUrl()));
			nueva.setName(String.valueOf(indice));
			nueva.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//actualizamos el contador de reproducciones del video.
					ControladorVideo.getUnicaInstancia().añadirReproduccionVideo(vid);
					
					
					videoweb.playVideo(vid.getUrl());
						
					// añadimos el video a recientes.

					if (videosRecientes.size() == 5) {
						videosRecientes.remove(0);
						videosRecientes.add(vid);
					} else {
						videosRecientes.add(vid);
					}
					// hacemos el dialogo visible
					reproductorVideo.setVisible(true);

				}
			});

			JLabel titulo = new JLabel(vid.getTitulo());
			titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

			// añadimos los componentes al nuevo panel
			panelNuevo.add(nueva);
			panelNuevo.add(titulo);
			panelNuevo.add(Box.createRigidArea(new Dimension(0, 25)));
			// creamos los constraints
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = contx;
			gbc.gridy = conty;
			// lo añadimos al panel del parametro.
			panel.add(panelNuevo,gbc);

			// actualizamos los posicionadores del gridbag
			if (contx < 3) {
				contx++;
			} else {
				contx = 0;
				conty++;
			}
			indice++;

		
		}
		}
		
	

		panel_recientes.revalidate();
		panel_recientes.repaint();
		panel_recientes.validate();
	}
	
	private void cambiarFooterABusqueda(JButton explorar, JButton hide1, JButton hide2,JButton hide3, Luz hide4) {
		explorar.setText("Volver");
		hide1.setVisible(false);
		hide2.setVisible(false);
		hide3.setVisible(false);
		hide4.setVisible(false);
		
	}
	private void cambiarFooterAExplorar(JButton explorar, JButton hide1, JButton hide2,JButton hide3, Luz hide4) {
		explorar.setText("Explorar");
		hide1.setVisible(true);
		hide2.setVisible(true);
		hide3.setVisible(true);
		hide4.setVisible(true);
		
	}

}
