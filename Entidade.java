// Base de tudo que luta no jogo (jogadores e inimigos): ATK, DEF e estado de vida.
// E abstrata: o metodo atacar (da interface Atacante) e implementado pelas filhas
public abstract class Entidade implements Atacante {

    // Atributos
    private int ataque;
    private int defesa;

    // Construtor
    protected Entidade(int ataque, int defesa) {
        this.ataque = ataque;
        this.defesa = defesa;
    }

    // Metodos get e set
    public int getAtaque() {
        return this.ataque;
    }

    public int getDefesa() {
        return this.defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    // Metodos
    // Viva enquanto a DEF for maior que zero
    public boolean estaVivo() {
        return this.defesa > 0;
    }
}
