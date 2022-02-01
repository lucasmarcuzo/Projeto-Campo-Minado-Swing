package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Campo {
	
	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	 
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	
	public boolean isDesmarcado() {
		return !isMarcado();
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}
	
	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !isAberto();
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean isSemMina() {
		return !isMinado();
	}
	
	public int getLinha() {
		return linha;
	}
	
	public int getColuna() {
		return coluna;
	}
	
	//Método para mostrar os vizinhos que não tem bomba em volta do campo que foi selecionado.
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = linha != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);	
			return true;
		}else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}
	}	
		
	//Marcar & Desmarcar determinado campo.
	void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
		}
	}
	
	/*
	 * Se campo não estiver aberto e marcado, o campo então é aberto. 
	 * Se estiver minado o Jogo é encerrado. 
	 * Se a vizinhaça do campo for segura, isso é não tem minas nos campos próximos, 
	 * então esses campos são abertos.
	 */
	boolean abrir() {
		if(!aberto && !marcado) {
			aberto = true;
			
			if(minado) {
				throw new ExplosaoException();
			}
			
			if(vizinhacaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			
			return true;
			
		}else return false;
	}
	
	//Verificar se os campos vizinhos são seguros para se abrir.
	boolean vizinhacaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	//Minar campo passado.
	void minar() {
		minado = true;
	}
	
	//Verificar se determinado campo foi Desvendado ou está protegido.
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	//Contas quantas minas tem em volta da jogada feita.
	long minasNaVizinhaca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	//Reiniciar campos do jogo.
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "x";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasNaVizinhaca() > 0) {
			return Long.toString(minasNaVizinhaca());
		} else if (aberto) {
			return " ";
		} else {
			return "?";
		}
	}
	
	
}
