package umu.tds.persistencia;

import java.util.List;
import umu.tds.modelo.ListaVideo;


public interface IAdaptadorListaVideoDAO {
	
	public void registrarListaVideo(ListaVideo listaVideo);
	public void borrarListaVideo(ListaVideo listaVideo);
	public void modificarListaVideo(ListaVideo listaVideo);
	public ListaVideo recuperarListaVideo(int codigo);
	public List<ListaVideo> recuperarTodosListaVideo(); 

}
