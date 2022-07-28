package umu.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.modelo.ListaVideo;
import umu.tds.modelo.Video;

public class AdaptadorListaVideoTDS implements IAdaptadorListaVideoDAO {
	
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorListaVideoTDS unicaInstancia = null;
	

	public static AdaptadorListaVideoTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorListaVideoTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorListaVideoTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void registrarListaVideo(ListaVideo listaVideo) {
		Entidad eLista=null;
		try {
			eLista = servPersistencia.recuperarEntidad(listaVideo.getCodigo());
		}catch (NullPointerException e) {}
		if(eLista != null)return;
		
		//registramos primero los video que contiene la lista de video.
		AdaptadorVideoTDS adapV = AdaptadorVideoTDS.getUnicaInstancia();
		for(Video v: listaVideo.recuperarVideos()) {
			adapV.registrarVideo(v);
		}
		
		//creamos la entidad listaVideo
		
		eLista = new Entidad();
		eLista.setNombre("listaVideo");
		eLista.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("nombre", listaVideo.getNombre()),
						new Propiedad("videos", obtenerCodigosVideos(listaVideo.recuperarVideos()) ))));
		
		//registramos la entidad lista
		eLista = servPersistencia.registrarEntidad(eLista);
		
		//asginamos el codigo obtenido
		listaVideo.setCodigo(eLista.getId());
	}

	@Override
	public void borrarListaVideo(ListaVideo listaVideo) {
		Entidad eLista;
		//no borramos los videos en caso de eliminar una playlist.
		eLista = servPersistencia.recuperarEntidad(listaVideo.getCodigo());
		servPersistencia.borrarEntidad(eLista);
		
	}

	@Override
	public void modificarListaVideo(ListaVideo listaVideo) {
		
		Entidad eLista= servPersistencia.recuperarEntidad(listaVideo.getCodigo());
		
		for (Propiedad prop :eLista.getPropiedades()) {
			if(prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(listaVideo.getCodigo()));
			}
			else if(prop.getNombre().equals("nombre")){
				prop.setValor(listaVideo.getNombre());
			}
			else if (prop.getNombre().equals("videos")) {
				prop.setValor(obtenerCodigosVideos(listaVideo.recuperarVideos()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
			
	}

	@Override
	public ListaVideo recuperarListaVideo(int codigo) {
		// Comprobamos si esta en el pool
				if (PoolDAO.getUnicaInstancia().contiene(codigo))
					return (ListaVideo) PoolDAO.getUnicaInstancia().getObjeto(codigo);

				// Else recuperamos de la base de datos.
				Entidad eVenta = servPersistencia.recuperarEntidad(codigo);

				//recuperamos las propiedades.	
				String nombre = servPersistencia.recuperarPropiedadEntidad(eVenta, "nombre");
				
				ListaVideo lista = new ListaVideo(nombre);
				lista.setCodigo(codigo);

				//añadimos al pool antes de llamar a otros adaptadores.
				PoolDAO.getUnicaInstancia().addObjeto(codigo, lista);

				// recuperaramos los videos de la lista.
				List<Video> videos = new LinkedList<Video>();
				String cadenavideos = servPersistencia.recuperarPropiedadEntidad(eVenta, "videos");
				if(cadenavideos != null) {
					videos = obtenerVideosDesdeCodigos(cadenavideos);
				}
					
				for (Video vi : videos)
					lista.añadirVideo(vi);

				PoolDAO.getUnicaInstancia().addObjeto(codigo, lista);
				
				return lista;
				
				
	}

	@Override
	public List<ListaVideo> recuperarTodosListaVideo() {
		List<ListaVideo> videos = new LinkedList<ListaVideo>();
		List<Entidad> eVideos = servPersistencia.recuperarEntidades("video");

		for (Entidad eVideo : eVideos) {
			videos.add(recuperarListaVideo(eVideo.getId()));
		}
		return videos;
	}
	
	//FUNCIONES AUXILIARES
	
	public String obtenerCodigosVideos(Set<Video> videos) {
		String cadena = "";
		for (Video vi : videos) {
			cadena+= vi.getCodigo()+ " ";
		}
		return cadena;
	}
	
	
	private List<Video> obtenerVideosDesdeCodigos(String videos) {
		List<Video> vids = new LinkedList<Video>();
		StringTokenizer strTok = new StringTokenizer(videos, " ");
		AdaptadorVideoTDS adaptadorLV = AdaptadorVideoTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			vids.add(adaptadorLV.recuperarVideo(Integer.valueOf((String) strTok.nextElement())));
		}
		return vids;
	}
	
}
