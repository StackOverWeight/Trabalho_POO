// Grade 5x5 de setores, com as portas fixas e a fonte de infeccao (X) sorteada
public class Tabuleiro {

    // Constantes
    public static final int TAMANHO = 5;
    public static final int CENTRO = 2;

    // Atributos
    private Setor[][] setores;

    private Coordenada fonteInfeccao;

    // Construtor
    public Tabuleiro() {
        this.setores = new Setor[TAMANHO][TAMANHO];

        this.inicializarSetores();
        this.configurarPortas();
        this.configurarTiposSetores();
        this.posicionarFonteInfeccao();

        // Centro comeca visitado (portas visiveis), mas sem gerar inimigos
        this.getSetor(new Coordenada(CENTRO, CENTRO)).marcarComoVisitado();
    }

    // Metodos get
    public Setor getSetor(Coordenada coordenada) {
        if (!this.coordenadaValida(coordenada)) {
            throw new IllegalArgumentException("Coordenada fora dos limites do tabuleiro.");
        }

        return this.setores[coordenada.getX()][coordenada.getY()];
    }

    // Metodos publicos
    public boolean ehFonteInfeccao(Coordenada coordenada) {
        if (!this.coordenadaValida(coordenada)) {
            return false;
        }

        return this.fonteInfeccao.compararCoordenadas(coordenada);
    }

    public boolean temPorta(Coordenada coordenada, Direcao direcao) {
        return this.getSetor(coordenada).temPorta(direcao);
    }

    public void visitarSetor(Coordenada coordenada) {
        this.getSetor(coordenada).visitar();
    }

    public boolean setorFoiVisitado(Coordenada coordenada) {
        return this.getSetor(coordenada).foiVisitado();
    }

    // So e possivel mover se existir porta ligando os dois setores
    public boolean podeMover(Coordenada coordenada, Direcao direcao) {
        if (!this.coordenadaValida(coordenada)) {
            return false;
        }

        if (!this.temPorta(coordenada, direcao)) {
            return false;
        }

        Coordenada vizinha = this.getCoordenadaVizinha(coordenada, direcao);

        if (vizinha == null) {
            return false;
        }

        return this.temPorta(vizinha, direcao.oposta());
    }

    // Coordenada do setor ao lado na direcao dada, ou null se sair do tabuleiro
    public Coordenada getCoordenadaVizinha(Coordenada coordenada, Direcao direcao) {
        if (!this.coordenadaValida(coordenada)) {
            return null;
        }

        int x = coordenada.getX();
        int y = coordenada.getY();

        switch (direcao) {
            case Norte:
                y--;
                break;

            case Sul:
                y++;
                break;

            case Leste:
                x++;
                break;

            case Oeste:
                x--;
                break;
        }

        Coordenada vizinha = new Coordenada(x, y);

        if (!this.coordenadaValida(vizinha)) {
            return null;
        }

        return vizinha;
    }

    // Metodos privados (montagem do tabuleiro)
    private void inicializarSetores() {
        for (int x = 0; x < TAMANHO; x++) {
            for (int y = 0; y < TAMANHO; y++) {
                this.setores[x][y] = new Setor(new Coordenada(x, y), TipoSetor.Normal);
            }
        }
    }

    // Portas fixas: todas as ligacoes horizontais e a coluna central
    private void configurarPortas() {
        // Conecta horizontalmente todos os setores de cada linha
        for (int y = 0; y < TAMANHO; y++) {
            for (int x = 0; x < TAMANHO - 1; x++) {
                this.conectarSetores(x, y, Direcao.Leste);
            }
        }

        // Conecta verticalmente os setores da coluna central
        for (int y = 0; y < TAMANHO - 1; y++) {
            this.conectarSetores(CENTRO, y, Direcao.Sul);
        }
    }

    // Abre a porta nos dois setores (o atual e o vizinho na direcao dada)
    private void conectarSetores(int x, int y, Direcao direcao) {
        Coordenada atual = new Coordenada(x, y);
        Coordenada vizinha = this.getCoordenadaVizinha(atual, direcao);

        // Vizinho fora do tabuleiro: fica parede
        if (vizinha == null) {
            return;
        }

        this.getSetor(atual).abrirPorta(direcao);
        this.getSetor(vizinha).abrirPorta(direcao.oposta());
    }

    // Tipos fixos: [1,1] oculto e [5,5] privado (na tela); o resto e normal
    private void configurarTiposSetores() {
        this.setores[0][0].definirTipo(TipoSetor.Oculto);
        this.setores[TAMANHO - 1][TAMANHO - 1].definirTipo(TipoSetor.Privado);
    }

    // Sorteia a posicao do X em qualquer setor, menos o centro
    private void posicionarFonteInfeccao() {
        int x;
        int y;

        do {
            x = Sorteador.sortear(0, TAMANHO - 1);
            y = Sorteador.sortear(0, TAMANHO - 1);
        } while (x == CENTRO && y == CENTRO);

        this.fonteInfeccao = new Coordenada(x, y);
    }

    private boolean coordenadaValida(Coordenada coordenada) {
        if (coordenada == null) {
            return false;
        }

        return coordenada.getX() >= 0 && coordenada.getX() < TAMANHO
                && coordenada.getY() >= 0 && coordenada.getY() < TAMANHO;
    }
}
