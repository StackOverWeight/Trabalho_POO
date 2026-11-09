import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Setor {
     
    // Atributos
    private Coordenada coordenada;

    private TipoSetor tipo;

    private boolean visitado;

    private Map<Direcao, Boolean> portas;

    private List<Inimigo> inimigos;

    // Construtor
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


    // Métodos get e set
    public Coordenada getCoordenada(){
        return this.coordenada;
    }

    public TipoSetor getTipo(){
        return this.tipo;
    }

    public List<Inimigo> getInimigos() {
        return new ArrayList<>(this.inimigos);
    }

    public boolean isVisitado(){
        return this.visitado;
    }

    public void marcarComoVisitado(){
        this.visitado = true;
    }

    public boolean temPorta(Direcao direcao){
        return this.portas.get(direcao);
    }

    public void abrirPorta(Direcao direcao){
        this.portas.put(direcao, true);
    }

    public void definirTipo(TipoSetor tipo) {
        this.tipo = tipo;
    }
    

    // Métodos
    public void definirPorta(Direcao direcao, boolean aberta){
        this.portas.put(direcao, aberta);
    }

    public void adicionarInimigo(Inimigo inimigo) {
        this.inimigos.add(inimigo);
    }

    public boolean possuiInimigos() {
        return !this.inimigos.isEmpty();
    }

    public int quantidadeInimigos() {
        return this.inimigos.size();
    }

    public void removerInimigo(Inimigo inimigo) {
        this.inimigos.remove(inimigo);
    }

    private void gerarInimigos() {
        Random random = new Random();

        int quantidade = random.nextInt(3) + 1;

        for (int i = 0; i < quantidade; i++) {
            int atributo = random.nextInt(3) + 1;

            Inimigo inimigo = new Inimigo(atributo, atributo);

            this.inimigos.add(inimigo);
        }
    }

    public void visitar() {
        if (!this.visitado) {
            this.marcarComoVisitado();
            this.gerarInimigos();
        }
    }

}