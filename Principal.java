/*
 * Trabalho Pratico - Paradigmas de Programacao
 * Arthur Paul Nickel - GRR20252825
 * Bruno Yuuki Hayashi - GRR20253206
 * Gabriel Dobrowolski Jorge - GRR20250938
 * Luiz Guilherme de Souza Mo - GRR...
 */
public class Principal {

    public static void terminarJogo(Jogador vencedor, Jogador p1, JogadorSuporte p2) {
        if (vencedor == p1) {
            System.out.println("P1 venceu o jogo!");
        } else if (vencedor == p2) {
            System.out.println("P2 venceu o jogo!");
        } else if (!p1.estaVivo() && !p2.estaVivo()) {
            System.out.println("Derrota: os dois jogadores foram eliminados.");
        } else {
            System.out.println("Derrota: os 25 ciclos acabaram.");
        }
    }

    public static void main (String[] args) {
        /* Inicializacao dos elementos do jogo */

        Jogador p1 = new Jogador();
        JogadorSuporte p2 = new JogadorSuporte();
        Tabuleiro tabuleiro = new Tabuleiro();
        Graficos graficos = new Graficos(tabuleiro, p1, p2);
        Turno turno = new Turno(tabuleiro, p1, p2, graficos);

        /* Loop principal do jogo */

        int ciclo = 0;
        while (ciclo < 25 && (p1.estaVivo() || p2.estaVivo()) && turno.getVencedor() == null) {
            turno.turnoJogador(p1);

            turno.turnoJogador(p2);

            /* Turno dos inimigos - cada inimigo ataca 1 vez cada jogador */

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
        
        graficos.imprimirJogo();

        terminarJogo(turno.getVencedor(), p1, p2);
        turno.encerrar();
    }
}