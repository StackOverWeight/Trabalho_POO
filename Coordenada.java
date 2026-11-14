public class Coordenada {
    private final int x; // coluna
    private final int y; // linha

    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }


    public int getY(){
        return this.y;
    }

    public boolean comparaCoordenadas(Coordenada c) {
        return this.x == c.getX() && this.y == c.getY();
    }
}