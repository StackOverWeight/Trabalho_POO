// Direcoes possiveis de movimento e de portas
public enum Direcao {
    Norte,
    Sul,
    Leste,
    Oeste;

    // Direcao contraria (usada para abrir a porta dos dois lados)
    public Direcao oposta() {
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
                throw new IllegalStateException("Direcao invalida");
        }
    }
}
