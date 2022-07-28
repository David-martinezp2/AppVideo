package umu.tds.modelo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ListaVideo {
	private int codigo;
	private String nombre;
	private Set<Video> videos;

	public ListaVideo(String nombre) {
		codigo=0;
		this.nombre = nombre;
		videos = new HashSet<Video>();
	}
	
	public ListaVideo(String nombre, Set<Video> videos) {
		this.nombre=nombre;
		this.videos=videos;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void  setCodigo(int codigo) {
		this.codigo=codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	
	public boolean containsVideo(Video v) {
		return videos.contains(v);
	}
	
	public void añadirVideo(Video vi) {
		videos.add(vi);
	}
	public void eliminarVideo(Video vi) {
		videos.remove(vi);
	}
	public Set<Video> recuperarVideos(){
		return Collections.unmodifiableSet(videos);
	}
}

