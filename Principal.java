public class Principal {

    /* Status do jogo:
     *  0 - Jogo chegou a 25 turnos e não houve vitória de nenhum jogador
     *  1 - P1 venceu (achou a fonte de infecção)
     *  2 - P2 venceu (achou a fonte de infecção)
     **/
    public static void terminarJogo(Jogador P1, Jogador P2, byte status) {
        if (status == 0) {
            System.out.println("O jogo terminou sem vencedores.");
        } else if (status == 1) {
            System.out.println("P1 venceu o jogo!");
        } else if (status == 2) {
            System.out.println("P2 venceu o jogo!");
        }

        System.out.println("Status dos jogadores:");
        System.out.println("P1: " + (P1.estaVivo() ? "Vivo" : "Morto"));
        System.out.println("P2: " + (P2.estaVivo() ? "Vivo" : "Morto"));
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

        // Entra em um ciclo - While de 25 ciclos
        // Condições extras: foi achado fonte de infecção e pelo menos 1 jogador vivo
        int i = 0;
        while (i < 25 && (P1.estaVivo() || P2.estaVivo())) {

            /* Turno dos jogadores */

            if (P1.estaVivo()) {
                Turno turno = new Turno(tabuleiro, P1, P2, new Graficos(tabuleiro, P1, P2));
                turno.turnoJogador(P1);
                if (tabuleiro.ehFonteInfeccao(P1.getPosicao())) { //Verifica se P1 ganhou
                    terminarJogo(P1, P2, (byte) 1);
                    return;
                }
            }

            if (P2.estaVivo()) {
                Turno turno = new Turno(tabuleiro, P1, P2, new Graficos(tabuleiro, P1, P2));
                turno.turnoJogador(P2);
                if (tabuleiro.ehFonteInfeccao(P2.getPosicao())) { //Verifica se P2 ganhou
                    terminarJogo(P1, P2, (byte) 2);
                    return;
                }
            }

            /* Turno dos inimigos - cada inimigo ataca 1 vez cada jogador */

            if (P1.estaVivo()) {
                for (Inimigo inimigo : tabuleiro.getSetor(P1.getPosicao()).getInimigos()) {
                    Turno turno = new Turno(tabuleiro, P1, (JogadorSuporte) P2, new Graficos(tabuleiro, P1, P2));
                    turno.turnoInimigo(P1, inimigo, tabuleiro.getSetor(P1.getPosicao()));
                }
            }

            if (P2.estaVivo()) {
                for (Inimigo inimigo : tabuleiro.getSetor(P2.getPosicao()).getInimigos()) {
                    Turno turno = new Turno(tabuleiro, P2, (JogadorSuporte) P1, new Graficos(tabuleiro, P1, P2));
                    turno.turnoInimigo(P2, inimigo, tabuleiro.getSetor(P2.getPosicao()));
                }
            }
        }

        /* Jogo terminou sem vencedores */
        terminarJogo(P1, P2, (byte) 0);
    }
}