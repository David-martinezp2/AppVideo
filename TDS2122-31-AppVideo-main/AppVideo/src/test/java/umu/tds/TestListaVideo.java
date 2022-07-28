package umu.tds;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import umu.tds.modelo.ListaVideo;
import umu.tds.modelo.Video;

public class TestListaVideo extends TestCase {

	ListaVideo lista;

	@Before
	public void setUp() {
		lista = new ListaVideo("prueba");
	}

	@Test
	public void test1_añadirVideo() {
		Video video = new Video("prueba", "www");
		lista.añadirVideo(video);

		assertTrue(lista.recuperarVideos().contains(video));
	}

	@Test
	public void test2_eliminarVideo() {
		Video video = new Video("prueba", "www");
		
		lista.eliminarVideo(video);
		assertTrue(lista.recuperarVideos().size() ==0 );
	}

}
