## Grupo

**- Bruno Rogério Coutinho Moretoni**

**- Lunna Damo Perera**

## Inserção

```java
if (raiz == null) raiz = new No();
```

Cria uma raiz vazia toda vez que encontrar um Nó que não existe, para que o caminho continue preenchido

```java
if (index == morse.length()) {
    raiz.valor = valor;
    }
```

Atualiza o valor (Letra associada) do nó quando o caminho completo é percorrido (leitura completa da variável *morse*)

```java
else if (morse.charAt(index) == '.') raiz.esquerda = inserir(raiz.esquerda, valor, morse , index + 1);
else if (morse.charAt(index) == '-') raiz.direita = inserir(raiz.direita, valor, morse, index + 1);
        
return raiz;
```

Lê a posição atual na string de morse e continua com a recursão pela árvore, navegando com as regras usadas ( '.' = esquerda, '-' = direita), associando os nós criados durante ela e avançando o índice

## Busca

### Busca de caractere por código morse

```java
Scanner divisor = new Scanner(morse);
char[] saida = new char[morse.length()];
```

Cria um elemento scanner para dividir a string em espaços (por que as regras deram a entender que eu não podia usar o .split() das strings) e um array de caracteres para armazenar as letras que forem encontradas

```java
while (divisor.hasNext()){
    String s = divisor.next();
    for (int i = 0; i < s.length(); i++) {
        if (atual == null) return "Não encontrado";
        if (s.charAt(i) == '.') atual = atual.esquerda;
        else if (s.charAt(i) == '-') atual = atual.direita; 
    }
```

Passa caractere por caractere de cada uma das strings do array, avançando pela árvore dependendo do caractere ( '.' = esquerda, '-' = direita)

```java
    if (atual != null && atual.valor != 0) saida[index++] = atual.valor; // 0 é o valor vazio
    }
return new String(saida, 0, index);
}
```

Ao chegar no fim da string, adiciona o caractere no array caso ele exista (não tenha o valor númerico de 0) e avança o indice (obs: char com valor 0 é diferente do char '0', é um caractere invisível/quebrado que é usado como valor padrão)

Após o fim do loop, retorna uma string usando o construtor da classe, passando o array de caracteres e o indice, que serve como a quantidade de elementos adicionados ao array, para evitar que sejam lidos os elementos vazios que o Java adiciona ao iniciar array de tamanho fixo.

### Busca de código morse por caractere

```java
if (raiz == null) return "";
else if (raiz.valor == alvo.charAt(0)) {
    return caminho + atual;
}
```

Os dois casos base, caso a rota seguida não seja a certa, apenas retorna nada, caso seja a certa, adiciona o ultimo elemento visitado ao caminho atual (que sempre é vazio, mas eu não queria tratar string e isso foi o primeiro jeito que veio na cabeça)

```java
String nova = (buscar_caractere(alvo, atual, '.', raiz.esquerda));

if (!(nova.length() == 0)) return (caminho != 0) ? caminho + nova : nova;

nova = (buscar_caractere(alvo, atual, '-', raiz.direita));

if (!(nova.length() == 0)) return (caminho != 0) ? caminho + nova : nova;

return nova;
```

Percorre a árvore recursivamente, se em algum momento ele encontra o caminho certo, para de percorrer e começa a preencher a rota tomada (adicionando caminho ao retorno da chamada), construindo a string de retorno, caso contrario ele vai apenas continuar retornando a string vazia do caso base

obs: (nova.length() == 0) pode ser trocado por isEmpty (e a IDE marca usar isso como um "erro"), mas eu fiz assim pelo mesmo motivo do .split()

## Impressão da árvore

Impressão foi inspirada no jeito que o windows mostra os arquivos (comando tree /f no powershell)

```java
if (raiz == null) return;
```

Caso base

```java
String prefixo;
String espacamento;
String formatada;
if (raiz == this.raiz) System.out.println("Raiz");
```

Cria as variáveis que vão ser iniciadas e imprime a raiz (que é sempre vazia)


```java
else {
    if (profundidade == 1) {
        prefixo = "├───";
        espacamento = "%s";
        formatada = espacamento + "%s '%c' -> \"%s\"";
    }
```

Para os primeiros nós após a raiz, usa o simbolo ├─── para alinhar ele com as linhas que vem depois, define a variavel espaçamento como apenas %s para que o mesmo print possa ser reutilizado (explicação melhor depois, quando a formatação completa for explicada)

```java
else {
    prefixo = "└───";
    espacamento = "%-" + (profundidade - 1) * (prefixo.length() + 1) + "s";
    formatada = " │" + espacamento + "%s '%c' -> \"%s\"";
}
```

