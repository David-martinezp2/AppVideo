package umu.tds.modelo;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroMenores implements FiltroVideo {

	@Override
	public List<Video> videosOk(List<Video> videos, Usuario usuario) {
		LocalDate nacimiento = usuario.getFechaNacimiento().toInstant().atZone(
					ZoneId.systemDefault()).toLocalDate();
		Period edad =Period.between( nacimiento, LocalDate.now());
		List<Video> filtrado = new ArrayList<Video>();
		if(edad.getYears() > 18 ) {
			return videos;
		}
		
		filtrado = videos.stream().filter(v -> v.getStringEtiquetas().contains("Adultos") == false).collect(Collectors.toList());
//		for(Video v : videos ) {
//			if(!v.getStringEtiquetas().contains("Adultos")) {
//				filtrado.add(v);
//			}
//		}
		return filtrado;
	}

}
