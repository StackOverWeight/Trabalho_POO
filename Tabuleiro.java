import java.util.Random;

public class Tabuleiro {
    private Setor[][] setores;
    private Coordenada fonteInfeccao;

    public Tabuleiro() {
        this.setores = new Setor[5][5]; 
        this.inicializarSetores();
        this.configurarPortas();
        this.configurarTiposSetores();
        this.posicionarFonteInfeccao();
        // Centro começa visitado (portas visíveis), mas sem gerar inimigos
        this.getSetor(new Coordenada((byte) 2, (byte) 2)).marcarComoVisitado();
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
        for (int x = 0; x < 5; x++){
            for (int y = 0; y < 5; y++){
                Coordenada coordenada = new Coordenada((byte) x, (byte) y);

                setores[x][y] = new Setor(coordenada, TipoSetor.Normal);
            }
        }
    }

    private void conectarSetores(int x, int y, Direcao direcao){
        int novoX = x;
        int novoY = y;

        switch(direcao){
            case Norte:
                novoY--;
                break;
            
            case Sul:
                novoY++;
                break;
            
            case Leste:
                novoX++;
                break;

            case Oeste:
                novoX--;
                break;
        }

        // Verifica se vizinho está dentro do tabuleiro
        if (novoX < 0 || novoX >=5 || novoY < 0 || novoY >= 5){
            return;
        }

        Setor atual = setores[x][y];
        Setor vizinho = setores[novoX][novoY];

        atual.abrirPorta(direcao);
        vizinho.abrirPorta(direcao.oposta());
    }

    private void configurarPortas() {
        // Conecta horizontalmente todos os setores de cada linha.
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 4; x++) {
                this.conectarSetores(x, y, Direcao.Leste);
            }
        }

        // Conecta verticalmente os setores da coluna central.
        for (int y = 0; y < 4; y++) {
            this.conectarSetores(2, y, Direcao.Sul);
        }
    }

    private void posicionarFonteInfeccao(){
        Random random = new Random();
        
        int x, y;

        do {
            x = random.nextInt(5);
            y = random.nextInt(5);
        } while (x == 2 && y ==2);

        this.fonteInfeccao = new Coordenada((byte) x, (byte) y);
    }

    private void configurarTiposSetores() {
        this.definirTipoSetor(0, 0, TipoSetor.Oculto);
        this.definirTipoSetor(4, 4, TipoSetor.Privado);
    }

    private void definirTipoSetor(int x, int y, TipoSetor tipo) {
        this.setores[x][y].definirTipo(tipo);
    }

    public boolean ehFonteInfeccao(Coordenada coordenada) {
        if (!this.coordenadaValida(coordenada)) {
            return false;
        }

        return this.fonteInfeccao.getX() == coordenada.getX() && this.fonteInfeccao.getY() == coordenada.getY();
    }

    public TipoSetor getTipoSetor(Coordenada coordenada) {
        return this.getSetor(coordenada).getTipo();
    }

    public boolean coordenadaValida(Coordenada coordenada) {
        if (coordenada == null) {
            return false;
        }

        return coordenada.getX() >= 0 && coordenada.getX() < 5
                && coordenada.getY() >= 0 && coordenada.getY() < 5;
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

        Coordenada vizinha = new Coordenada((byte) x, (byte) y);

        if (!this.coordenadaValida(vizinha)) {
            return null;
        }

        return vizinha;
    }
}