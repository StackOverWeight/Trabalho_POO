public class Jogador extends Entidade {

    //construct

        //this.ataque = ATK_P_SIMPLES (2)

        //this.defesa = DEF_P_SIMPLES (6)

    //Opção 1:
    //Atacar tem implementação herdada e verificação de setor feito em outro lugar

    //Opção 2:
    //Atacar (setor)

        //Se setor for oculto

            //Sorteia se ataca ou não

                //Se sim: ataca (igual herdado)

        //Caso contrário

            //ataque (igual herdado)


    //movimentar (setor)

        // Troca coordenada do jogador

        // Eventos do setor -> Q: Aqui ou em turno?
 
    // procurar (setor)

        // Toda a lógica de procurar

        // Setor: atingir inimigos

        //Q: Como alcançar os 2 jogadores? 
}