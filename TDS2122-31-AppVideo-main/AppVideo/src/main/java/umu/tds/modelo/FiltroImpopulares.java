package umu.tds.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroImpopulares implements FiltroVideo {

	@Override
	public List<Video> videosOk(List<Video> videos, Usuario usuario) {
		List<Video> filtrado = new ArrayList<Video>();
		
		
		filtrado = videos.stream()
				.filter(v -> v.getNumReproducciones() >4)
				.collect(Collectors.toList());
//		for(Video v : videos) {
//			if(v.getNumReproducciones() >4) {
//				filtrado.add(v);
//			}
//		}
		return filtrado;
	}

}
