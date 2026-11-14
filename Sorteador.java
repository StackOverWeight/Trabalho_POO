import java.util.Random;

public class Sorteador{
    private static final Random aleat = new Random();

    // Sorteia um numero inteiro entre min e max (incluindo os dois)
    public static int sorteia(int min, int max){
        return aleat.nextInt(max - min + 1) + min;
    }
}