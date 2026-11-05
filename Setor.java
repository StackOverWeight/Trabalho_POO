public class Setor {
    public enum StatusSetor {
        NaoVisitado,
        Ocupado,
        Visitado
    }

    public enum Tipo {
        Normal,
        Oculto,
        Privado
    }

    //True = Tem e False = Não tem
    private boolean porta_esquerda;
    private boolean porta_direita;
    private boolean porta_superior;
    private boolean porta_inferior;

    private int status;

    private Coordenada posicao;

    private int tipo;

    //lista de inimigos

    public Setor(/*Definição de Portas*/) {
        //Definir portas

        this.status = this.Tipo.NaoVisitado //Q: Não sei se é assim que acessa

         

    }

    /* Métodos get e set */
}