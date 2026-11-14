public class Inimigo extends Entidade {
    public Inimigo(int ataque, int defesa){
        super(ataque, defesa);
    }

    @Override
    public void atacar(Entidade alvo, Setor setor) {
        int num = Sorteador.sorteia(1, 6);
        if(num % 2 == 0)
            alvo.setDefesa(alvo.getDefesa() - this.getAtaque());
    }
}