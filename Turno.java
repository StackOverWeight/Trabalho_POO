public class Turno {
    //Turno (P1, Setor)

        //Ler entrada (Movimentar, Procurar ou Atacar)

        //Casos de erro

            //Digitação errada

            //Jogador não realiza essa ação

            //Se setor == visitado ou centro: pode movimentar e procurar

            //Se setor == ocupado: pode atacar e procurar

            //Se setor == Privado: não pode procurar

            //...

        //Se usuario quer atacar: 
        
            //Se setor for oculto: Sorteia ataque

                //Lê inimigo escolhido

                    //Caso de erro

                //Atacar(Inimigo escolhido)

        //Se não, se escolheu procurar: Procurar

        //Se não, se escolheu movimentar: 
            
            //lê posição para se movimentar

            //É válido?

                //Movimentar(Próximo Setor)

            /*Opção ============================== */
            //Ou os eventos do setor novo é realizado aqui ou dentro da função movimentar

            //Se setor é novo
    
                //Eventos de novo setor visitado

            /* ==================================== */

    //Turno (P1, P2, Setor)

        //Tudo praticamente igual ao turno do jogador P2 +  |  Q: Repetição de código? 

        //Se não, se escolheu recuperar:

            /* Opção 1 ================================*/ 
            //Verificação de P1 aqui
            //Consequência: Dá para fazer um polimorfismo de sobrecarga em recuperar do P2

                //Verifica se jogador P1 está vivo e está no setor

                    //Escolhe jogador a ser recuperado

                    //P1 escolhido

                        //Recuperar(P1)

                    //P2 escolhido

                        //Recuperar()

                //Se P1 não pode ser recuperado

                    //Recuperar()

            /* Opção 2 ================================*/ 
            //Verificação de P1 encapsulada dentro de recuperar

                //Recupera(P1)

    //Turno (Jogador, Inimigo) //Turno do inimigo

        /* Opção 1 =================================== */

        //Sorteia o ataque

            //Inimigo.Atacar(Jogador)

        /* Opção 2 =================================== */
        //Sorteio dentro de ataque encapsulado em inimigo

        //Inimigo.Atacar(Jogador)

}