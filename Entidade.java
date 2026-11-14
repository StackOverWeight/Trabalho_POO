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
