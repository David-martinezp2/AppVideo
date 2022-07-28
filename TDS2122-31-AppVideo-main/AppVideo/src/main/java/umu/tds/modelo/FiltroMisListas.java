package umu.tds.modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FiltroMisListas implements FiltroVideo{

	@Override
	public List<Video> videosOk(List<Video> videos, Usuario usuario) {
		
		Set<ListaVideo> listasUsuario = usuario.getListasVideos();

//		for (Video v : videos) {
//		for(ListaVideo lista : usuario.getListasVideos()) {
//			if(lista.containsVideo(v)) {
//				flag =1;
//			}
//		}
//		if(flag ==0) 
//			filtrado.add(v);
//		flag=0;
//	}

		
		return videos.stream().filter(v ->listasUsuario.stream().
				noneMatch(l -> l.containsVideo(v))).collect(Collectors.toList());
		

		
	}

}
