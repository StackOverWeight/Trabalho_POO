import java.util.Scanner;

// Controla os turnos: le a entrada do usuario, valida as acoes e chama os metodos
// dos jogadores e inimigos. Guarda o vencedor e as mensagens de resultado
public class Turno {

    // Constantes
    private static final int ACOES_POR_TURNO = 2;

    // Atributos
    private Tabuleiro tabuleiro;

    private Jogador p1;

    private JogadorSuporte p2;

    private Graficos graficos;

    private Scanner entrada;

    private Jogador vencedor;

    // Resultado das ultimas acoes, mostrado depois que a tela e redesenhada
    private String mensagem;

    // Construtor
    public Turno(Tabuleiro tabuleiro, Jogador p1, JogadorSuporte p2, Graficos graficos) {
        this.tabuleiro = tabuleiro;
        this.p1 = p1;
        this.p2 = p2;
        this.graficos = graficos;
        this.entrada = new Scanner(System.in);
        this.vencedor = null;
        this.mensagem = "";
    }

    // Metodos get
    public Jogador getVencedor() {
        return this.vencedor;
    }

    // Metodos publicos
    // Turno de um jogador (P1 ou P2): duas acoes. Acao invalida nao e contada
    public void turnoJogador(Jogador jogador) {
        int acoes = 0;

        while (acoes < ACOES_POR_TURNO && jogador.estaVivo() && this.vencedor == null) {
            this.mostrarTela();

            System.out.println();
            System.out.println("Vez de " + this.nome(jogador) + " - acao " + (acoes + 1) + " de " + ACOES_POR_TURNO);
            System.out.println("1 - Atacar");
            System.out.println("2 - Procurar");
            System.out.println("3 - Movimentar");

            if (jogador == this.p2) {
                System.out.println("4 - Recuperar defesa");
            }

            System.out.print("Escolha: ");
            String opcao = this.lerEntrada();

            if (this.executarAcao(jogador, opcao)) {
                acoes++;
            }
        }
    }

    // Turno do inimigo: um inimigo ataca um jogador
    public void turnoInimigo(Jogador jogador, Inimigo inimigo, Setor setor) {
        if (!jogador.estaVivo() || !inimigo.estaVivo()) {
            return;
        }

        String texto = "Inimigo " + inimigo.getAtaque() + "/" + inimigo.getDefesa();

        if (this.resolverAtaque(inimigo, jogador, setor)) {
            this.mensagem = this.mensagem + texto + " acertou " + this.nome(jogador)
                    + " (-" + inimigo.getAtaque() + " DEF)\n";
        } else {
            this.mensagem = this.mensagem + texto + " errou o ataque em " + this.nome(jogador) + "\n";
        }

        if (!jogador.estaVivo()) {
            this.mensagem = this.mensagem + this.nome(jogador) + " foi eliminado!\n";
        }
    }

    // Redesenha a tela e mostra as mensagens que ainda nao foram exibidas
    public void mostrarTela() {
        this.graficos.imprimirJogo();
        System.out.print(this.mensagem);
        this.mensagem = "";
    }

    // Fecha a leitura do teclado (chamar uma vez, no fim do jogo)
    public void encerrar() {
        this.entrada.close();
    }

    // Metodos privados
    // Escolhe a acao. Devolve true se a acao foi realizada
    private boolean executarAcao(Jogador jogador, String opcao) {
        switch (opcao) {
            case "1":
                return this.atacar(jogador);

            case "2":
                return this.procurar(jogador);

            case "3":
                return this.movimentar(jogador);

            case "4":
                if (jogador == this.p2) {
                    return this.recuperar();
                }

                this.mensagem = "Somente P2 pode recuperar defesa.\n";
                return false;

            default:
                this.mensagem = "Opcao invalida.\n";
                return false;
        }
    }

    // Atacar um inimigo do setor
    private boolean atacar(Jogador jogador) {
        Setor setor = this.tabuleiro.getSetor(jogador.getPosicao());

        if (!setor.possuiInimigos()) {
            this.mensagem = "Nao ha inimigos neste setor.\n";
            return false;
        }

        // Lista os inimigos numerados
        int numero = 1;
        for (Inimigo inimigo : setor.getInimigos()) {
            System.out.println(numero + " - Inimigo " + inimigo.getAtaque() + "/" + inimigo.getDefesa());
            numero++;
        }

        System.out.print("Qual inimigo atacar? ");
        String escolha = this.lerEntrada();

        // Procura o inimigo escolhido (comparando texto: letras nao quebram o programa)
        Inimigo alvo = null;
        numero = 1;
        for (Inimigo inimigo : setor.getInimigos()) {
            if (escolha.equals("" + numero)) {
                alvo = inimigo;
            }
            numero++;
        }

        if (alvo == null) {
            this.mensagem = "Inimigo invalido.\n";
            return false;
        }

        if (this.resolverAtaque(jogador, alvo, setor)) {
            this.mensagem = this.nome(jogador) + " acertou o inimigo (-" + jogador.getAtaque() + " DEF).\n";
        } else {
            this.mensagem = this.nome(jogador) + " errou o ataque (setor oculto).\n";
        }

        if (!alvo.estaVivo()) {
            this.mensagem = this.mensagem + "Inimigo eliminado!\n";
        }

        this.removerMortos(setor);
        return true;
    }

