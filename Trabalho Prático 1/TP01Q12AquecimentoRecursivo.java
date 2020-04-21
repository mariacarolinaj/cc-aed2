class TP01Q12AquecimentoRecursivo {
    public static boolean isMaiuscula(char c) {
        return (c >= 'A' && c <= 'Z');
    }

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static int contarLetrasMaiusculas(int indiceAtual, String s) {
        int resp = 0;

        if (indiceAtual < s.length()) {
            if (isMaiuscula(s.charAt(indiceAtual)) == true) {
                resp++;
            }

            resp += contarLetrasMaiusculas(indiceAtual + 1, s);
        }

        return resp;
    }

    public static void main(String[] args) {
        String[] entrada = new String[1000];
        int numEntrada = 0;

        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

        // Para cada linha de entrada, gerando uma de saida contendo o numero de letras
        // maiusculas da entrada
        for (int i = 0; i < numEntrada; i++) {
            // inicializa o parâmetro indiceAtual como 0 para começar o método pela primeira
            // posição da string
            MyIO.println(contarLetrasMaiusculas(0, entrada[i]));
        }
    }
}
