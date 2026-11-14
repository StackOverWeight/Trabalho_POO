/*
 * Trabalho Prático - Paradigmas de Programação
 * Arthur Paul Nickel - GRR20252825
 * Bruno Yuuki Hayashi - GRR...
 * Gabriel Dobrowolski Jorge - GRR...
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
        /* Decisões temporárias  do programa (podem mudar) ========================================== */
            //Leitura de ação de casos de erro são tratados nos turnos
            //O laço que percorre os inimigos do setor está na main, mas poderia estar em turno
            //Verificação de setor oculto dentro de turno, não dentro de atacar de jogador
            //Movimentação dentro dos turnos

        //...

        /* Inicialização dos elementos do jogo */

        Jogador P1 = new Jogador();
        JogadorSuporte P2 = new JogadorSuporte();
        Tabuleiro tabuleiro = new Tabuleiro();
        Graficos graficos = new Graficos(tabuleiro, P1, P2);
        Turno turno = new Turno(tabuleiro, P1, P2, graficos);

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

        /* Jogo terminou sem vencedores */

        graficos.imprimirJogo();

        terminarJogo(turno.getVencedor(), p1, p2);
        turno.encerrar();
    }
}