public class JogadorSuporte extends Jogador {
    //construct

        //this.pos = posição do centro -> Q: rep. de código?

        //this.ataque = ATK_P_SUPORTE (1)

        //this.defesa = DEF_P_SUPORTE (7)

    //Procurar(..., P1)

        //Sobrescrição: igual Jogador simples, mas implementação diferentece quem recebe def.  

    //METODO RECUPERAR - OPÇÕES

    //Opção 1: Verificação de mesmo setor aqui + verificação qual jogador
    //recuperar(jogador simples, "setor[não necessário]")

        //Se estão no mesmo setor

            //Escolhe qual jogador deve ser recuperado

                //Recupera jogador escolhido

        //Se não estão

            //Recupera o suporte

    //Opção 2: Verificação de setor fora + verificação qual jogador
    //recuperar (jogador simples)

        //Escolhe qual jogador deve ser recuperado

            //Recupera jogador escolhido


    //Opção 3: Verificação de setor fora daqui + polimorfismo de sobrecarga (verificação de jogador fora)
    //recuperar()

        //recupera suporte

    //recuperar(jogador simples)

        //recupera jogador simples

}