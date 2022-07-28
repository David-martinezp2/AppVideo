package umu.tds;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import umu.tds.modelo.CatalogoVideos;
import umu.tds.modelo.Video;

public class TestCatalogoVideos extends TestCase {

	private CatalogoVideos catalogoVideos;
	private Video video;
	private Video video1;

	@Before
	public void setUp() {

		catalogoVideos = CatalogoVideos.getUnicaInstancia();
		video = new Video("Prueba", "www");
		video1 = new Video("video", "qqq");

	}

	@Test
	public void test1_añadirVideoYgetVideo() {

		catalogoVideos.addVideo(video);
		assertEquals(catalogoVideos.getVideo(video.getUrl()).getUrl(), video.getUrl());
		assertTrue(catalogoVideos.existeVideo(video.getUrl()));
	}


	@Test
	public void test2_getVideos() {
		catalogoVideos.addVideo(video1);
		assertTrue(catalogoVideos.getVideos().size() >= 1);

	}

	@Test
	public void test4_removeVideo() {
		catalogoVideos.removeVideo(video);
		assertNull(catalogoVideos.getVideo(video.getUrl()));
	}

}
