package br.com.cod3r.cm.modelo;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class CampoTeste {
	
	private Campo campo = new Campo(3,3);
	
	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3,3);
	}
	
	@Test
	void testeVizinhoExistenteDistancia1Esquerda() {
		Campo vizinhoEsquerda = new Campo(3,2);		
		boolean resultadoEsquerda = campo.adicionarVizinho(vizinhoEsquerda);
		assertTrue(resultadoEsquerda);
	}
	
	@Test
	void testeVizinhoExistenteDistancia1Direita() {
		Campo vizinhoDireita = new Campo(3,4);		
		boolean resultadoDireita = campo.adicionarVizinho(vizinhoDireita);
		assertTrue(resultadoDireita);
	}
	
	@Test
	void testeVizinhoExistenteDistancia1EmCima() {
		Campo vizinhoEmCima = new Campo(2,3);		
		boolean resultadoEmCima = campo.adicionarVizinho(vizinhoEmCima);
		assertTrue(resultadoEmCima);
	}
	
	@Test
	void testeVizinhoExistenteDistancia1EmBaixo() {
		Campo vizinhoEmBaixo = new Campo(4,3);		
		boolean resultadoEmBaixo = campo.adicionarVizinho(vizinhoEmBaixo);
		assertTrue(resultadoEmBaixo);
	}
	
	@Test
	void testeVizinhoExistenteDistancia2Diagonal() {
		Campo vizinhoEmDiagonal = new Campo(2,2);		
		boolean resultadoDiagonal = campo.adicionarVizinho(vizinhoEmDiagonal);
		assertTrue(resultadoDiagonal);
	}
	
	@Test
	void testeVizinhoNaoExistenteDistancia2Diagonal() {
		Campo vizinhoEmDiagonal = new Campo(1,1);		
		boolean resultadoDiagonal = campo.adicionarVizinho(vizinhoEmDiagonal);
		assertFalse(resultadoDiagonal);
	}
	
	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAbrirNãoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	
	@Test
	void testeAbrirNãoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();
		
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
	}
	
	@Test
	void testeAbrirComVizinhos() {
		Campo campo11 = new Campo(1,1);
		Campo campo22 = new Campo(2,2);
		campo22.adicionarVizinho(campo11);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isAberto());
	}
	
	@Test
	void testeAbrirComVizinhos2() {
		Campo campo11 = new Campo(1,1);
		Campo campo12 = new Campo(1,1);
		campo12.minar();
		
		Campo campo22 = new Campo(2,2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isFechado());
	}
	
	
	@Test
	void testeReiniciar() {
		Campo campo = new Campo(1,1);
		campo.alternarMarcacao();
		campo.minar();
		campo.abrir();
		
		campo.reiniciar();
		assertTrue(campo.isFechado() && campo.isSemMina() && campo.isDesmarcado());
	}
	
	@Test
	void testeObjetivoAlcancadoDesvendado() {
		Campo campo = new Campo(1,1);
		campo.abrir();
		
		assertTrue(campo.objetivoAlcancado());
	}
	
	@Test
	void testeObjetivoAlcancadoProtegido() {
		Campo campo = new Campo(1,1);
		campo.minar();
		campo.alternarMarcacao();
		
		assertTrue(campo.objetivoAlcancado());
	}
	
	@Test
	void testeToStringMarcado() {
		Campo campo = new Campo(1,1);	
		campo.alternarMarcacao();
		
		assertEquals("x", campo.toString());
	}
	
	@Test
	void testeToStringAbertoMinado() {
		Campo campo = new Campo(1,1);	
		campo.abrir();
		campo.minar();
		
		assertEquals("*", campo.toString());
	}
	
	@Test
	void testeToStringAberto() {
		Campo campo = new Campo(1,1);	
		campo.abrir();
		
		assertEquals(" ", campo.toString());
	}
	
	@Test
	void testeToStringFechado() {
		Campo campo = new Campo(1,1);	
		
		assertEquals("?", campo.toString());
	}
	

	
	
	
	


}
