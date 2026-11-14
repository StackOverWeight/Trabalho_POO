public class Tabuleiro {
    public static final int TAMANHO = 5;
    public static final int CENTRO = 2;

    private Setor[][] setores;
    private Coordenada fonteInfeccao;

    public Tabuleiro() {
        this.setores = new Setor[TAMANHO][TAMANHO]; 
        this.inicializarSetores();
        this.configurarPortas();
        this.configurarTiposSetores();
        this.posicionarFonteInfeccao();
        // Centro começa visitado (portas visíveis), mas sem gerar inimigos
        this.getSetor(new Coordenada(CENTRO, CENTRO)).marcarComoVisitado();
    }

    // Métodos get e set
    public Setor getSetor(Coordenada coordenada) {
        if (!this.coordenadaValida(coordenada)) {
            throw new IllegalArgumentException("Coordenada fora dos limites do tabuleiro.");
        }

        return this.setores[coordenada.getX()][coordenada.getY()];
    }

    // Métodos
    private void inicializarSetores(){
        for (int x = 0; x < TAMANHO; x++){
            for (int y = 0; y < TAMANHO; y++){
                Coordenada coordenada = new Coordenada(x, y);

                setores[x][y] = new Setor(coordenada, TipoSetor.Normal);
            }
        }
    }

    private void conectarSetores(int x, int y, Direcao direcao){
        Coordenada atual = new Coordenada(x, y);
        Coordenada vizinha = this.getCoordenadaVizinha(atual, direcao);

        // Vizinho fora do tabuleiro: fica parede
        if (vizinha == null){
            return;
        }

        this.getSetor(atual).abrirPorta(direcao);
        this.getSetor(vizinha).abrirPorta(direcao.oposta());
    }

    private void configurarPortas() {
        // Conecta horizontalmente todos os setores de cada linha.
        for (int y = 0; y < TAMANHO; y++) {
            for (int x = 0; x < TAMANHO - 1; x++) {
                this.conectarSetores(x, y, Direcao.Leste);
            }
        }

        // Conecta verticalmente os setores da coluna central.
        for (int y = 0; y < TAMANHO - 1; y++) {
            this.conectarSetores(CENTRO, y, Direcao.Sul);
        }
    }

    private void posicionarFonteInfeccao(){
        int x, y;

        do {
            x = Sorteador.sorteia(0, TAMANHO - 1);
            y = Sorteador.sorteia(0, TAMANHO - 1);
        } while (x == CENTRO && y == CENTRO);

        this.fonteInfeccao = new Coordenada(x, y);
    }

    private void configurarTiposSetores() {
        this.definirTipoSetor(0, 0, TipoSetor.Oculto);
        this.definirTipoSetor(TAMANHO - 1, TAMANHO - 1, TipoSetor.Privado);
    }

    private void definirTipoSetor(int x, int y, TipoSetor tipo) {
        this.setores[x][y].definirTipo(tipo);
    }

    public boolean ehFonteInfeccao(Coordenada coordenada) {
        if (!this.coordenadaValida(coordenada)) {
            return false;
        }

        return this.fonteInfeccao.comparaCoordenadas(coordenada);
    }

    public TipoSetor getTipoSetor(Coordenada coordenada) {
        return this.getSetor(coordenada).getTipo();
    }

    public boolean coordenadaValida(Coordenada coordenada) {
        if (coordenada == null) {
            return false;
        }

        return coordenada.getX() >= 0 && coordenada.getX() < TAMANHO
                && coordenada.getY() >= 0 && coordenada.getY() < TAMANHO;
    }

    public boolean temPorta(Coordenada coordenada, Direcao direcao) {
        return this.getSetor(coordenada).temPorta(direcao);
    }

    public void visitarSetor(Coordenada coordenada) {
        this.getSetor(coordenada).visitar();
    }

    public boolean setorFoiVisitado(Coordenada coordenada) {
        return this.getSetor(coordenada).isVisitado();
    }

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
}