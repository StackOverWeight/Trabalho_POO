public class JogadorSuporte extends Jogador {

    private static final int ATAQUE_SUPORTE = 1;
    private static final int DEFESA_SUPORTE = 7;

    public JogadorSuporte() {
        super(ATAQUE_SUPORTE, DEFESA_SUPORTE);
    }

    public void recuperar(Jogador alvo){
        if(alvo.getPosicao().comparaCoordenadas(this.getPosicao()))
            alvo.setDefesa(alvo.getDefesa() + 2);
    }
}