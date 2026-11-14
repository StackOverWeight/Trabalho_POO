
public class TesteTabuleiro {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro();

        Coordenada centro = new Coordenada((byte) 2, (byte) 2);
        Coordenada canto = new Coordenada((byte) 0, (byte) 0);

        // 1. Criação do tabuleiro
        System.out.println("=== CRIACAO DO TABULEIRO ===");
        System.out.println("Tabuleiro criado com sucesso!");
        System.out.println("Centro visitado: "
                + tabuleiro.setorFoiVisitado(centro));
        System.out.println("Inimigos no centro: "
                + tabuleiro.getSetor(centro).quantidadeInimigos());

        // 2. Portas do centro
        System.out.println("\n=== PORTAS DO CENTRO ===");
        System.out.println("Norte: "
                + tabuleiro.temPorta(centro, Direcao.Norte));
        System.out.println("Sul: "
                + tabuleiro.temPorta(centro, Direcao.Sul));
        System.out.println("Leste: "
                + tabuleiro.temPorta(centro, Direcao.Leste));
        System.out.println("Oeste: "
                + tabuleiro.temPorta(centro, Direcao.Oeste));

        // 3. Limites do tabuleiro
        System.out.println("\n=== LIMITES ===");
        System.out.println("Norte do canto: "
                + tabuleiro.temPorta(canto, Direcao.Norte));
        System.out.println("Oeste do canto: "
                + tabuleiro.temPorta(canto, Direcao.Oeste));
        System.out.println("Coordenada valida (2,2): "
                + tabuleiro.coordenadaValida(centro));
        System.out.println("Coordenada invalida (5,2): "
                + tabuleiro.coordenadaValida(
                        new Coordenada((byte) 5, (byte) 2)));

        // 4. Movimentação
        System.out.println("\n=== MOVIMENTACAO ===");
        System.out.println("Centro -> Norte: "
                + tabuleiro.podeMover(centro, Direcao.Norte));
        System.out.println("Centro -> Sul: "
                + tabuleiro.podeMover(centro, Direcao.Sul));
        System.out.println("Centro -> Leste: "
                + tabuleiro.podeMover(centro, Direcao.Leste));
        System.out.println("Centro -> Oeste: "
                + tabuleiro.podeMover(centro, Direcao.Oeste));
        System.out.println("Canto -> Norte: "
                + tabuleiro.podeMover(canto, Direcao.Norte));

        // 5. Primeira visita e visitas posteriores
        System.out.println("\n=== VISITAS ===");
        Coordenada destino = new Coordenada((byte) 2, (byte) 1);

        tabuleiro.visitarSetor(destino);

        int primeiraVisita =
                tabuleiro.getSetor(destino).quantidadeInimigos();

        tabuleiro.visitarSetor(destino);

        int segundaVisita =
                tabuleiro.getSetor(destino).quantidadeInimigos();

        System.out.println("Setor visitado: "
                + tabuleiro.setorFoiVisitado(destino));
        System.out.println("Inimigos na primeira visita: "
                + primeiraVisita);
        System.out.println("Inimigos na segunda visita: "
                + segundaVisita);
        System.out.println("Quantidade permaneceu igual: "
                + (primeiraVisita == segundaVisita));

        // 6. Fonte de infecção
        System.out.println("\n=== FONTE DE INFECCAO ===");

        int quantidadeFontes = 0;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Coordenada coordenada =
                        new Coordenada((byte) x, (byte) y);

                if (tabuleiro.ehFonteInfeccao(coordenada)) {
                    quantidadeFontes++;
                    System.out.println("Fonte encontrada em: ("
                            + x + ", " + y + ")");
                }
            }
        }

        System.out.println("Quantidade de fontes: "
                + quantidadeFontes);
        System.out.println("Centro e fonte: "
                + tabuleiro.ehFonteInfeccao(centro));

        // 7. Tipos de setor
        System.out.println("\n=== TIPOS DE SETOR ===");

        Coordenada oculto = new Coordenada((byte) 0, (byte) 0);
        Coordenada privado = new Coordenada((byte) 4, (byte) 4);
        Coordenada normal = new Coordenada((byte) 1, (byte) 1);

        System.out.println("Setor (0,0): "
                + tabuleiro.getTipoSetor(oculto));
        System.out.println("Setor (4,4): "
                + tabuleiro.getTipoSetor(privado));
        System.out.println("Setor (1,1): "
                + tabuleiro.getTipoSetor(normal));

        // 8. Atributos dos inimigos
        System.out.println("\n=== ATRIBUTOS DOS INIMIGOS ===");

        boolean atributosValidos = true;

        for (Inimigo inimigo : tabuleiro.getSetor(destino).getInimigos()) {
            int ataque = inimigo.getAtaque();
            int defesa = inimigo.getDefesa();

            System.out.println("Ataque: " + ataque
                    + " | Defesa: " + defesa);

            if (ataque < 1 || ataque > 3
                    || defesa < 1 || defesa > 3
                    || ataque != defesa) {
                atributosValidos = false;
            }
        }

        System.out.println("Todos os atributos validos: "
                + atributosValidos);
    }
}
