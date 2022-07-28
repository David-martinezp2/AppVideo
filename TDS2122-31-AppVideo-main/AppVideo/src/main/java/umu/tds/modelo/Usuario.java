package umu.tds.modelo;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Usuario {
	
	private int codigo;
	private String nombre;
	private String apellidos;
	private Date fechaNacimiento;
	private String usuario;
	private String clave;
	private String email;
	private boolean isPremium;
	private Set<ListaVideo> listasVideos;
	
	
	public Usuario(String nombre,String apellidos,Date fechaNacimiento,String email,String clave) {
		this.codigo=0;
		this.nombre=nombre;
		this.email=email;
		this.fechaNacimiento=fechaNacimiento;
		this.clave=clave;
		this.apellidos=apellidos;
		isPremium=false;
		listasVideos= new HashSet<ListaVideo>();
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Set<ListaVideo> getListasVideos() {
		return listasVideos;
	}

	public void setListasVideos(Set<ListaVideo> listasVideos) {
		this.listasVideos = listasVideos;
	}
	
	public void addListaVideo(ListaVideo lista) {
		listasVideos.add(lista);
	}
	
	
	
	
	
	

}
