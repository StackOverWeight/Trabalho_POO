//T: Rever nome

public class Graficos {

    //Imprimir tabuleiro

        //Limpa a tela

        //Imprimir título - antivírus por um dia

        //Impressão inicial do topo do tabuleiro (números + borda superior)

        //Impressão da linha 1 tabuleiro

            //Imprime "1   |"

            //Impressão de colunas

                //Para cada setor da linha 1 do tabuleiro

                    //Imprime conteúdo setor
                    
                        //Se for fonte de infecção imprime " X ""

                        //Se for centro imprime " C "

                        //Senão, se tiver os dois jogadores imprime " P "

                        //Senão, se tiver só jogador 1 imprime " P1 "

                        //Senão, se tiver só jogador 2 imprime " P2 "

                        //Caso contrário: imprime "   "

                    //Imprime portas / paredes

                        //Se porta aparece: imprime " *"

                        //Se não: imprime  " |"

            //** Imprime posição dos setor(es)

                //Se setor de P1 == setor de P2

                    //Imprime "      setor[x,y]"

                //Caso contrário

                    //Imprime " Setor [x,y]     Setor[x,y]"

            //Impressão de linha

                //Imprime "|"

                //Para cada setor da linha 1 do tabuleiro

                    //Imprime "-"

                    //Se porta aparece

                        //Imprime "*"

                    //Se não

                        //Imprime "-"

                    //Imprime "-|"

        //Impressão da linha 2 tabuleiro

        /* Reutilizações: Impressão inicial, impressão de colunas e linha */

        /* O que vai mudar: controle do que é mostrado ao lado do tabuleiro (inimigos, jogadores, portas do setor, ...) */ 
                    

    public void LimparTela() {
        // Limpa o ecrã e move o cursor para o canto superior esquerdo
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}