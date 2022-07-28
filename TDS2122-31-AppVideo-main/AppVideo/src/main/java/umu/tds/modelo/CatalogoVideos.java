package umu.tds.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;

import umu.tds.persistencia.IAdaptadorVideoDAO;

public class CatalogoVideos {

	private Map<Integer,Video> videos; 
	private HashSet<String> urls;
	private static CatalogoVideos unicaInstancia = new CatalogoVideos();
	
	private FactoriaDAO dao;
	private IAdaptadorVideoDAO adaptadorVideo;
	
	private CatalogoVideos() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorVideo = dao.getVideoDAO();
  			videos = new HashMap<Integer,Video>();
  			urls = new HashSet<String>();
  			
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoVideos getUnicaInstancia(){
		return unicaInstancia;
	}
	
	/*devuelve todos los videos*/
	public List<Video> getVideos(){
		ArrayList<Video> lista = new ArrayList<Video>();
		for (Video c:videos.values()) 
			lista.add(c);
		return lista;
	}
	
	public Video getVideo(int codigo) {
		for (Video c: videos.values()) {
			if (c.getCodigo()==codigo) return c;
		}
		return null;
	}
	public Video getVideo(String url) {
		for (Video c: videos.values()) {
			if (c.getUrl().equals(url)) return c;
		}
		return null;
	}
	public Boolean existeVideo(String url) {
		return urls.contains(url); 
	}
	
	public void addVideo(Video vi) {
		videos.put(vi.getCodigo(),vi);
		urls.add(vi.getUrl());
	}
	public void removeVideo (Video vi) {
		videos.remove(vi.getCodigo());
	}
	
	/*Recupera todos los videos para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Video> videosBD = adaptadorVideo.recuperarTodosVideos();
		 for (Video vi: videosBD) { 
			     videos.put(vi.getCodigo(),vi);
		 		 urls.add(vi.getUrl());
		 }
	}
}