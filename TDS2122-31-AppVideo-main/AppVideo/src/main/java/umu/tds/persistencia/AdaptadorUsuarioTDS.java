package umu.tds.persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.modelo.ListaVideo;
import umu.tds.modelo.Usuario;
import umu.tds.modelo.Video;

//Usa un pool para evitar problemas doble referencia con ventas
public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;
	
	private SimpleDateFormat dateFormat; // para formatear la fecha de venta en
	// la base de datos

	public static AdaptadorUsuarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorUsuarioTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	/* cuando se registra un usuario se le asigna un identificador �nico */
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario=null;
		
		
		// Si la entidad est� registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {
			
			System.out.println("null pointer RegistrarUsuario AdaptadorUsuario");
		}
		if (eUsuario != null) return;

		
		// crear entidad usuario
		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList( new Propiedad("nombre", usuario.getNombre()),
						new Propiedad("apellidos", usuario.getApellidos()),
						new Propiedad("fechaNacimiento",dateFormat.format(usuario.getFechaNacimiento())),
						new Propiedad("email", usuario.getEmail()),
						new Propiedad("clave", usuario.getClave()),
						new Propiedad("isPremium", String.valueOf(usuario.isPremium())) ,
						new Propiedad("listasVideos",""))));//inicialmente un usuario se registra sin playlist.
		
		// registrar entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId()); 
	}

	public void borrarUsuario(Usuario usuario) {
		// No se comprueban restricciones de integridad con Venta
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		
		servPersistencia.borrarEntidad(eUsuario);
	}

	public void modificarUsuario(Usuario usuario) {

		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
	
		for (Propiedad prop :eUsuario.getPropiedades()) {
			if(prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(usuario.getCodigo()));
			}
			else if(prop.getNombre().equals("nombre")){
				prop.setValor(usuario.getNombre());
			}
			else if (prop.getNombre().equals("apellidos")) {
				prop.setValor(usuario.getApellidos());
			} else if (prop.getNombre().equals("email")) {
				prop.setValor(String.valueOf(usuario.getEmail()));
			}  
			else if (prop.getNombre().equals("clave")) {
				prop.setValor(String.valueOf(usuario.getClave()));
			} 
			else if (prop.getNombre().equals("isPremium")) {
				prop.setValor(String.valueOf(String.valueOf(usuario.isPremium())));
			} 
			else if (prop.getNombre().equals("listasVideos")) {
				prop.setValor(obtenerCodigosPlaylist(usuario.getListasVideos()));
				
			} 
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Usuario recuperarUsuario(int codigo) {

		// Si la entidad est� en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		Entidad eUsuario;
		String nombre;
		String email;
		String apellidos;
		Date fechaNacimiento=null;
		String clave;
		Boolean isPremium;
		
		

		// recuperar entidad
		eUsuario = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, "apellidos");
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		try {
			fechaNacimiento = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		clave= servPersistencia.recuperarPropiedadEntidad(eUsuario, "clave");
		isPremium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, "isPremium"));

		Usuario usuario = new Usuario(nombre,apellidos,fechaNacimiento, email,clave);
		usuario.setApellidos(apellidos);
		usuario.setClave(clave);
		usuario.setFechaNacimiento(fechaNacimiento);
		usuario.setPremium(isPremium);
		usuario.setCodigo(codigo);
		
		
		// recuperaramos los videos de la lista.
		
		
		List<ListaVideo> listasvideos = obtenerPlaylistDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "listasVideos"));

		for (ListaVideo vi : listasvideos)
			usuario.addListaVideo(vi);

		
		PoolDAO.getUnicaInstancia().addObjeto(codigo, usuario);


		return usuario;
	}

	public List<Usuario> recuperarTodosUsuarios() {

		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}
	
	
	//FUNCIONES AUXILIARES
	
		public String obtenerCodigosPlaylist(Set<ListaVideo> listaVideo) {
			String cadena = "";
			for (ListaVideo vi : listaVideo) {
				cadena+= vi.getCodigo()+ " ";
			}
			return cadena;
		}
		
		
		private List<ListaVideo> obtenerPlaylistDesdeCodigos(String listaVideos) {
			List<ListaVideo> listavids = new LinkedList<ListaVideo>();
			StringTokenizer strTok = new StringTokenizer(listaVideos, " ");
			AdaptadorListaVideoTDS adaptadorLV = AdaptadorListaVideoTDS.getUnicaInstancia();
			while (strTok.hasMoreTokens()) {
				listavids.add(adaptadorLV.recuperarListaVideo(Integer.valueOf((String) strTok.nextElement())));
			}
			return listavids;
		}


}
