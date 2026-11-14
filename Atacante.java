// Tudo que pode atacar uma entidade (Jogador e Inimigo, atraves de Entidade)
public interface Atacante {

    // Ataca o alvo; o setor e usado para regras especiais (ex.: setor oculto)
    public void atacar(Entidade alvo, Setor setor);
}
