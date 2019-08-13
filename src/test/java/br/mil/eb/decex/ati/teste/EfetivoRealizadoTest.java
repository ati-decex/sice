package br.mil.eb.decex.ati.teste;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import br.mil.eb.decex.ati.controlador.RealizadoController;
import br.mil.eb.decex.ati.servico.EfetivoExistenteService;

public class EfetivoRealizadoTest {

	private RealizadoController controller;
	private EfetivoExistenteService loginService;
	//private FacesMessages facesMessages;

	@Before
	public void setup() {
		loginService = mock(EfetivoExistenteService.class);
		//facesMessages = mock(FacesMessages.class);

		controller = new RealizadoController();
		//controller.setFacesMessages(facesMessages);
		
	}

	@After
	public void after() {
		controller = null;
	}

	@Test
	public void invalidCredentialsShouldStayOnLoginPageAndGiveMessage() {
		controller.setExibirEspecialidades(true);
		//controller.setPassword("1234");

//stub
		//when(loginService.login("primo", "1234")).thenReturn(false);
		when(loginService.verificaSePrimeiroAcessoNoPeriodo()).thenReturn(true);

//execute command action
		String result = controller.getLabelQCP();

//outcome should be failed
		Assert.assertEquals(null,  result    );
		

//verify if message is added
		//verify(facesMessages).addError("Invalid login", "Wrong username/password combination");
	}

/*
	@Test
	public void correctCredentialsShouldLoginTheUser() {
		controller.setEfetivo(new EfetivoRealizado());
		//controller.setPassword("4444");

//stub
		when(loginService.login("primo", "4444")).thenReturn(true);

//execute command action
		String result = controller.loginClicked();

//outcome should be failed
		assertEquals("mainpage", result);
	}
	
*/
}