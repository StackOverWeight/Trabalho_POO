// Jogador simples (P1). Tambem e a base do JogadorSuporte (P2)
public class Jogador extends Entidade {

    // Constantes
    private static final int ATAQUE_SIMPLES = 2;
    private static final int DEFESA_SIMPLES = 6;

    // Atributos
    private Coordenada posicao;

    // Construtores
    public Jogador() {
        this(ATAQUE_SIMPLES, DEFESA_SIMPLES);
    }

    // Usado pelo JogadorSuporte, que tem ATK e DEF diferentes
    protected Jogador(int ataque, int defesa) {
        super(ataque, defesa);

        // Todo jogador comeca no centro do tabuleiro
        this.posicao = new Coordenada(Tabuleiro.CENTRO, Tabuleiro.CENTRO);
    }

    // Metodos get e set
    public Coordenada getPosicao() {
        return this.posicao;
    }

    private void setPosicao(Coordenada posicao) {
        this.posicao = posicao;
    }

    // Metodos
    // Tira do alvo o valor do ATK. No setor oculto, so acerta se o dado der par
    @Override
    public void atacar(Entidade alvo, Setor setor) {
        if (setor.getTipo() == TipoSetor.Oculto) {
            int numero = Sorteador.sortear(1, 6);

            if (numero % 2 != 0) {
                return;
            }
        }

        alvo.setDefesa(alvo.getDefesa() - this.getAtaque());
    }

    // Vai para o setor recebido
    public void movimentar(Setor setor) {
        this.setPosicao(setor.getCoordenada());
    }

    // Sorteia 1 a 6: 1-3 nada, 4 = +1 DEF, 5 = +2 DEF, 6 = todos os inimigos do setor perdem 1 DEF.
    // No setor privado nao e possivel procurar.
    // Devolve o numero sorteado (0 se nao procurou), para o Turno saber o que foi encontrado
    public int procurar(Setor setor) {
        if (setor.getTipo() == TipoSetor.Privado) {
            return 0;
        }

        int numero = Sorteador.sortear(1, 6);

        switch (numero) {
            case 6:
                for (Inimigo inimigo : setor.getInimigos()) {
                    inimigo.setDefesa(inimigo.getDefesa() - 1);
                }
                break;

            case 5:
                this.setDefesa(this.getDefesa() + 2);
                break;

            case 4:
                this.setDefesa(this.getDefesa() + 1);
                break;
        }

        return numero;
    }
}
