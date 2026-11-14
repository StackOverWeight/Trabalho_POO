// Jogador de suporte (P2): ATK menor, DEF maior e a habilidade de recuperar defesa
public class JogadorSuporte extends Jogador {

    // Constantes que representam os atributos
    private static final int ATAQUE_SUPORTE = 1;
    private static final int DEFESA_SUPORTE = 7;

    // DEF ganha ao usar recuperar
    public static final int BONUS_RECUPERACAO = 2;

    // Construtor
    public JogadorSuporte() {
        super(ATAQUE_SUPORTE, DEFESA_SUPORTE);
    }

    // Metodos
    // Da +2 DEF ao alvo (ele mesmo ou o P1), so se estiverem no mesmo setor
    public void recuperar(Jogador alvo) {
        if (alvo.getPosicao().compararCoordenadas(this.getPosicao())) {
            alvo.setDefesa(alvo.getDefesa() + BONUS_RECUPERACAO);
        }
    }
}
