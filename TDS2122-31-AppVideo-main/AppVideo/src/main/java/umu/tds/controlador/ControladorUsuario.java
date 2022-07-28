package umu.tds.controlador;
import umu.tds.persistencia.DAOException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import umu.tds.componente.Video;
import umu.tds.modelo.CatalogoUsuarios;
import umu.tds.modelo.FactoriaFiltros;
import umu.tds.modelo.FactoriaFiltrosException;
import umu.tds.modelo.FiltroVideo;
import umu.tds.modelo.ListaVideo;
import umu.tds.modelo.Usuario;
import umu.tds.persistencia.IAdaptadorUsuarioDAO;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IAdaptadorListaVideoDAO;


public class ControladorUsuario {
	
	private static ControladorUsuario unicaInstancia;
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorListaVideoDAO adaptadorListaVideo;
	
	private CatalogoUsuarios catalogoUsuarios;

	
	
	private ControladorUsuario() {
		inicializarAdaptadores();
		inicializarCatalogos();
	}
	
	public static ControladorUsuario getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorUsuario();
		return unicaInstancia;
	}
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorListaVideo = factoria.getListaVideoDAO();

	}
	
	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
	}
	
	public Usuario loginUsuario(String email, String clave) {
		Usuario u=catalogoUsuarios.getUsuario(email);
		if(u!=null && u.getClave().equals(clave)) {
			return u;
		}
		return null;
	}
	
	public boolean existeUsuarios(String email) {
		return catalogoUsuarios.getUsuario(email) != null;
	}
	
	public Usuario recuperarUsuario(String email) {
		return catalogoUsuarios.getUsuario(email);
	}
	
	public void registrarUsuario(String nombre,String apellidos,Date fechaNacimiento,String email,String clave) {
		//Controlamos que previamente no se haya registrado el email.
		Usuario usuario = new Usuario(nombre,apellidos,fechaNacimiento,email,clave);
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
	}
	
	public void añadirPlaylistUsuario(String email, ListaVideo lista) {
		//Persistimos la lista de videos
		adaptadorListaVideo.registrarListaVideo(lista);
		//modificamos el cambio en el usuario.
		Usuario u=catalogoUsuarios.getUsuario(email);
		u.addListaVideo(lista);
		adaptadorUsuario.modificarUsuario(u);
		catalogoUsuarios.addUsuario(u);

		
	}
	
	//metodo para actualizar todas las playlist del usuario, las modifica internamente y
	//eliminando las playlist no incluidas en el set.
	public void modificarPlaylistUsuario(String email, Set<ListaVideo> listas) {
		//modificamos las playlist
		for(ListaVideo listaVideo :listas) {
			System.out.println(listaVideo.getCodigo());
			adaptadorListaVideo.modificarListaVideo(listaVideo);
		}
		//añadimos los cambios en el objeto usuario
		Usuario u = catalogoUsuarios.getUsuario(email);
		u.setListasVideos(listas);
		adaptadorUsuario.modificarUsuario(u);
		catalogoUsuarios.addUsuario(u);
		
	}
	
	public Set<ListaVideo> recuperarPlaylistUsuario(String email) {
		 Usuario u=catalogoUsuarios.getUsuario(email);
		 return u.getListasVideos();
	}
	
	public void hacerPremiumUsuario(String email) {
		Usuario u = catalogoUsuarios.getUsuario(email);
		
		u.setPremium(true);
		
		//actualizamos el usuario en persisntecia y catalogo
		adaptadorUsuario.modificarUsuario(u);
		catalogoUsuarios.addUsuario(u);
	}
	public void eliminarPremiumUsuario(String email) {
		Usuario u = catalogoUsuarios.getUsuario(email);
		
		u.setPremium(false);
		
		//actualizamos el usuario en persisntecia y catalogo
		adaptadorUsuario.modificarUsuario(u);
		catalogoUsuarios.addUsuario(u);
	}

	public void generarPDFlistas(String directorio, String email) throws DocumentException, FileNotFoundException {
		FileOutputStream archivo = new FileOutputStream(directorio + "\\Mislistas.pdf");
	      Document documento = new Document();
	      PdfWriter.getInstance(documento, archivo);
	      documento.open();
	      documento.add((new Chunk("")));
	      Usuario u = catalogoUsuarios.getUsuario(email);
	      for(ListaVideo lista : u.getListasVideos()) {
	    	 documento.add(new Paragraph("Lista: " + lista.getNombre()));
	    	 for(umu.tds.modelo.Video video : lista.recuperarVideos()) {
	    		 documento.add(new Paragraph("         "+ video.getTitulo()+ ", "+ video.getNumReproducciones()+ " reproducciones."));
	    	 }
	      }
	      documento.close();
		
	}

	public List<umu.tds.modelo.Video> filtrarVideos(List<umu.tds.modelo.Video>videos, Usuario usuario,String filtro) throws FactoriaFiltrosException {
		FiltroVideo filtroCreado = FactoriaFiltros.getUnicaInstancia().getFiltroVideo(filtro);
		return filtroCreado.videosOk(videos, usuario);
	}
	
	
}
