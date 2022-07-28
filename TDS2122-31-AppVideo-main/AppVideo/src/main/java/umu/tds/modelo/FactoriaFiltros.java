package umu.tds.modelo;

public class FactoriaFiltros {
	
	private static FactoriaFiltros unicaInstancia;

	public static final String FILROS_TDS = "umu.tds.modelo.FactoriaFiltros";
	private static FiltroVideo filtroMislistas;
	private static FiltroVideo filtroImpopulares;
	private static FiltroVideo filtroMenores;
	private static FiltroVideo filtroNoFiltro;
		
	/** 
	 * Crea un tipo de factoria DAO.
	 * Solo existe el tipo TDSFactoriaDAO
	 */
	public static FactoriaFiltros getUnicaInstancia() throws FactoriaFiltrosException{
		if (unicaInstancia == null)
			try {
				unicaInstancia=new FactoriaFiltros();
				filtroMislistas = new FiltroMisListas();
				filtroImpopulares = new FiltroImpopulares();
				filtroMenores = new FiltroMenores();
				filtroNoFiltro = new FiltroNoFiltro();
				
				
			} catch (Exception e) {	
				throw new FactoriaFiltrosException();
			} 
		return unicaInstancia;
	}



	/* Constructor */
	protected  FactoriaFiltros() {}
		
		
	// Metodos para obtener el filtro deseado.
	//así unicamente necesitamos una instancia de cada uno.
	public FiltroVideo getFiltroVideo(String filtro) {
		switch (filtro) {
		case "Adultos":
			return filtroMenores;
		case "Eliminar impopulares":
			return filtroImpopulares;
		case "No en mis listas":
			return filtroMislistas;
		case "No filtrar":
			return filtroNoFiltro;
		default:
			 return null;
		}
	}
	
	
}