Define o prefixo como └─── para mostrar e alinhar os nós, calcula o espaçamento com base na profundidade atual (-1 pois a profundidade 1 fica alinhada com a esquerda), e usa a estrutura de formatação
%-Xs para realizar o padding (espacamento) onde X é a quantidade de espaçamento calculada,

Então formata a string, com a diferença com o código da profundidade 1 sendo a presença de uma linha reta na esquerda, que alinha com as da profundidade 1 (além do espaçamento contendo o cálculo)


```java
caminho = caminho + simbolo_atual;

```

Atualiza o caminho usado até o nó atual, mostrando todos os elementos (pontos e traços) que foram visitados pelo caminho, para que o código completo seja mostrado

```java

if (raiz.valor != 0){
    formatada = formatada + " = %c \n";
    System.out.printf(formatada, " ", prefixo, simbolo_atual, caminho, raiz.valor);
}
else {
    formatada = formatada + "\n";
    System.out.printf(formatada," ", prefixo,simbolo_atual, caminho);
}
```

Caso a raiz atual contém o um valor adiciona o comando para mostrar ele na formatação + uma quebra de linha, caso contrario adiciona apenas a quebra de linha, após isso imprime


**Estrutura da formatação (com todos os elementos possiveis):** ***"|%-Xs '%c' -> \"%s\" = %c \n"***

%-Xs - Código de padding, o "-" significa que alinhará o código a esquerda (adiciona o padding na direita), quantidade de espaços adicionados depende do cálculo feito anteriormente (o valor de X neste caso)

'%c' - Ultimo caractere visitado no código morse, mostra o ultimo caminho tomado para chegar até o nó atual (esquerda ou direita do pai)

-> \"%s\" - Mostra o caminho inteiro que foi percorrido, no caso de um nó com valor, mostra o código morse da letra representada, as barras antes das aspas servem para que elas não sejam consideradas como fim da string

= %c - O valor do nó, ou seja, a tradução do código morse do caminho visitado até o nó atual



```java
imprimir(profundidade + 1, raiz.esquerda, caminho, '.');
imprimir(profundidade + 1, raiz.direita, caminho, '-');
```

Continua a recursão aumentando a profundidade e passando a direção tomada (ponto ou traço)

### Input do usuário

## Converter mensagem para morse

```java
char[] saida = new char[mensagem.length() * 6];
```

Cria o array para armazenar a saída (com tamanho 6 vezes maior que a entrada, pois os maiores caracteres em morse tem tamanho 5, e eu sempre adiciono 1 pra prevenir


```java
int index = 0;
for (int i = 0; i < mensagem.length(); i++) {
    if (mensagem.charAt(i) == ' ') {
        saida[index++] = ' ';
    }
```

Começa o loop pela mensagem, caso o caractere seja um espaço, adiciona um espaço na saida

```java
else {
    String morse = this.buscar_caractere(String.valueOf(mensagem.charAt(i)));
    for (int j = 0; j < morse.length(); j++) {
        saida[index++] = morse.charAt(j);
    }
```

Caso não seja um espaço, transforma o caractere em morse e usa um loop para adicionar ele na saida

```java
return new String(saida, 0, index);
```

Após os loops, retorna a string usando o construtor, assim como na busca

## Input de usuário / Método Main

```java
ArvoreMorse arvore = new ArvoreMorse();
arvore.inicializar();
Scanner input = new Scanner(System.in);
criar_alfabeto(arvore);
boolean ativo = true;
```

Inicialização de variáveis e a criação do alfabeto

Opçäo para cada um dos métodos adicionados, e um para converter 

```typescript
while (ativo) {
    System.out.println("--------------------------------");
    System.out.println("""
        Selecione uma opção
        1 - Inserir caractere
        2 - Buscar caractere usando morse
        3 - Buscar código morse usando caractere
        4 - Exibir árvore
        5 - Transformar mensagem em morse
        6 - Sair
        """);
```

Opções disponiveis para o usuário, após isso, entra em um switch que apenas pega as entradas de cada função e executa elas

## Principais referências usadas

https://www.baeldung.com/java-printstream-printf

https://www.baeldung.com/java-scanner

https://stackoverflow.com/questions/15961130/align-printf-output-in-java

Ferramenta usada para montar o readme:
https://ofershap.github.io/readme-builder/

---

<sub>Built with [README Builder](https://ofershap.github.io/readme-builder/) — visual drag-and-drop editor for GitHub READMEs</sub>
