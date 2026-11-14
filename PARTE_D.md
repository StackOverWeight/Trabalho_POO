# Parte D: Graficos e Turno

Arquivos: `Graficos.java`, `Turno.java` e `Cores.java` (copiada da seção 6.2 do PDF).

- **Graficos** mostra o estado do jogo na tela. Ele **só lê** os dados e nunca altera nada.
- **Turno** conversa com o usuário: lê a ação, valida e chama os métodos das partes B e C.

---

## 1. Como as partes se conectam

```
Principal (A)                      laço de até 25 ciclos
   │
   ├─▶ Turno.turnoJogador(p1)      ┐
   ├─▶ Turno.turnoJogador(p2)      │ parte D
   ├─▶ Turno.turnoInimigo(...)     ┘  (a Principal faz o laço dos inimigos)
   │
   │        Turno chama ──▶ Graficos.imprimirJogo()      (desenha a tela)
   │        Turno chama ──▶ Jogador / JogadorSuporte     (B: atacar, procurar, movimentar, recuperar)
   │        Turno chama ──▶ Inimigo.atacar               (B)
   │        Turno chama ──▶ Tabuleiro / Setor            (C: podeMover, visitarSetor, removerInimigo...)
   │
   └─▶ turno.getVencedor() / estaVivo() / ciclo > 25 → fim de jogo
       turno.encerrar()  → fecha o Scanner
```

O objeto `Graficos` é criado **antes** do `Turno` e entregue a ele no construtor:

```java
Graficos graficos = new Graficos(tabuleiro, p1, p2);
Turno turno = new Turno(tabuleiro, p1, p2, graficos);
```

Os dois guardam **referências** aos mesmos objetos `tabuleiro`, `p1` e `p2`. Quando o Turno muda a DEF de alguém, o Graficos já mostra o valor novo na próxima impressão, sem precisar avisá-lo.

---

## 2. Graficos

### Coordenadas
O modelo (Tabuleiro, Coordenada) conta de **0 a 4**, e o centro é `(2,2)`. A tela mostra de **1 a 5**, e o centro é `[3,3]`, como no PDF. **Só o Graficos faz o `+1`**, e só na hora de mostrar. O painel escreve `Setor [linha, coluna]`, ou seja, `[y+1, x+1]`.

### Como a tela é montada
O terminal imprime linha por linha. Por isso, cada linha do tabuleiro termina imprimindo a linha correspondente do painel lateral:

```
imprimirJogo()
 ├─ limparTela()
 ├─ letras vermelhas + fundo preto (Cores)
 ├─ título, números das colunas, borda de cima
 ├─ para cada linha y de 0 a 4:
 │    imprimeColunas(y)  → casas + portas Leste  + linha 2y   do painel
 │    imprimeLinha(y)    → borda + portas Sul    + linha 2y+1 do painel
 └─ volta às cores normais (ANSI_RESET)
```

O tabuleiro tem sempre a mesma largura, então o painel fica alinhado sem nenhum cálculo de espaço.

### Regras de desenho (tiradas do PDF)

| Método | Regra |
|---|---|
| `conteudoSetor(x, y)` | ordem de prioridade: `C` no centro, `X` na fonte, `P` com os dois jogadores, `P1`, `P2`, vazio. **Jogador morto não aparece.** |
| `portaVisivel(x, y, dir)` | a porta aparece se existe **e** se um dos dois setores ligados por ela já foi visitado (Figura 2) |
| `imprimePaineis(linha)` | os dois jogadores vivos no mesmo setor geram um painel; em setores diferentes, um painel para cada; jogador morto não tem painel |
| `linhaPainel(...)` | cada uma das 9 linhas do painel: coordenada, porta Norte, inimigos, portas Oeste e Leste, nomes, ATK/DEF, porta Sul |
| `completarEspacos` | completa o texto com espaços para manter a largura do painel |

