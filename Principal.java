/*
 * Trabalho Pratico - Paradigmas de Programacao
 * Arthur Paul Nickel - GRR20252825
 * Bruno Yuuki Hayashi - GRR20253206
 * Gabriel Dobrowolski Jorge - GRR20250938
 * Luiz Guilherme de Souza Mo - GRR20252981
 */
public class Principal {

    // Constantes
    private static final int MAXIMO_CICLOS = 25;

    // Ponto de entrada: cria os objetos e repete os ciclos ate o jogo acabar
    public static void main(String[] args) {
        // Inicializacao dos elementos do jogo
        Jogador p1 = new Jogador();
        JogadorSuporte p2 = new JogadorSuporte();
        Tabuleiro tabuleiro = new Tabuleiro();
        Graficos graficos = new Graficos(tabuleiro, p1, p2);
        Turno turno = new Turno(tabuleiro, p1, p2, graficos);

        // Ciclo: turno do P1, turno do P2 e turno dos inimigos
        int ciclo = 0;
        while (ciclo < MAXIMO_CICLOS && (p1.estaVivo() || p2.estaVivo()) && turno.getVencedor() == null) {
            turno.turnoJogador(p1);
            turno.turnoJogador(p2);

            // Turno dos inimigos: cada inimigo ataca 1 vez cada jogador do seu setor
            if (turno.getVencedor() == null) {
                Setor setorP1 = tabuleiro.getSetor(p1.getPosicao());
                for (Inimigo inimigo : setorP1.getInimigos()) {
                    turno.turnoInimigo(p1, inimigo, setorP1);
                }

                Setor setorP2 = tabuleiro.getSetor(p2.getPosicao());
                for (Inimigo inimigo : setorP2.getInimigos()) {
                    turno.turnoInimigo(p2, inimigo, setorP2);
                }
            }

            ciclo++;
        }

        // Tela final, com as ultimas mensagens (ex.: ataques e eliminacoes do ultimo ciclo)
        turno.mostrarTela();

        terminarJogo(turno.getVencedor(), p1, p2);
        turno.encerrar();
    }

    // Mostra o resultado final do jogo
    private static void terminarJogo(Jogador vencedor, Jogador p1, JogadorSuporte p2) {
        if (vencedor == p1) {
            System.out.println("P1 venceu o jogo!");
        } else if (vencedor == p2) {
            System.out.println("P2 venceu o jogo!");
        } else if (!p1.estaVivo() && !p2.estaVivo()) {
            System.out.println("Derrota: os dois jogadores foram eliminados.");
        } else {
            System.out.println("Derrota: os " + MAXIMO_CICLOS + " ciclos acabaram.");
        }
    }
}
