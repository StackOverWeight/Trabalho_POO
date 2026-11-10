public class JogadorSuporte extends Jogador {

    //construct
    //this.pos = posição do centro    |    Q: rep. de código?
    //this.ataque = ATK_P_SUPORTE (1)
    //this.defesa = DEF_P_SUPORTE (7)
    public JogadorSuporte() {
        super(1, 7, new Coordenada((byte) 3, (byte) 3));
    }

    public void recuperar(Jogador alvo){
        if(alvo.getPosicao().comparaCoordenadas(this.getPosicao()))
            alvo.setDefesa(alvo.getDefesa() + 2);
    }
    //Procurar(..., P1)

        //Sobrescrição: igual Jogador simples, mas implementação diferentece quem recebe def.    |   Q: esquisito isso

    /* RECUPERAR --------------------------------- *

    //Opção 1 ====================================
    //Verificação de mesmo setor aqui + verificação qual jogador

    //recuperar(P1)

        //Verifica se jogador P1 está vivo e está no mesmo setor

            //Escolhe jogador a ser recuperado

            //P1 escolhido

                //Recuperar o P1

            //P2 escolhido

                //Recuperar o P2

        //Se P1 não pode ser recuperado

            //Recuperar o P2

    //Opção 2 =======================================
    //Verificação de setor  + polimorfismo de sobrecarga (verificação de jogador fora)

    //recuperar()

        //recupera suporte

    //recuperar(jogador simples)

        //recupera jogador simplesJ

    //Procurar(..., P1)

        //Sobrescrição: igual Jogador simples, mas implementação diferentece quem recebe def.    |   Q: esquisito isso

    /* RECUPERAR --------------------------------- */ 

    //Opção 1 ====================================
    //Verificação de mesmo setor aqui + verificação qual jogador

    //recuperar(P1)

        //Verifica se jogador P1 está vivo e está no mesmo setor

            //Escolhe jogador a ser recuperado

            //P1 escolhido

                //Recuperar o P1

            //P2 escolhido

                //Recuperar o P2

        //Se P1 não pode ser recuperado

            //Recuperar o P2

    //Opção 2 =======================================
    //Verificação de setor  + polimorfismo de sobrecarga (verificação de jogador fora)

    //recuperar()

        //recupera suporte

    //recuperar(jogador simples)

        //recupera jogador simples

}