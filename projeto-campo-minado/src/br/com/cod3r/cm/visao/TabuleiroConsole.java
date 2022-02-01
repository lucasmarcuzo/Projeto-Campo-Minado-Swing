package br.com.cod3r.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.cod3r.cm.excecao.ExplosaoException;
import br.com.cod3r.cm.excecao.SairException;
import br.com.cod3r.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	
	private Tabuleiro tabuleiro;
	private Scanner scan = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while (continuar) {	
				cicloDoJogo();
				
				System.out.println("Deseja Jogar Novamente? (S/n) ");
				String resposta = scan.nextLine();
				
				if("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					tabuleiro.reiniciarJogo();
				}
			}
		} catch (SairException e) {
			System.out.println("Obrigado por ter jogado, até a próxima, tchau!!! :)");
		}finally {
			scan.close();
		}
	}
	
	private void cicloDoJogo() {
		try {
			
			while(!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				String digitado = capturarValorDigitado("Informe a posição da jogada: (x, y): ");
				
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
					  .map(e -> Integer.parseInt(e.trim())).iterator();
				
				digitado = capturarValorDigitado("Digite: \n1 - Abrir\n2 - (Des)Marcar: ");
				
				if("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				}else if ("2".equals(digitado)) {
					tabuleiro.alterarMarcacao(xy.next(), xy.next());
				}
				
			}
			
			mensagemSaidaJogo(true);
		} catch (ExplosaoException e) {
			mensagemSaidaJogo(false);
		}
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.println(texto);
		String digitado = scan.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		
		return digitado;
	}
	
	private void mensagemSaidaJogo(boolean resultado) {
		System.out.println(tabuleiro);	
		System.out.println(resultado ? "Você Ganhou! :D" : "Você Perdeu! :/");	
		System.out.println("Obrigado por ter jogado, até a próxima, tchau!!! :)");
	}

}
