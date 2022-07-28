package umu.tds.controlador;
import umu.tds.persistencia.DAOException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventObject;
import java.util.List;

import umu.tds.componente.CargadorVideos;
import umu.tds.componente.IVideosListener;
import umu.tds.componente.Video;
import umu.tds.componente.Videos;
import umu.tds.componente.VideosEvent;
import umu.tds.modelo.CatalogoUsuarios;
import umu.tds.modelo.CatalogoVideos;
import umu.tds.modelo.Usuario;
import umu.tds.persistencia.IAdaptadorUsuarioDAO;
import umu.tds.persistencia.IAdaptadorVideoDAO;
import umu.tds.persistencia.TDSFactoriaDAO;
import umu.tds.persistencia.FactoriaDAO;

public class ControladorVideo {
	
	

	private static ControladorVideo unicaInstancia;
	
	private IAdaptadorVideoDAO adaptadorVideo;
	
	private CatalogoVideos catalogoVideos;
	
	CargadorVideos cargador;
	
	
	private ControladorVideo() {
		cargador = new CargadorVideos();
		inicializarAdaptadores();
		catalogoVideos= CatalogoVideos.getUnicaInstancia();
	}
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorVideo = factoria.getVideoDAO();

	}
	
	public static ControladorVideo getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorVideo();
		return unicaInstancia;
	}
	
	public void cargarVideos(File xml) {
		cargador.addVideosListener(new IVideosListener() {
			
			@Override
			public void enteradoCambioVideos(EventObject event) {
		
				Videos nuevos = ((VideosEvent) event).getVideos();
				//Insertamos los videos que no esten actualmente en el catalogo
				
				
				for (Video vi : nuevos.getVideo()) {//comparamos por url,
											//no puede ser igual en 2 videos diferentes
					
					if(!catalogoVideos.existeVideo(vi.getURL())) {
						
						umu.tds.modelo.Video insert= new umu.tds.modelo.Video
									(vi.getTitulo(), vi.getURL());//creamos el video de nuestro modelo
						//añadimos las etiquetas
						for(String e: vi.getEtiqueta())
							insert.addEtiqueta(e);
						
						//añadimos el nuevo video al catalogo y a la BBDD.
						
						catalogoVideos.addVideo(insert);
						adaptadorVideo.registrarVideo(insert);
					}
				}				
			}
		});
		
		cargador.setArchivoVideos(xml.getAbsolutePath());
	
	}
	
	public List<umu.tds.modelo.Video> obtenerVideos(){
		return adaptadorVideo.recuperarTodosVideos();
	}
	
	public ArrayList<umu.tds.modelo.Video> videosMasReproducidos(){
		ArrayList<umu.tds.modelo.Video> videos = (ArrayList<umu.tds.modelo.Video>) catalogoVideos.getVideos();
		
		videos.sort( Comparator.comparing( umu.tds.modelo.Video::getNumReproducciones, 
				  Comparator.reverseOrder()));
		
		ArrayList<umu.tds.modelo.Video> out = new ArrayList<umu.tds.modelo.Video>();
		
		if(videos.size()> 20) {
			for(int i =0 ; i<10; i++) {
				out.add(videos.get(i));
			}
			return out;
		}
		return videos;
	}
	
	
	public void añadirReproduccionVideo(umu.tds.modelo.Video vid) {
		vid.setNumReproducciones(vid.getNumReproducciones()+1);
		//actualizamos el video en persistencia y catalogo
		adaptadorVideo.modificarVideo(vid);
		catalogoVideos.addVideo(vid);
		
	}
	
	
}
