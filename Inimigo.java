// Inimigo de um setor. Ataca so quando o dado da um numero par
public class Inimigo extends Entidade {

    // Construtor
    public Inimigo(int ataque, int defesa) {
        super(ataque, defesa);
    }

    // Metodos
    // Sorteia 1 a 6: se for par, tira do alvo o valor do ATK do inimigo
    @Override
    public void atacar(Entidade alvo, Setor setor) {
        int numero = Sorteador.sortear(1, 6);

        if (numero % 2 == 0) {
            alvo.setDefesa(alvo.getDefesa() - this.getAtaque());
        }
    }
}
