public class Jogador extends Entidade { //Q: Ou implements?

    //construct

        //this.pos = posição do centro

        //this.ataque = ATK_P_SIMPLES (2)

        //this.defesa = DEF_P_SIMPLES (6)

    /* Opção 1: Verificação de setor dentro de Jogador ======================== */
    //Aqui, classe jogador tem relação com setor

    //Atacar (setor)

        //Se setor for oculto

            //Sorteia se ataca ou não

                //Se sim: ataca (igual herdado)

        //Caso contrário

            //ataque (igual herdado)

    //Opção 1: Eventos de setor novo verificado e chamado aqui
    //Movimentar (setor)

        // Troca coordenada do jogador: this.setCoordenada(setor.getCoordenada())

        // Se setor é novo: eventos de setor novo

    //Opção 2: Eventos de setor novo verificado e chamado fora daqui
    //Movimentar (setor)

        // Troca coordenada do jogador: this.setCoordenada(setor.getCoordenada())



    //!! Verificação de procurar em turno (se for setor privado não pode e não entra aqui)

    // procurar (setor, P2)

        // Toda a lógica de procurar

            //Setor: para atingir inimigos

            //Alcança o outro jogador pelo argumento -> Q: Parece estranho isso


    /* Opção 2: Verificação de setor fora de Jogador ======================= */
    //Aqui, classe jogador independente
    
    //Atacar() => igual entidade

    //Procurar (Lista de inimigos, P2)

        // Mesma coisa, mas atingindo inimigos através da lista

    //Movimentar(int x, int y)
    //Eventos de novo evento visitado fora de jogador

        //this.SetCoordenada(x, y)





}