    // Procurar itens no setor
    private boolean procurar(Jogador jogador) {
        Setor setor = this.tabuleiro.getSetor(jogador.getPosicao());

        if (setor.getTipo() == TipoSetor.Privado) {
            this.mensagem = "Setor privado: nao e possivel procurar.\n";
            return false;
        }

        // O numero sorteado diz qual item foi encontrado
        int numero = jogador.procurar(setor);

        switch (numero) {
            case 4:
                this.mensagem = this.nome(jogador) + " encontrou um item: +1 DEF.\n";
                break;

            case 5:
                this.mensagem = this.nome(jogador) + " encontrou um item: +2 DEF.\n";
                break;

            case 6:
                if (setor.possuiInimigos()) {
                    this.mensagem = this.nome(jogador) + " encontrou um item: todos os inimigos perderam 1 DEF.\n";
                } else {
                    this.mensagem = this.nome(jogador) + " encontrou um item que tira 1 DEF dos inimigos, mas nao ha inimigos no setor.\n";
                }
                break;

            default:
                this.mensagem = this.nome(jogador) + " nao encontrou nada.\n";
                break;
        }

        // Inimigos que morreram com o item (6) saem do setor
        int inimigosAntes = setor.getInimigos().size();

        this.removerMortos(setor);

        int eliminados = inimigosAntes - setor.getInimigos().size();

        if (eliminados > 0) {
            this.mensagem = this.mensagem + eliminados + " inimigo(s) eliminado(s)!\n";
        }

        return true;
    }

    // Movimentar para um setor vizinho
    private boolean movimentar(Jogador jogador) {
        Coordenada posicao = jogador.getPosicao();
        Setor setor = this.tabuleiro.getSetor(posicao);

        if (setor.possuiInimigos()) {
            this.mensagem = "Elimine os inimigos do setor antes de se movimentar.\n";
            return false;
        }

        System.out.print("Direcao (N - Norte, S - Sul, L - Leste, O - Oeste): ");
        String escolha = this.lerEntrada();

        Direcao direcao;

        switch (escolha) {
            case "N":
            case "n":
                direcao = Direcao.Norte;
                break;

            case "S":
            case "s":
                direcao = Direcao.Sul;
                break;

            case "L":
            case "l":
                direcao = Direcao.Leste;
                break;

            case "O":
            case "o":
                direcao = Direcao.Oeste;
                break;

            default:
                this.mensagem = "Direcao invalida.\n";
                return false;
        }

        if (!this.tabuleiro.podeMover(posicao, direcao)) {
            this.mensagem = "Nao ha porta nessa direcao.\n";
            return false;
        }

        Coordenada novaPosicao = this.tabuleiro.getCoordenadaVizinha(posicao, direcao);

        jogador.movimentar(this.tabuleiro.getSetor(novaPosicao));

        // Entrou no X: vence o jogo
        if (this.tabuleiro.ehFonteInfeccao(novaPosicao)) {
            this.vencedor = jogador;
            return true;
        }

        // Setor novo: portas ficam visiveis e inimigos sao gerados
        this.tabuleiro.visitarSetor(novaPosicao);

        this.mensagem = this.nome(jogador) + " se movimentou.\n";
        return true;
    }

    // Habilidade especial do P2: +2 DEF nele ou no P1 (mesmo setor)
    private boolean recuperar() {
        System.out.println("1 - Recuperar P2");
        System.out.println("2 - Recuperar P1");
        System.out.print("Escolha: ");
        String escolha = this.lerEntrada();

        switch (escolha) {
            case "1":
                this.p2.recuperar(this.p2);
                this.mensagem = "P2 recuperou " + JogadorSuporte.BONUS_RECUPERACAO + " de DEF.\n";
                return true;

            case "2":
                if (!this.p1.estaVivo() || !this.p1.getPosicao().compararCoordenadas(this.p2.getPosicao())) {
                    this.mensagem = "P1 precisa estar vivo e no mesmo setor que P2.\n";
                    return false;
                }

                this.p2.recuperar(this.p1);
                this.mensagem = "P2 recuperou " + JogadorSuporte.BONUS_RECUPERACAO + " de DEF de P1.\n";
                return true;

            default:
                this.mensagem = "Opcao invalida.\n";
                return false;
        }
    }

    // Qualquer Atacante (Jogador ou Inimigo) ataca um alvo. Devolve true se acertou
    private boolean resolverAtaque(Atacante atacante, Entidade alvo, Setor setor) {
        int defesaAntes = alvo.getDefesa();

        // O Java executa o atacar do tipo real do objeto (Jogador ou Inimigo)
        atacante.atacar(alvo, setor);

        return alvo.getDefesa() < defesaAntes;
    }

    // Remove do setor os inimigos com DEF <= 0
    private void removerMortos(Setor setor) {
        for (Inimigo inimigo : setor.getInimigos()) {
            if (!inimigo.estaVivo()) {
                setor.removerInimigo(inimigo);
            }
        }
    }

    // Nome do jogador na tela
    private String nome(Jogador jogador) {
        if (jogador == this.p1) {
            return "P1";
        }

        return "P2";
    }

    // Le uma linha digitada pelo usuario.
    // Se a entrada acabar (ex.: Ctrl+Z / Ctrl+D), encerra o jogo sem erro
    private String lerEntrada() {
        if (!this.entrada.hasNextLine()) {
            System.out.println();
            System.out.println("Entrada encerrada. Fim do jogo.");
            System.exit(0);
        }

        return this.entrada.nextLine();
    }
}
