import java.util.Scanner;

public class Turno {

    // Atributos
    private Tabuleiro tabuleiro;

    private Jogador p1;

    private JogadorSuporte p2;

    private Graficos graficos;

    private Scanner entrada;

    private Jogador vencedor;

    // Resultado das últimas ações, mostrado depois que a tela é redesenhada
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

    // Métodos get
    public Jogador getVencedor() {
        return this.vencedor;
    }

    // Turno de um jogador (P1 ou P2): duas ações
    public void turnoJogador(Jogador jogador) {
        int acoes = 0;

        while (acoes < 2 && jogador.estaVivo() && this.vencedor == null) {
            this.graficos.imprimirJogo();
            System.out.print(this.mensagem);
            this.mensagem = "";

            System.out.println();
            System.out.println("Vez de " + this.nome(jogador) + " - acao " + (acoes + 1) + " de 2");
            System.out.println("1 - Atacar");
            System.out.println("2 - Procurar");
            System.out.println("3 - Movimentar");

            if (jogador == this.p2) {
                System.out.println("4 - Recuperar defesa");
            }

            System.out.print("Escolha: ");
            String opcao = this.lerEntrada();

            // Ação inválida não é contada
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

        int defesaAntes = jogador.getDefesa();

        inimigo.atacar(jogador, setor);

        String texto = "Inimigo " + inimigo.getAtaque() + "/" + inimigo.getDefesa();

        if (jogador.getDefesa() < defesaAntes) {
            this.mensagem = this.mensagem + texto + " acertou " + this.nome(jogador)
                    + " (-" + (defesaAntes - jogador.getDefesa()) + " DEF)\n";
        } else {
            this.mensagem = this.mensagem + texto + " errou o ataque em " + this.nome(jogador) + "\n";
        }

        if (!jogador.estaVivo()) {
            this.mensagem = this.mensagem + this.nome(jogador) + " foi eliminado!\n";
        }
    }

    // Escolhe a ação. Devolve true se a ação foi realizada
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

        // Procura o inimigo escolhido
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

        int defesaAntes = alvo.getDefesa();

        jogador.atacar(alvo, setor);

        if (alvo.getDefesa() < defesaAntes) {
            this.mensagem = this.nome(jogador) + " acertou o inimigo (-" + (defesaAntes - alvo.getDefesa()) + " DEF).\n";
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

        int defesaAntes = jogador.getDefesa();
        int defesaInimigosAntes = this.somaDefesaInimigos(setor);

        jogador.procurar(setor);

        if (jogador.getDefesa() > defesaAntes) {
            this.mensagem = this.nome(jogador) + " encontrou um item: +" + (jogador.getDefesa() - defesaAntes) + " DEF.\n";
        } else if (this.somaDefesaInimigos(setor) < defesaInimigosAntes) {
            this.mensagem = this.nome(jogador) + " encontrou um item: todos os inimigos perderam 1 DEF.\n";
        } else {
            this.mensagem = this.nome(jogador) + " nao encontrou nada.\n";
        }

        this.removerMortos(setor);
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

        if (this.tabuleiro.ehFonteInfeccao(novaPosicao)) {
            this.vencedor = jogador;
            return true;
        }

        // Setor novo: portas ficam visíveis e inimigos são gerados
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
                this.mensagem = "P2 recuperou 2 de DEF.\n";
                return true;

            case "2":
                if (!this.p1.estaVivo() || !this.p1.getPosicao().comparaCoordenadas(this.p2.getPosicao())) {
                    this.mensagem = "P1 precisa estar vivo e no mesmo setor que P2.\n";
                    return false;
                }

                this.p2.recuperar(this.p1);
                this.mensagem = "P2 recuperou 2 de DEF de P1.\n";
                return true;

            default:
                this.mensagem = "Opcao invalida.\n";
                return false;
        }
    }

    // Remove do setor os inimigos com DEF <= 0
    private void removerMortos(Setor setor) {
        for (Inimigo inimigo : setor.getInimigos()) {
            if (!inimigo.estaVivo()) {
                setor.removerInimigo(inimigo);
            }
        }
    }

    // Soma da DEF de todos os inimigos do setor
    private int somaDefesaInimigos(Setor setor) {
        int soma = 0;

        for (Inimigo inimigo : setor.getInimigos()) {
            soma = soma + inimigo.getDefesa();
        }

        return soma;
    }

    // Nome do jogador na tela
    private String nome(Jogador jogador) {
        if (jogador == this.p1) {
            return "P1";
        }

        return "P2";
    }

    // Lê uma linha digitada pelo usuário
    private String lerEntrada() {
        return this.entrada.nextLine();
    }

    // Fecha a leitura do teclado (chamar uma vez, no fim do jogo)
    public void encerrar() {
        this.entrada.close();
    }
}
