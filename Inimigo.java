import java.util.Random;

public class Inimigo extends Entidade implements Atacante{
    // Bruno: fiz a estrutura da classe pois preciso que seja assim
    public Inimigo(int ataque, int defesa){
        super(ataque, defesa);
    }

    @Override
    public void atacar(Entidade alvo, Setor setor) {
        Random aleat = new Random();
        int num = aleat.nextInt(1, 7);
        if(num % 2 == 0) 
            alvo.setDefesa(alvo.getDefesa() - this.getAtaque());
    }



    /* ATACAR ------------------------- */

    //Opção 1 =========================
    //O sorteio do ataque é encapsulado dentro do atacar do inimigo

    //Atacar (Jogador)

        //Sorteia o ataque

            //Ataque

    //Opção 2 =========================
    //O sorteio do ataque é feito dentro do turno

    //Só herda o ataque



/* 
 * Bruno:
 * DECISÃO DE IMPLEMENTAÇÃO — CLASSE INIMIGO
 *
 * A classe Inimigo deverá seguir a seguinte estrutura:
 *
 * 1. Atributos privados:
 *    - ataque (int): valor entre 1 e 3.
 *    - defesa (int): valor entre 1 e 3.
 *
 * 2. Construtor:
 *    - Inimigo(int ataque, int defesa)
 *    - Recebe os valores de ataque e defesa na criação do objeto.
 *
 * 3. Getters:
 *    - getAtaque(): retorna o valor de ataque.
 *    - getDefesa(): retorna o valor de defesa.
 *
 * 4. Método atacar():
 *    - Retorna um int.
 *    - É responsável por sortear o valor do ataque durante sua execução.
 *
 * 5. Geração:
 *    - O Setor será responsável por gerar de 1 a 3 inimigos
 *      na primeira visita.
 *    - Cada inimigo será criado com ataque e defesa iguais,
 *      utilizando um valor aleatório entre 1 e 3.
 *
 * O integrante responsável pela classe deverá implementar
 * essa estrutura e o comportamento de combate conforme
 * as regras definidas no enunciado.
 */
}