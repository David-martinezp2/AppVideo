package umu.tds;

import static org.junit.Assert.*;

import org.junit.Test;

import umu.tds.controlador.ControladorUsuario;

public class Borra {

	@Test
	public void test() {
		assertTrue(ControladorUsuario.getUnicaInstancia().recuperarUsuario("a@um.es").isPremium());
	}

}
