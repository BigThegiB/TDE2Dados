import java.util.Scanner;
public class ArvoreMorse {
    class No {
        char valor;
        No esquerda;
        No direita;

        public No(char valor) {
            this.valor = valor;
            esquerda = null;
            direita = null;
        }

        public No() {
            esquerda = null;
            direita = null;
        }

    }

    No raiz;
    public ArvoreMorse(){
        raiz = null;
    }
    public void inicializar() {
        raiz = new No();
    }



    private No inserir(No raiz, char valor, String morse, int index) {
        if (raiz == null) raiz = new No();
        if (index == morse.length()) {
            raiz.valor = valor;
        }
        else if (morse.charAt(index) == '.') raiz.esquerda = inserir(raiz.esquerda, valor, morse , index + 1);
        else if (morse.charAt(index) == '-') raiz.direita = inserir(raiz.direita, valor, morse, index + 1);
        
        return raiz;

    }

    public void inserir(String morse, char valor) {inserir(raiz, valor, morse, 0);}
    public void inserir(String morse, String valor) {inserir(raiz, valor.charAt(0), morse, 0);}



    String buscar(String morse){
        Scanner divisor = new Scanner(morse);
        char[] saida = new char[morse.length()];
        int index = 0;
        while (divisor.hasNext()){
            String s = divisor.next();
            No atual = raiz;
            for (int i = 0; i < s.length(); i++) {
                if (atual == null) return "Não encontrado";
                if (s.charAt(i) == '.') atual = atual.esquerda;
                else if (s.charAt(i) == '-') atual = atual.direita; 
            }
            if (atual != null && atual.valor != 0) saida[index++] = atual.valor; // 0 é o valor vazio
            }
        divisor.close();
        return new String(saida, 0, index);
        }


    private String buscar_caractere(String alvo, String atual, char caminho, No raiz){
        if (raiz == null) return "";
        else if (raiz.valor == alvo.charAt(0)) {
            return caminho + atual;
        }

        String nova = (buscar_caractere(alvo, atual, '.', raiz.esquerda));

        if (!(nova.length() == 0)) return (caminho != 0) ? caminho + nova : nova;

        nova = (buscar_caractere(alvo, atual, '-', raiz.direita));

        if (!(nova.length() == 0)) return (caminho != 0) ? caminho + nova : nova;

        return nova;
    }
    public String buscar_caractere(String alvo) {
        return buscar_caractere(alvo, "",  (char) 0, this.raiz);
    }

    private void imprimir(int profundidade, No raiz, String caminho, char simbolo_atual) {
        if (raiz == null) return;
        String prefixo;
        String espacamento;
        String formatada;
        if (raiz == this.raiz) System.out.println("Raiz");
        else {
            if (profundidade == 1) {
                prefixo = "├───";
                espacamento = "%s";
                formatada = espacamento + "%s '%c' -> \"%s\"";
            }
            else {
                prefixo = "└───";
                espacamento = "%-" + (profundidade - 1) * (prefixo.length() + 1) + "s";
                formatada = " │" + espacamento + "%s '%c' -> \"%s\"";


            }
            caminho = caminho + simbolo_atual;


            if (raiz.valor != 0){
                formatada = formatada + " = %c \n";
                System.out.printf(formatada, " ", prefixo, simbolo_atual, caminho, raiz.valor);
            }
            else {
                formatada = formatada + "\n";
                System.out.printf(formatada," ", prefixo,simbolo_atual, caminho);
            }
        }

        imprimir(profundidade + 1, raiz.esquerda, caminho, '.');
        imprimir(profundidade + 1, raiz.direita, caminho, '-');
    }

    public void imprimir() {
        imprimir(0, this.raiz, "", (char) 0);
    }
    public String converter_mensagem(String mensagem){
        char[] saida = new char[mensagem.length() * 6];
        int index = 0;
        for (int i = 0; i < mensagem.length(); i++) {
            if (mensagem.charAt(i) == ' ') {
                saida[index++] = ' ';
            }
            else {
                String morse = this.buscar_caractere(String.valueOf(mensagem.charAt(i)));
                for (int j = 0; j < morse.length(); j++) {
                    saida[index++] = morse.charAt(j);
                }

            }
        }
        return new String(saida, 0, index);
    }


}

void main() {
    ArvoreMorse arvore = new ArvoreMorse();
    arvore.inicializar();
    Scanner input = new Scanner(System.in);
    criar_alfabeto(arvore);
    boolean ativo = true;

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
        System.out.println("--------------------------------");
        try {
            int escolha = input.nextInt();
            input.nextLine();
            switch (escolha) {
                case 1 -> {
                    System.out.println("Insira o caractere que deseja inserir");
                    String caractere = input.nextLine();
                    System.out.println("Insira o código morse relacionado ao caractere");
                    String morse = input.nextLine();
                    arvore.inserir(morse, caractere);
                    System.out.println("Caractere inserido com sucesso");
                }
                case 2 -> {
                    System.out.println("Insira o código morse que deseja buscar");
                    String morse = input.nextLine();
                    System.out.println("O caractere relacionado ao código morse é: " + arvore.buscar(morse));
                }
                case 3 -> {
                    System.out.println("Insira o caractere que deseja buscar");
                    String caractere = input.nextLine();
                    System.out.println("O código morse relacionado ao caractere é: " + arvore.buscar_caractere(caractere));
                }
                case 4 -> {
                    System.out.println("Exibindo árvore");
                    arvore.imprimir();
                }
                case 5 -> { 
                    System.out.println("Insira a mensagem que deseja transformar em morse");
                    String mensagem = input.nextLine();
                    String saida = arvore.converter_mensagem(mensagem);
                    System.out.println("A mensagem em morse é: " + saida);
                }
                case 6 -> {ativo = false;}
                default -> {System.out.println("Algo deu errado, tente novamente");}
            }
        } catch (Exception e) {
            System.out.println("Algo deu errado, tente novamente");
            input.nextLine();
            
        }
    }





    }

void criar_alfabeto(ArvoreMorse arvore){
    arvore.inserir(".-", "A");
    arvore.inserir("-...", "B");
    arvore.inserir("-.-.", "C");
    arvore.inserir("-..", "D");
    arvore.inserir(".", "E");
    arvore.inserir("..-.", "F");
    arvore.inserir("--.", "G");
    arvore.inserir("....", "H");
    arvore.inserir("..", "I");
    arvore.inserir(".---", "J");
    arvore.inserir("-.-", "K");
    arvore.inserir(".-..", "L");
    arvore.inserir("--", "M");
    arvore.inserir("-.", "N");
    arvore.inserir("---", "O");
    arvore.inserir(".--.", "P");
    arvore.inserir("--.-", "Q");
    arvore.inserir(".-.", "R");
    arvore.inserir("...", "S");
    arvore.inserir("-", "T");
    arvore.inserir("..-", "U");
    arvore.inserir("...-", "V");
    arvore.inserir(".--", "W");
    arvore.inserir("-..-", "X");
    arvore.inserir("-.--", "Y");
    arvore.inserir("--..", "Z");
    arvore.inserir("-----", "0");
    arvore.inserir(".----", "1");
    arvore.inserir("..---", "2");
    arvore.inserir("...--", "3");
    arvore.inserir("....-", "4");
    arvore.inserir(".....", "5");
    arvore.inserir("-....", "6");
    arvore.inserir("--...", "7");
    arvore.inserir("---..", "8");
    arvore.inserir("----.", "9");
}

