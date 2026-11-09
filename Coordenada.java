public class Coordenada {
    private final byte x; // coluna
    private final byte y; // linha

    public Coordenada(byte x, byte y) {
        this.x = x;
        this.y = y;
    }

    public byte getX(){
        return this.x;
    }


    public byte getY(){
        return this.y;
    }

    public boolean comparaCoordenadas(Coordenada c) {
        return this.x == c.getX() && this.y == c.getY();
    }
}