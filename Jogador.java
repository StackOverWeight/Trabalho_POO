public class Jogador extends Entidade {

    private static final int ATAQUE_SIMPLES = 2;
    private static final int DEFESA_SIMPLES = 6;

    private Coordenada posicao;

    public Jogador(){
        this(ATAQUE_SIMPLES, DEFESA_SIMPLES);
    }

    protected Jogador(int ataque, int defesa){
        super(ataque, defesa);
        // Todo jogador começa no centro do tabuleiro
        this.posicao = new Coordenada(Tabuleiro.CENTRO, Tabuleiro.CENTRO);
    }

    public Coordenada getPosicao(){
        return this.posicao;
    }

    public void setPosicao(Coordenada posicao){
        this.posicao = posicao;
    }

    @Override
    public void atacar(Entidade alvo, Setor setor){
        if(setor.getTipo() == TipoSetor.Oculto){
            int num = Sorteador.sorteia(1, 6);

            if(num % 2 != 0)
                return;
        }
        alvo.setDefesa(alvo.getDefesa() - this.getAtaque());
    }

    public void movimentar(Setor setor){
        this.setPosicao(setor.getCoordenada());
    }

    public void procurar(Setor setor){
        if(setor.getTipo() == TipoSetor.Privado)
            return;

        int num = Sorteador.sorteia(1, 6);
        switch(num){
        case 6:
            for(Inimigo inimigo : setor.getInimigos())
                inimigo.setDefesa(inimigo.getDefesa() - 1);
            break;
        case 5:
            this.setDefesa(this.getDefesa() + 2);
            break;
        case 4:
            this.setDefesa(this.getDefesa() + 1);
            break;
        default:
        }
    }
}