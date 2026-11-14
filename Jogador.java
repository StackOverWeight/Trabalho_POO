public class Jogador extends Entidade implements Atacante{ //Q: Ou implements?

    private Coordenada posicao;
    //construct

        //this.pos = posição do centro

        //this.ataque = ATK_P_SIMPLES (2)

        //this.defesa = DEF_P_SIMPLES (6)

    public Jogador(){
        super(2, 6);
        this.posicao = new Coordenada((byte) 2, (byte) 2);
    }

    protected Jogador(int ataque, int defesa, Coordenada posicao){
        super(ataque, defesa);
        this.posicao = posicao;
    }

    public Coordenada getPosicao(){
        return this.posicao;
    }

    public void setPosicao(Coordenada posicao){
        this.posicao = posicao;
    }
    /* Opção 1 ============================= */
    //Verificação de setor oculto dentro de atacar


    //Atacar (inimigo, setor)

        //Se setor for oculto

            //Sorteia se ataca ou não

                //Se sim: ataca (igual herdado)

        //Caso contrário

            //ataque (igual herdado)

    @Override
    public void atacar(Entidade alvo, Setor setor){
        if(setor.getTipo() == TipoSetor.Oculto){
            Sorteador sorteador = new Sorteador();
            int num = sorteador.sorteia(1, 6);

            if(num % 2 != 0)
                return;
        }
        alvo.setDefesa(alvo.getDefesa() - this.getAtaque());
    }
    
    /* Opção 2 ============================= */
    //Só herda atacar

    /* ------------------------------------- */

    /* MOVIMENTAR -------------------------- */

    /* Opção 1 ============================= */ 
    //Eventos de setor novo verificado e chamado aqui

    //Movimentar (setor)

        // Troca coordenada do jogador: this.setCoordenada(setor.getCoordenada())

        // Se setor é novo: eventos de setor novo

    /* Opção 2 ============================ */ 
    //Eventos de setor novo verificado e chamado fora em turno
    
    //Movimentar (setor)

        // Troca coordenada do jogador: this.setCoordenada(setor.getCoordenada())

    /* Opção 3 ============================ */ 
    //Se relação com setor não for estritamente necessária 

    //Movimentar(Coordenada)

        //this.SetCoordenada(Coordenada)

    /* ------------------------------------ */

    public void movimentar(Setor setor){
        this.setPosicao(setor.getCoordenada());
    }

    /* PROCURAR --------------------------- */
    //Como que esse método alcançará os inimigos do setor?

    /* Opção 1 ============================ */
    //Alcança inimigos através do setor

    // procurar (P2, Setor)

        // Toda a lógica de procurar - bem explicada no docs do trabalho

        // Alcança o outro jogador pelo argumento   |   Q: Parece estranho isso

    /* Opção 2 ============================ */
    //Alcança inimigos através da lista de inimigos

    //Procurar (Lista de inimigos, P2)

        // Mesma coisa, mas atingindo inimigos através da lista

    /* ------------------------------------ */
    public void procurar(Setor setor){
        if(setor.getTipo() == TipoSetor.Privado)
            return;

        Sorteador sorteador = new Sorteador();
        int num = sorteador.sorteia(1, 6);
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