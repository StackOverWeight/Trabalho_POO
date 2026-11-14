public abstract class Entidade implements Atacante {

    private int ataque;
    private int defesa;

    public Entidade(int ataque, int defesa){
        this.ataque = ataque;
        this.defesa = defesa;
    }

    public boolean estaVivo(){
        if(this.defesa > 0)
            return true;

        return false;
    }

    public void setDefesa(int defesa){
        this.defesa = defesa;
    }

    public int getAtaque(){
        return this.ataque;
    }

    public int getDefesa(){
        return this.defesa;
    }
}