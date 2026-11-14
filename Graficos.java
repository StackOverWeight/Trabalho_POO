//T: Rever nome

public class Graficos {

    // Atributos
    private Tabuleiro tabuleiro;

    private Jogador p1;

    private JogadorSuporte p2;

    // Construtor
    public Graficos(Tabuleiro tabuleiro, Jogador p1, JogadorSuporte p2) {
        this.tabuleiro = tabuleiro;
        this.p1 = p1;
        this.p2 = p2;
    }

    public void limparTela() {
        // Limpa o ecrã e move o cursor para o canto superior esquerdo
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Imprimir jogo (tabuleiro)
    public void imprimirJogo() {
        this.limparTela();

        // Letras vermelhas com fundo preto
        System.out.print(Cores.ANSI_RED + Cores.ANSI_BLACK_BACKGROUND);

        // Título
        System.out.println("-----------------------------");
        System.out.println("|   Antivirus por um dia    |");
        System.out.println("-----------------------------");

        // Números das colunas
        System.out.println("      1   2   3   4   5");

        // Borda superior (limite do tabuleiro: sempre parede)
        System.out.print("    |");
        for (int x = 0; x < 5; x++) {
            System.out.print("---|");
        }
        System.out.println();

        // Linhas do tabuleiro
        for (int y = 0; y < 5; y++) {
            this.imprimeColunas(y);
            this.imprimeLinha(y);
        }

        // Volta às cores normais do terminal
        System.out.print(Cores.ANSI_RESET);
    }

    // Imprime colunas(tabuleiro, linha): conteúdo dos setores + portas/paredes laterais
    private void imprimeColunas(int linha) {
        System.out.print((linha + 1) + "   |");

        for (int x = 0; x < 5; x++) {
            System.out.print(this.conteudoSetor(x, linha));

            if (this.portaVisivel(x, linha, Direcao.Leste)) {
                System.out.print("*");
            } else {
                System.out.print("|");
            }
        }

        this.imprimePaineis(linha * 2);
    }

    // Imprime linha(tabuleiro, linha): borda inferior da linha + portas para o Sul
    private void imprimeLinha(int linha) {
        System.out.print("    |");

        for (int x = 0; x < 5; x++) {
            System.out.print("-");

            if (this.portaVisivel(x, linha, Direcao.Sul)) {
                System.out.print("*");
            } else {
                System.out.print("-");
            }

            System.out.print("-|");
        }

        this.imprimePaineis(linha * 2 + 1);
    }

    // Conteúdo de um setor (sempre 3 caracteres)
    private String conteudoSetor(int x, int y) {
        Coordenada coordenada = new Coordenada((byte) x, (byte) y);

        // Jogador eliminado some do tabuleiro
        boolean temP1 = this.p1.estaVivo() && this.p1.getPosicao().comparaCoordenadas(coordenada);
        boolean temP2 = this.p2.estaVivo() && this.p2.getPosicao().comparaCoordenadas(coordenada);

        if (x == 2 && y == 2) {
            return " C ";
        }

        if (this.tabuleiro.ehFonteInfeccao(coordenada)) {
            return " X ";
        }

        if (temP1 && temP2) {
            return " P ";
        }

        if (temP1) {
            return "P1 ";
        }

        if (temP2) {
            return "P2 ";
        }

        return "   ";
    }

    // A porta só aparece se um dos dois setores ligados por ela já foi visitado
    private boolean portaVisivel(int x, int y, Direcao direcao) {
        Coordenada coordenada = new Coordenada((byte) x, (byte) y);

        if (!this.tabuleiro.temPorta(coordenada, direcao)) {
            return false;
        }

        if (this.tabuleiro.setorFoiVisitado(coordenada)) {
            return true;
        }

        Coordenada vizinha = this.tabuleiro.getCoordenadaVizinha(coordenada, direcao);

        if (vizinha == null) {
            return false;
        }

        return this.tabuleiro.setorFoiVisitado(vizinha);
    }

    // Imprime ao lado do tabuleiro a linha do(s) painel(is) de setor
    private void imprimePaineis(int linhaPainel) {
        if (linhaPainel <= 8) {
            Coordenada posicaoP1 = this.p1.getPosicao();
            Coordenada posicaoP2 = this.p2.getPosicao();

            // Jogador eliminado não tem painel
            boolean p1Vivo = this.p1.estaVivo();
            boolean p2Vivo = this.p2.estaVivo();

            if (p1Vivo && p2Vivo && posicaoP1.comparaCoordenadas(posicaoP2)) {
                // Os dois jogadores no mesmo setor: um painel só
                System.out.print("     " + this.linhaPainel(posicaoP1, linhaPainel, true, true));
            } else {
                // Setores diferentes: um painel para cada jogador vivo
                if (p1Vivo) {
                    System.out.print("     " + this.linhaPainel(posicaoP1, linhaPainel, true, false));
                }

                if (p2Vivo) {
                    System.out.print("     " + this.linhaPainel(posicaoP2, linhaPainel, false, true));
                }
            }
        }

        System.out.println();
    }

    // Uma linha do painel de um setor (sempre 15 caracteres)
    private String linhaPainel(Coordenada coordenada, int linhaPainel, boolean mostraP1, boolean mostraP2) {
        Setor setor = this.tabuleiro.getSetor(coordenada);

        // Jogadores que aparecem no painel (P1 sempre primeiro)
        String nome1 = "";
        String status1 = "";
        String nome2 = "";
        String status2 = "";

        if (mostraP1) {
            nome1 = "P1";
            status1 = this.p1.getAtaque() + "/" + this.p1.getDefesa();
        }

        if (mostraP2) {
            if (mostraP1) {
                nome2 = "P2";
                status2 = this.p2.getAtaque() + "/" + this.p2.getDefesa();
            } else {
                nome1 = "P2";
                status1 = this.p2.getAtaque() + "/" + this.p2.getDefesa();
            }
        }

        switch (linhaPainel) {
            case 0:
                return this.completarEspacos("Setor [" + (coordenada.getY() + 1) + "," + (coordenada.getX() + 1) + "]", 15);

            case 2:
                return "|------" + this.simboloPorta(coordenada, Direcao.Norte, "-") + "------|";

            case 3:
                return "| " + this.completarEspacos(this.textoInimigos(setor), 11) + " |";

            case 4:
                return "|             |";

            case 5:
                return this.simboloPorta(coordenada, Direcao.Oeste, "|") + "             "
                        + this.simboloPorta(coordenada, Direcao.Leste, "|");

            case 6:
                return "| " + this.completarEspacos(nome1, 7) + this.completarEspacos(nome2, 4) + " |";

            case 7:
                return "| " + this.completarEspacos(status1, 7) + this.completarEspacos(status2, 4) + " |";

            case 8:
                return "|------" + this.simboloPorta(coordenada, Direcao.Sul, "-") + "------|";

            default:
                return "               ";
        }
    }

    // "*" se o setor tem porta na direção; senão, o símbolo de parede
    private String simboloPorta(Coordenada coordenada, Direcao direcao, String parede) {
        if (this.tabuleiro.temPorta(coordenada, direcao)) {
            return "*";
        }

        return parede;
    }

    // Inimigos do setor no formato "1/1 3/3 2/2"
    private String textoInimigos(Setor setor) {
        String texto = "";
        int quantidade = 0;

        for (Inimigo inimigo : setor.getInimigos()) {
            if (quantidade > 0) {
                texto = texto + " ";
            }

            texto = texto + inimigo.getAtaque() + "/" + inimigo.getDefesa();
            quantidade++;
        }

        return texto;
    }

    // Completa o texto com espaços até o tamanho desejado
    private String completarEspacos(String texto, int tamanho) {
        for (int i = texto.length(); i < tamanho; i++) {
            texto = texto + " ";
        }

        return texto;
    }
}
