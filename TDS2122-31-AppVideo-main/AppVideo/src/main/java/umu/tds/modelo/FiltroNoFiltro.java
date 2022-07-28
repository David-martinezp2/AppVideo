package umu.tds.modelo;

import java.util.List;

public class FiltroNoFiltro implements FiltroVideo {

	@Override
	public List<Video> videosOk(List<Video> videos, Usuario usuario) {
		return videos;
	}

}
