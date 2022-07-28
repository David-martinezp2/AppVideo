package umu.tds.vista;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.MinimalHTMLWriter;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.sun.glass.events.MouseEvent;

import pulsador.IEncendidoListener;
import pulsador.Luz;
import tds.video.VideoWeb;
import umu.tds.controlador.ControladorUsuario;
import umu.tds.controlador.ControladorVideo;
import umu.tds.modelo.ListaVideo;
import umu.tds.modelo.Usuario;
import umu.tds.modelo.Video;
import javax.swing.JTabbedPane;
import javax.swing.JScrollBar;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JSpinner;
import com.toedter.components.JSpinField;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.AbstractListModel;
import java.awt.SystemColor;
import javax.swing.JComboBox;

public class MisListasInterfaz {

	private JFrame frame;
	private static VideoWeb videoweb;
	private JDialog reproductorVideo;
	private Usuario usuario;
	private List<Video> videos;
	private Video seleccionado;
	private Video seleccionadoPlaylist;
	private ListaVideo playlist;
	private JTextField textField_1;
	private HashMap<String,ListaVideo> listasUsuario;
	private JTextField txtBuscarPorTitulo;
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
					}
					catch (Exception e) {
						System.err.println( "Failed to initialize LaF" );
					}
					
					MisListasInterfaz window = new MisListasInterfaz(null, new VideoWeb(), new ArrayList<Video>());
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
	public MisListasInterfaz(Usuario usuario, VideoWeb video, ArrayList<Video> recientes) {
		videoweb = video;
		videos = ControladorVideo.getUnicaInstancia().obtenerVideos();
		videosRecientes=recientes;
		this.usuario = ControladorUsuario.getUnicaInstancia().recuperarUsuario(usuario.getEmail());
		listasUsuario = new HashMap<String, ListaVideo>();
		for(ListaVideo lv : usuario.getListasVideos()) {
			listasUsuario.put(lv.getNombre(), lv);
			System.out.println("los codigos al inicializar son:" + lv.getCodigo());
		}
		playlist = new ListaVideo(null);
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
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
		
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(500, 500));
		frame.setBounds(100, 100, 863, 740);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_norte = new JPanel();
		panel_norte.setBorder(new EmptyBorder(25, 0, 0, 15));
		frame.getContentPane().add(panel_norte, BorderLayout.NORTH);
		panel_norte.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(HomeInterfaz.class.getResource("/resources/logo1.png")));
		panel_norte.add(lblNewLabel_1, BorderLayout.WEST);
		
		JLabel lblUnClickPara = new JLabel("<html>Ayuda: <br/>  1 Click selecciona video<br/> 2 Clicks reproduce el video.</html>");
		lblUnClickPara.setPreferredSize(new Dimension(300, 100));
		lblUnClickPara.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_norte.add(lblUnClickPara, BorderLayout.EAST);
		
		JPanel panel_sur = new JPanel();
		panel_sur.setBorder(new EmptyBorder(0, 0, 0, 0));
		frame.getContentPane().add(panel_sur, BorderLayout.SOUTH);
		
		JButton btnVolver = new JButton("Guardar y volver");
		panel_sur.add(btnVolver);
		
		JPanel panel_centro = new JPanel();
		panel_centro.setBorder(new EmptyBorder(20, 0, 0, 0));
		frame.getContentPane().add(panel_centro, BorderLayout.CENTER);
		GridBagLayout gbl_panel_centro = new GridBagLayout();
		gbl_panel_centro.columnWidths = new int[]{282, 0, 0};
		gbl_panel_centro.rowHeights = new int[] {80, 0, 0, 0};
		gbl_panel_centro.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_centro.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_centro.setLayout(gbl_panel_centro);
		
		
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
		modelo.addElement("Seleccionar lista");
		//inicializamos la lista.
		for(String nombre : listasUsuario.keySet()) {
			modelo.addElement(nombre);
			
		}
		
	
		
		JPanel panel_seleccionLista = new JPanel();
		GridBagConstraints gbc_panel_seleccionLista = new GridBagConstraints();
		gbc_panel_seleccionLista.insets = new Insets(0, 0, 5, 5);
		gbc_panel_seleccionLista.fill = GridBagConstraints.BOTH;
		gbc_panel_seleccionLista.gridx = 0;
		gbc_panel_seleccionLista.gridy = 0;
		panel_centro.add(panel_seleccionLista, gbc_panel_seleccionLista);
		
		JComboBox<String> combobox = new JComboBox<String>();
		
		combobox.setModel(modelo);
		
		panel_seleccionLista.add(combobox);
		
		JPanel panel_busqueda = new JPanel();
		panel_busqueda.setPreferredSize(new Dimension(300, 10));
		GridBagConstraints gbc_panel_busqueda = new GridBagConstraints();
		gbc_panel_busqueda.insets = new Insets(0, 0, 5, 0);
		gbc_panel_busqueda.fill = GridBagConstraints.BOTH;
		gbc_panel_busqueda.gridx = 1;
		gbc_panel_busqueda.gridy = 0;
		panel_centro.add(panel_busqueda, gbc_panel_busqueda);
		
		txtBuscarPorTitulo = new JTextField();
		txtBuscarPorTitulo.setText("Buscar por titulo del video");
		panel_busqueda.add(txtBuscarPorTitulo);
		txtBuscarPorTitulo.setColumns(30);
		
		JButton btnBuscarVideos = new JButton("Iniciar busqueda");
		panel_busqueda.add(btnBuscarVideos);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_centro.add(scrollPane, gbc_scrollPane);
		
		JPanel panelListaVideos = new JPanel();
		scrollPane.setViewportView(panelListaVideos);
		
		GridBagLayout gbl_panel_ListaVideos = new GridBagLayout();
		gbl_panel_ListaVideos.columnWidths = new int[] { 200 };
		gbl_panel_ListaVideos.rowHeights = new int[] { 180, 180, 180 };
		gbl_panel_ListaVideos.columnWeights = new double[] { 0.0 };
		gbl_panel_ListaVideos.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		panelListaVideos.setLayout(gbl_panel_ListaVideos);
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		panel_centro.add(scrollPane_1, gbc_scrollPane_1);
		
		JPanel panel_videos = new JPanel();
		scrollPane_1.setViewportView(panel_videos);
		GridBagLayout gbl_panel_videos = new GridBagLayout();
		gbl_panel_videos.columnWidths = new int[] { 200, 200 };
		gbl_panel_videos.rowHeights = new int[] { 180, 180, 180 };
		gbl_panel_videos.columnWeights = new double[] { 0.0 };
		gbl_panel_videos.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		panel_videos.setLayout(gbl_panel_videos);
		mostrarVideos(panel_videos);
		
		JPanel panelAñadirQuitar = new JPanel();
		panelAñadirQuitar.setBorder(new EmptyBorder(0, 25, 0, 25));
		GridBagConstraints gbc_panelAñadirQuitar = new GridBagConstraints();
		gbc_panelAñadirQuitar.insets = new Insets(0, 0, 0, 5);
		gbc_panelAñadirQuitar.fill = GridBagConstraints.BOTH;
		gbc_panelAñadirQuitar.gridx = 0;
		gbc_panelAñadirQuitar.gridy = 2;
		panel_centro.add(panelAñadirQuitar, gbc_panelAñadirQuitar);
		panelAñadirQuitar.setLayout(new BorderLayout(0, 0));
		
		
		JButton btnAdd = new JButton("Añadir");
		btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAñadirQuitar.add(btnAdd, BorderLayout.WEST);
		
		JButton btnQuitar = new JButton("Quitar");
		btnQuitar.setAlignmentY(Component.CENTER_ALIGNMENT);
		btnQuitar.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAñadirQuitar.add(btnQuitar, BorderLayout.EAST);
		
		
