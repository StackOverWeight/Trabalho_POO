import java.util.Random;

public class Sorteador{
    public int sorteia(int min, int max){
        Random aleat = new Random();
        return aleat.nextInt(min, max+1);
    }
}