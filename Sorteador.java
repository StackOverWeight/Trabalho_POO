import java.util.Random;

// Centraliza todos os sorteios do jogo
public class Sorteador {

    // Constantes
    private static final Random ALEATORIO = new Random();

    // Metodos
    // Sorteia um numero inteiro entre min e max (incluindo os dois)
    public static int sortear(int min, int max) {
        return ALEATORIO.nextInt(max - min + 1) + min;
    }
}
