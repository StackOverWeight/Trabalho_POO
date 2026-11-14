// Posicao (x, y) no tabuleiro. Imutavel: nao tem metodos set
public class Coordenada {

    // Atributos
    private final int x; // coluna
    private final int y; // linha

    // Construtor
    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Metodos get
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    // Metodos
    // Devolve true se as duas coordenadas sao a mesma posicao
    public boolean compararCoordenadas(Coordenada outra) {
        return this.x == outra.getX() && this.y == outra.getY();
    }
}
