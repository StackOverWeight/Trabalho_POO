public enum Direcao {
    Norte,
    Sul,
    Leste,
    Oeste;

    public Direcao oposta(){
        switch (this) {
            case Norte:
                return Sul;
            
            case Sul:
                return Norte;
            
            case Leste:
                return Oeste;

            case Oeste:
                return Leste;

            default:
                throw new IllegalStateException("Direção inválida");
        }
    }
}