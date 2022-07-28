package umu.tds;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import umu.tds.modelo.CatalogoUsuarios;
import umu.tds.modelo.Usuario;

public class TestCatalogoUsuarios extends TestCase {

	private Date fecha;
	private Usuario usuario;
	private Usuario usuario2;
	private CatalogoUsuarios catalogoUsuario;

	@Before
	public void setUp() {

		fecha = new Date(System.currentTimeMillis());
		usuario = new Usuario("Paco", "Paquito", fecha, "paquito@gmail.com", "123");
		usuario2 = new Usuario("Lucia", "perez", fecha, "lucia@gmail.com", "123");
		catalogoUsuario = CatalogoUsuarios.getUnicaInstancia();

	}

	@Test
	public void test1_addUsuarioYgetUsuario() {

		catalogoUsuario.addUsuario(usuario);

		assertEquals(catalogoUsuario.getUsuario(usuario.getEmail()).getEmail(), usuario.getEmail());

	}

	@Test
	public void test2_getUsuarios() {
		// previamente hemos añadido 1 en el metodo addUsuario
		catalogoUsuario.addUsuario(usuario2);
		assertTrue(catalogoUsuario.getUsuarios().size() >= 1);

	}

	@Test
	public void test3_removeUsuario() {
		catalogoUsuario.removeUsuario(usuario);
		assertNull(catalogoUsuario.getUsuario(usuario.getEmail()));
	}

}
