package umu.tds.persistencia;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.modelo.Video;

//Usa un pool para evitar problemas doble referencia con ventas
public class AdaptadorVideoTDS implements IAdaptadorVideoDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorVideoTDS unicaInstancia = null;
	


	public static AdaptadorVideoTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorVideoTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorVideoTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		
	}

	/* cuando se registra un Video se le asigna un identificador �nico */
	public void registrarVideo(Video video) {
		Entidad eVideo=null;
		
		
		// Si la entidad est� registrada no la registra de nuevo
		try {
			eVideo = servPersistencia.recuperarEntidad(video.getCodigo());
		} catch (NullPointerException e) {
			
			System.out.println("null pointer RegistrarVideo AdaptadorVideo");
		}
		if (eVideo != null) return;

		
		// crear entidad Video
		eVideo = new Entidad();
		eVideo.setNombre("video");
		eVideo.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList( new Propiedad("titulo", video.getTitulo()),
						new Propiedad("url", video.getUrl()),
						new Propiedad("numReproducciones", String.valueOf(video.getNumReproducciones())) ,
						new Propiedad("etiquetas",video.getStringEtiquetas())  )));
						
		// registrar entidad video
		eVideo = servPersistencia.registrarEntidad(eVideo);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		video.setCodigo(eVideo.getId()); 
	}

	public void borrarVideo(Video video) {
		// No se comprueban restricciones de integridad con Venta
		Entidad eVideo = servPersistencia.recuperarEntidad(video.getCodigo());
		
		servPersistencia.borrarEntidad(eVideo);
	}

	public void modificarVideo(Video video) {

		Entidad eVideo = servPersistencia.recuperarEntidad(video.getCodigo());
		
		for (Propiedad prop :eVideo.getPropiedades()) {
			if(prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(video.getCodigo()));
			}
			else if(prop.getNombre().equals("url")){
				prop.setValor(video.getUrl());
			}
			else if (prop.getNombre().equals("titulo")) {
				prop.setValor(video.getTitulo());
			} else if (prop.getNombre().equals("numReproducciones")) {
				prop.setValor(String.valueOf(String.valueOf(video.getNumReproducciones())));
			} 
			else if (prop.getNombre().equals("etiquetas")) {
				prop.setValor(video.getStringEtiquetas());
			} 
			servPersistencia.modificarPropiedad(prop);
		}


	}

	public Video recuperarVideo(int codigo) {

		// Si la entidad est� en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Video) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		Entidad eVideo;
		String titulo;
		String url;
		int numReproducciones;
		HashSet<String> etiquetas;
		
		

		// recuperar entidad
		eVideo = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		titulo = servPersistencia.recuperarPropiedadEntidad(eVideo, "titulo");
		url = servPersistencia.recuperarPropiedadEntidad(eVideo, "url");
		numReproducciones = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eVideo, "numReproducciones"));
		etiquetas = stringEtiquetasASet( servPersistencia.recuperarPropiedadEntidad(eVideo, "etiquetas"));
		
		Video video = new Video(titulo,url,etiquetas);
		video.setNumReproducciones(numReproducciones);
		video.setCodigo(codigo);

		// IMPORTANTE:a�adir el Video al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, video);


		return video;
	}

	public List<Video> recuperarTodosVideos() {

		List<Entidad> eVideos = servPersistencia.recuperarEntidades("video");
		List<Video> videos = new LinkedList<Video>();

		for (Entidad eVideo : eVideos) {
			videos.add(recuperarVideo(eVideo.getId()));
		}
		return videos;
	}
	
	
	//FUNCIONES AUXILIARES
	
	public HashSet<String> stringEtiquetasASet(String cadenaEtiquetas){
		HashSet<String> etiqs= new HashSet<String>();
		for (String et:cadenaEtiquetas.split(" ")){
			etiqs.add(et);
		}	
		return etiqs;
	}


}
