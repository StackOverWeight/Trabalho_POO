// Desenha o jogo no terminal: tabuleiro + painel do setor de cada jogador.
// So le o estado do jogo (metodos get); nunca altera tabuleiro, jogadores ou inimigos
public class Graficos {

    // Constantes
    // Largura (em caracteres) de uma linha do painel de setor
    private static final int LARGURA_PAINEL = 15;

    // Ultima linha do painel (o painel ocupa as linhas 0 a 8 ao lado do tabuleiro)
    private static final int ULTIMA_LINHA_PAINEL = 8;

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

    // Metodos publicos
    // Limpa a tela e imprime o tabuleiro inteiro com os paineis
    public void imprimirJogo() {
        this.limparTela();

        // Letras vermelhas com fundo preto
        System.out.print(Cores.ANSI_RED + Cores.ANSI_BLACK_BACKGROUND);

        // Titulo
        System.out.println("-----------------------------");
        System.out.println("|   Antivirus por um dia    |");
        System.out.println("-----------------------------");

        // Numeros das colunas
        System.out.println("      1   2   3   4   5");

        // Borda superior (limite do tabuleiro: sempre parede)
        System.out.print("    |");
        for (int x = 0; x < Tabuleiro.TAMANHO; x++) {
            System.out.print("---|");
        }
        System.out.println();

        // Linhas do tabuleiro
        for (int y = 0; y < Tabuleiro.TAMANHO; y++) {
            this.imprimirColunas(y);
            this.imprimirLinha(y);
        }

        // Volta as cores normais do terminal
        System.out.print(Cores.ANSI_RESET);
    }

    // Metodos privados
    // Limpa a tela e move o cursor para o canto superior esquerdo
    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Conteudo dos setores de uma linha + portas/paredes laterais
    private void imprimirColunas(int linha) {
        System.out.print((linha + 1) + "   |");

        for (int x = 0; x < Tabuleiro.TAMANHO; x++) {
            System.out.print(this.conteudoSetor(x, linha));

            if (this.portaVisivel(x, linha, Direcao.Leste)) {
                System.out.print("*");
            } else {
                System.out.print("|");
            }
        }

        this.imprimirPaineis(linha * 2);
    }

    // Borda inferior de uma linha + portas para o Sul
    private void imprimirLinha(int linha) {
        System.out.print("    |");

        for (int x = 0; x < Tabuleiro.TAMANHO; x++) {
            System.out.print("-");

            if (this.portaVisivel(x, linha, Direcao.Sul)) {
                System.out.print("*");
            } else {
                System.out.print("-");
            }

            System.out.print("-|");
        }

        this.imprimirPaineis(linha * 2 + 1);
    }

    // Imprime ao lado do tabuleiro uma linha do(s) painel(is) de setor
    private void imprimirPaineis(int linhaPainel) {
        if (linhaPainel <= ULTIMA_LINHA_PAINEL) {
            Coordenada posicaoP1 = this.p1.getPosicao();
            Coordenada posicaoP2 = this.p2.getPosicao();

            // Jogador eliminado nao tem painel
            boolean p1Vivo = this.p1.estaVivo();
            boolean p2Vivo = this.p2.estaVivo();

            if (p1Vivo && p2Vivo && posicaoP1.compararCoordenadas(posicaoP2)) {
                // Os dois jogadores no mesmo setor: um painel so
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

    // Conteudo de um setor no tabuleiro (sempre 3 caracteres)
    private String conteudoSetor(int x, int y) {
        Coordenada coordenada = new Coordenada(x, y);

        // Jogador eliminado some do tabuleiro
        boolean temP1 = this.p1.estaVivo() && this.p1.getPosicao().compararCoordenadas(coordenada);
        boolean temP2 = this.p2.estaVivo() && this.p2.getPosicao().compararCoordenadas(coordenada);

        if (x == Tabuleiro.CENTRO && y == Tabuleiro.CENTRO) {
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

    // A porta so aparece se um dos dois setores ligados por ela ja foi visitado
    private boolean portaVisivel(int x, int y, Direcao direcao) {
        Coordenada coordenada = new Coordenada(x, y);

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

    // Uma linha do painel de um setor (sempre LARGURA_PAINEL caracteres)
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
                return this.completarEspacos("Setor [" + (coordenada.getY() + 1) + "," + (coordenada.getX() + 1) + "]",
                        LARGURA_PAINEL);

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
                // Linha 1: espaco vazio entre o titulo e o desenho do setor
                return this.completarEspacos("", LARGURA_PAINEL);
        }
    }

    // "*" se o setor tem porta na direcao; senao, o simbolo de parede
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

    // Completa o texto com espacos ate o tamanho desejado
    private String completarEspacos(String texto, int tamanho) {
        for (int i = texto.length(); i < tamanho; i++) {
            texto = texto + " ";
        }

        return texto;
    }
}