### Painel lateral (alinhado com o tabuleiro)
```
linha 0   Setor [3,4]
linha 1
linha 2   |------*------|   ← porta Norte
linha 3   | 1/1 3/3 2/2 |   ← inimigos (ATK/DEF)
linha 4   |             |
linha 5   *             *   ← portas Oeste / Leste
linha 6   | P1     P2   |
linha 7   | 2/6    1/7  |
linha 8   |------*------|   ← porta Sul
```

---

## 3. Turno

### Turno de um jogador: `turnoJogador(Jogador jogador)`
Um único método serve para P1 e P2. A opção "Recuperar" só aparece se `jogador == p2`.

```
enquanto (ações < 2 e jogador vivo e ninguém venceu):
    desenha a tela + mostra a mensagem da ação anterior
    mostra o menu e lê a opção
    executarAcao(...)  → true: conta a ação | false: erro, NÃO conta
```

### Ações e validações
Cada ação valida antes de agir. Se a ação é inválida, ela escreve o motivo em `mensagem` e devolve `false`.

| Ação | Quando é inválida | O que chama |
|---|---|---|
| Atacar | sem inimigos no setor; número de inimigo inválido | `jogador.atacar(alvo, setor)` (B), depois `removerMortos(setor)` |
| Procurar | setor Privado | `jogador.procurar(setor)` (B), depois `removerMortos(setor)` |
| Movimentar | ainda há inimigos no setor; direção inválida; sem porta | `tabuleiro.podeMover` (C), `jogador.movimentar` (B), `ehFonteInfeccao` (C) para a vitória, ou `visitarSetor` (C) para gerar inimigos |
| Recuperar (só P2) | P1 morto ou em outro setor | `p2.recuperar(alvo)` (B) |
| Qualquer outra entrada | sempre | mensagem "Opcao invalida" |

**Decisões do grupo:**
- Movimentar gasta 1 das 2 ações.
- Recuperar vale 1 ação.
- Quem remove os inimigos mortos é o Turno.
- O laço dos inimigos fica na Principal.

### Turno do inimigo: `turnoInimigo(jogador, inimigo, setor)`
Um inimigo ataca um jogador. O sorteio (par acerta, ímpar erra) já fica dentro de `Inimigo.atacar` (B). O Turno só compara a DEF antes e depois para escrever "acertou" ou "errou".

A Principal chama esse método **para cada inimigo do setor de P1** e depois **para cada inimigo do setor de P2**. Assim, se os dois estiverem no mesmo setor, cada inimigo ataca cada jogador exatamente uma vez, como o PDF pede.

### Outros detalhes
- **`mensagem`**: como `imprimirJogo()` limpa a tela, o resultado da ação fica guardado e é mostrado depois de redesenhar.
- **`removerMortos`**: percorre `setor.getInimigos()` e remove os mortos com `setor.removerInimigo()`. Isso funciona porque `getInimigos()` devolve uma **cópia** da lista (encapsulamento do Setor).
- **Leitura**: há um único `Scanner`, criado no construtor. As opções são lidas como texto e comparadas no `switch` ou com `equals`, então digitar letras não quebra o programa.
- **`encerrar()`**: fecha o `Scanner`. A Principal chama uma vez, no fim do jogo.

---

## 4. Conceitos de POO na parte D (para o relatório e a entrevista)

| Conceito | Onde aparece |
|---|---|
| Classes, atributos e construtores | `Graficos(tabuleiro, p1, p2)`, `Turno(tabuleiro, p1, p2, graficos)` |
| Encapsulamento | atributos `private`; o Graficos só usa getters; o Turno usa `removerInimigo` em vez de mexer na lista |
| Polimorfismo de **inclusão (subtipo)** | `turnoJogador(Jogador)` recebe P1 (`Jogador`) ou P2 (`JogadorSuporte`); `jogador.atacar(...)` executa o método do tipo real do objeto |
| Interface | `atacar` vem de `Atacante`, implementada por `Jogador` e `Inimigo` |
| Coleção | percorre `List<Inimigo>` do Setor (for-each) |

---

## 5. Como executar

```bash
javac *.java
java Principal
```

Comandos durante o jogo: `1` Atacar, `2` Procurar, `3` Movimentar (depois `N`, `S`, `L` ou `O`), `4` Recuperar (só P2).
