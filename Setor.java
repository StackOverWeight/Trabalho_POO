import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Um quadrado do tabuleiro: tipo, portas, se ja foi visitado e os inimigos que estao nele
public class Setor {

    // Atributos
    private Coordenada coordenada;

    private TipoSetor tipo;

    private boolean visitado;

    private Map<Direcao, Boolean> portas;

    private List<Inimigo> inimigos;

    // Construtor: comeca sem portas (todas paredes), sem inimigos e nao visitado
    public Setor(Coordenada coordenada, TipoSetor tipo) {
        this.coordenada = coordenada;
        this.tipo = tipo;
        this.visitado = false;

        this.portas = new HashMap<>();
        this.inimigos = new ArrayList<>();

        for (Direcao direcao : Direcao.values()) {
            this.portas.put(direcao, false);
        }
    }

    // Metodos get e set
    public Coordenada getCoordenada() {
        return this.coordenada;
    }

    public TipoSetor getTipo() {
        return this.tipo;
    }

    public void definirTipo(TipoSetor tipo) {
        this.tipo = tipo;
    }

    // Devolve uma copia: quem percorre a lista pode remover inimigos do setor sem erro
    public List<Inimigo> getInimigos() {
        return new ArrayList<>(this.inimigos);
    }

    public boolean foiVisitado() {
        return this.visitado;
    }

    public void marcarComoVisitado() {
        this.visitado = true;
    }

    // Metodos
    public boolean temPorta(Direcao direcao) {
        return this.portas.get(direcao);
    }

    public void abrirPorta(Direcao direcao) {
        this.portas.put(direcao, true);
    }

    public boolean possuiInimigos() {
        return !this.inimigos.isEmpty();
    }

    public void removerInimigo(Inimigo inimigo) {
        this.inimigos.remove(inimigo);
    }

    // Na primeira visita, marca o setor como visitado e gera os inimigos
    public void visitar() {
        if (!this.visitado) {
            this.marcarComoVisitado();
            this.gerarInimigos();
        }
    }

    // Cria de 1 a 3 inimigos, cada um com ATK = DEF entre 1 e 3
    private void gerarInimigos() {
        int quantidade = Sorteador.sortear(1, 3);

        for (int i = 0; i < quantidade; i++) {
            int atributo = Sorteador.sortear(1, 3);

            this.inimigos.add(new Inimigo(atributo, atributo));
        }
    }
}