//--------------------ACTION LISTENERS----------------------//
		
		
		//action listener iniciar busqueda de videos.
		btnBuscarVideos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String buscar = txtBuscarPorTitulo.getText();
				JPanel panel_videos = new JPanel();
				scrollPane_1.setViewportView(panel_videos);
				panel_videos.setLayout(gbl_panel_videos);
				
				//mostramos los videos filtrados.
				mostrarVideos(panel_videos, buscar);
				
				panel_videos.repaint();
				
			}
		});
		
		//action listener añadir video a playlist
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panelListaVideos = new JPanel();
				scrollPane.setViewportView(panelListaVideos);
				panelListaVideos.setLayout(gbl_panel_ListaVideos);
				playlist.añadirVideo(seleccionado);
				mostrarVideosPlaylist(panelListaVideos);
				
				panelListaVideos.repaint();
				
			}
		});
		
		//action listener eliminar video de la playlist.
				btnQuitar.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						JPanel panelListaVideos = new JPanel();
						scrollPane.setViewportView(panelListaVideos);
						panelListaVideos.setLayout(gbl_panel_ListaVideos);
						playlist.eliminarVideo(seleccionadoPlaylist);
						mostrarVideosPlaylist(panelListaVideos);
						
						panelListaVideos.repaint();
						
					}
				});
		
		//action listener seleccionada otra playlist del usuario
		combobox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				//guardamos el estado de la anterior playlist y cambiamos a la nueva
				if(playlist!=null && e.getItem() != "Seleccionar lista") {//comprobamos que haya lista seleccionada
					//guardamos los cambios en la anterior lista.
					listasUsuario.put(playlist.getNombre(), playlist);
					
			
					//actualizamos y mostramos la lista seleccionada 
					playlist = listasUsuario.get(e.getItem());
					//mostramos
					JPanel panelListaVideos = new JPanel();
					scrollPane.setViewportView(panelListaVideos);
					panelListaVideos.setLayout(gbl_panel_ListaVideos);
					mostrarVideosPlaylist(panelListaVideos);
					
					panelListaVideos.repaint();
				}
					
				
			}
		});
		
		//action listener guardar playlist y volver a explorar.
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//persistimos los cambios.
				HashSet<ListaVideo> listas = new HashSet<ListaVideo>();
				for(ListaVideo l : listasUsuario.values()) {
					if(l.getCodigo()!=0) {
						listas.add(l);
					}
					
				}
				ControladorUsuario.getUnicaInstancia().modificarPlaylistUsuario(usuario.
						getEmail(),listas);
				//volvemos a la ventana de explorar.
				if(usuario.isPremium()) {
					ExplorarPremiumInterfaz explorarp = new ExplorarPremiumInterfaz(usuario, 
							videoweb, videosRecientes);
					frame.dispose();
					explorarp.mostrarVentana();
				}
				else {
					ExplorarInterfaz explorar = new ExplorarInterfaz(usuario, 
							videoweb, videosRecientes);
					frame.dispose();
					explorar.mostrarVentana();
					
				}
				
			}
		});
		
		
	}

	//metodo auxiliar  mostrar videos en el grid
	
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
			
			nueva.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(java.awt.event.MouseEvent e) {
				
				}
				
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					//en caso de hacer click una vez marcamos el video
					if(e.getClickCount() == 1) {
						seleccionado= vid;
						System.out.println(seleccionado.getTitulo());
					}
					//en caso de hacer doble click reproducimos el video.
					if(e.getClickCount() == 2) {
						videoweb.playVideo(vid.getUrl());
						// hacemos el dialogo visible
						reproductorVideo.setVisible(true);
						// añadimos el video a recientes.

						if (videosRecientes.size() == 5) {
							videosRecientes.remove(0);
							videosRecientes.add(vid);
						} else {
							videosRecientes.add(vid);
						}
					}
				}
				
				@Override
				public void mouseExited(java.awt.event.MouseEvent e) {
				
				}
				
				@Override
				public void mouseEntered(java.awt.event.MouseEvent e) {
			
				}
				
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					
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
			if (contx < 1) {
				contx++;
			} else {
				contx = 0;
				conty++;
			}
			indice++;

		}

	}
	
	private void mostrarVideos(JPanel panel, String busqueda) {
		int contx = 0;
		int conty = 0;
		int indice = 0;

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
			nueva.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(java.awt.event.MouseEvent e) {
				
				}
				
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					//en caso de hacer click una vez marcamos el video
					if(e.getClickCount() == 1) {
						seleccionado= vid;
					}
					//en caso de hacer doble click reproducimos el video.
					if(e.getClickCount() == 2) {
						videoweb.playVideo(vid.getUrl());
						// hacemos el dialogo visible
						reproductorVideo.setVisible(true);
						// añadimos el video a recientes.

						if (videosRecientes.size() == 5) {
							videosRecientes.remove(0);
							videosRecientes.add(vid);
						} else {
							videosRecientes.add(vid);
						}
					}
				}
				
				@Override
				public void mouseExited(java.awt.event.MouseEvent e) {
				
				}
				
				@Override
				public void mouseEntered(java.awt.event.MouseEvent e) {
			
				}
				
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					
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
			if (contx < 1) {
				contx++;
			} else {
				contx = 0;
				conty++;
			}
			indice++;

		}}

	}
	
	
	private void mostrarVideosPlaylist(JPanel panel) {
	
		int conty = 0;
		int indice = 0;

		for (Video vid : playlist.recuperarVideos()) {

			// cremos el panel para cada video.
			JPanel panelNuevo = new JPanel();
			panelNuevo.setLayout(new BoxLayout(panelNuevo, BoxLayout.Y_AXIS));
			panelNuevo.setPreferredSize(new Dimension(200, 140));

			JButton nueva = new JButton();
			nueva.setAlignmentX(Component.CENTER_ALIGNMENT);
			nueva.setPreferredSize(new Dimension(200, 200));
			nueva.setName(String.valueOf(indice));
			nueva.setIcon(videoweb.getThumb(vid.getUrl()));
			nueva.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(java.awt.event.MouseEvent e) {
				
				}
				
				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					//en caso de hacer click una vez marcamos el video
					if(e.getClickCount() == 1) {
						seleccionadoPlaylist= vid;
						System.out.println(seleccionadoPlaylist.getTitulo());
					}
					//en caso de hacer doble click reproducimos el video.
					if(e.getClickCount() == 2) {
						videoweb.playVideo(vid.getUrl());
						// hacemos el dialogo visible
						reproductorVideo.setVisible(true);
						// añadimos el video a recientes.

						if (videosRecientes.size() == 5) {
							videosRecientes.remove(0);
							videosRecientes.add(vid);
						} else {
							videosRecientes.add(vid);
						}
					}
				}
				
				@Override
				public void mouseExited(java.awt.event.MouseEvent e) {
				
				}
				
				@Override
				public void mouseEntered(java.awt.event.MouseEvent e) {
			
				}
				
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					
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
			gbc.gridx =0;
			gbc.gridy = conty;
			// lo añadimos al panel del parametro.
			panel.add(panelNuevo, gbc);

			// actualizamos los posicionadores del gridbag
			
			conty++;
			
			indice++;

		}
	}
	
}

