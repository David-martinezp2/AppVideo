package umu.tds.modelo;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.print.attribute.UnmodifiableSetException;


public class Video {

	private int codigo;
	private String url;
	private String titulo;
	private Set<String> etiquetas;
	private int numReproducciones;
	
	public Video(String titulo, String url) {
		this.codigo=0;
		this.titulo=titulo;
		this.url=url;
		etiquetas= new HashSet<String>();
		numReproducciones=0;
	}
	
	public Video(String titulo, String url, HashSet<String> etiquetas) {
		this.codigo=0;
		this.titulo=titulo;
		this.url=url;
		this.etiquetas= etiquetas;
		numReproducciones=0;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo=codigo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void addEtiqueta(String etiqueta) {
		etiquetas.add(etiqueta);
	}	
	public void eliminarEtiqueta(String etiqueta) {
		etiquetas.remove(etiqueta);
	}
	public boolean tieneEtiqueta(String etiqueta) {
		return etiquetas.contains(etiqueta);
	}
	public Set<String> recuperarEtiquetas(){
		return Collections.unmodifiableSet(etiquetas);
	}
	
	public int getNumReproducciones() {
		return numReproducciones;
	}
	public void setNumReproducciones(int numReproducciones) {
		this.numReproducciones = numReproducciones;
	}
	
	
	//funcion auxiliar usada en el adaptadorVideo
	
	public String getStringEtiquetas() {
		String salida="";
		for(String et : etiquetas) {
			salida+= et + " ";
		}
		return salida.trim();
	}


	
	
	
}