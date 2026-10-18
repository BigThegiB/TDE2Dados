class FilaCircular {
    private int[] dados;
    private int primeiro;
    private int ultimo;
    private int max_itens;
    private int tamanho_atual;


    public FilaCircular(int max_itens) {
        this.max_itens = max_itens;
        dados = new int[max_itens];
        primeiro = 0;
        ultimo = -1;
        tamanho_atual = 0;
    }

    static boolean cheia(FilaCircular F) {
        return F.tamanho_atual == F.max_itens;
    }

    static boolean vazia(FilaCircular F) {
        return F.tamanho_atual == 0;
    }


    static void insere(FilaCircular F, int E) {
        if (cheia(F)) {
            System.out.println("Cheia haha");
            return;
        }
        if (++F.ultimo == F.max_itens) F.ultimo = 0;
        F.dados[F.ultimo] = E;
        F.tamanho_atual++;
    }


    static void remove(FilaCircular F) {
        if (vazia(F)) {
            System.out.println("Vazia");
            return;
        }

        if (++F.primeiro == F.max_itens) F.primeiro = 0;
        F.tamanho_atual--;
    }

    static int primeiro(FilaCircular F) throws Exception {
        if (!vazia(F)) {
            return F.dados[F.primeiro];
        } else throw new Exception("Lista vazia");
    }

    static int ultimo(FilaCircular F) throws Exception {
        if (!vazia(F)) {
            return F.dados[F.ultimo];
        } else throw new Exception("Lista vazia");
    }


    static FilaCircular merge(FilaCircular F1, FilaCircular F2) {
        int total_itens = F1.max_itens + F2.max_itens;
        FilaCircular F3 = new FilaCircular(total_itens);
        try {
            while (!vazia(F1) && !vazia(F2)) {
                int val1 = primeiro(F1);
                int val2 = primeiro(F2);
                if (val1 < val2) {
                    insere(F3, val1);
                    remove(F1);
                } else {
                    insere(F3, val2);
                    remove(F2);
                }

            }
                FilaCircular F = (!vazia(F1)) ? F1 : F2;
                while (!vazia(F)) {

                    int val = primeiro(F);
                    insere(F3, val);
                    remove(F);
                }
        } catch (Exception e) {
            System.out.println("Tentativa de acessar lista vazia");
        }

        return F3;
    }

    void imprime() {
        int itens_lidos = 0;
        int primeiro = this.primeiro;
        while (itens_lidos != tamanho_atual) {
            System.out.print(dados[primeiro] + " ");
            if (++primeiro == max_itens) primeiro = 0;
            itens_lidos++;
        }
        System.out.println();
    }

    public static void main() throws Exception {
        FilaCircular f1 = new FilaCircular(4);
        FilaCircular.insere(f1, 10);
        FilaCircular.insere(f1, 20);
        FilaCircular.insere(f1, 30);
        FilaCircular.remove(f1);
        FilaCircular.insere(f1, 40);
        FilaCircular.insere(f1, 50);
        f1.imprime();

        System.out.println(FilaCircular.primeiro(f1));
        System.out.println(FilaCircular.ultimo(f1));

        FilaCircular filaA = new FilaCircular(3);
        FilaCircular.insere(filaA, 1);
        FilaCircular.insere(filaA, 3);
        FilaCircular.insere(filaA, 5);

        FilaCircular filaB = new FilaCircular(3);
        FilaCircular.insere(filaB, 2);
        FilaCircular.insere(filaB, 4);
        FilaCircular.insere(filaB, 6);

        FilaCircular filaC = FilaCircular.merge(filaA, filaB);
        filaC.imprime();

        FilaCircular filaD = new FilaCircular(2);
        FilaCircular.insere(filaD, 10);
        FilaCircular.insere(filaD, 50);

        FilaCircular filaE = new FilaCircular(4);
        FilaCircular.insere(filaE, 5);
        FilaCircular.insere(filaE, 15);
        FilaCircular.insere(filaE, 25);
        FilaCircular.insere(filaE, 35);

        FilaCircular filaF = FilaCircular.merge(filaC, filaD);
        filaF.imprime();
    }
}
