
import java.io.*;
import java.nio.charset.*;

/* Nome: Maria Carolina Resende Jaudacy - Matrícula: 667477 
 * 2º período do curso de Ciência da Computação PUC MINAS - 2/2019 */

public class TP01Q07AlgebraBooleana {

    public static boolean verificarFlagFimLeitura(String s) {
        return (s.length() >= 1 && s.charAt(0) == '0');
    }

    public static void avaliarExpressao(String expressao) {
        expressao = removerEspacos(expressao);
        int[] valoresVariaveis = obterValoresVariaveis(expressao);

        expressao = obterExpressaoSemDadosInicializacao(expressao);

    }

    public static int[] obterValoresVariaveis(String expressao) {
        int quantidadeVariaveis = (int) expressao.charAt(0);
        int[] valores = new int[quantidadeVariaveis];
        int ultimoIndiceInserido = 0;

        // O for é inicializado em 1 para pular a entrada da quantidade de variáveis
        for (int indice = 1; indice < quantidadeVariaveis; indice++) {
            if ((int) expressao.charAt(indice) > 0) {
                valores[ultimoIndiceInserido] = (int) expressao.charAt(indice);
                ultimoIndiceInserido++;
            }
        }

        return valores;
    }

    public static String removerEspacos(String expressao) {
        String novaExpressao = "";

        for (int indice = 0; indice < expressao.length(); indice++) {
            if (expressao.charAt(indice) != ' ') {
                novaExpressao += expressao.charAt(indice);
            }
        }

        return novaExpressao;
    }

    public static String obterExpressaoSemDadosInicializacao(String expressao) {
        String expressaoSemDadosIniciais = "";
        int quantidadeCharASeremRemovidosDoInicio = 1 + (int) expressao.charAt(0);

        // decrementa 1 da quantidadeCharASeremRemovidosDoInicio pois a primeira posição
        // do vetor é 0
        for (int indice = quantidadeCharASeremRemovidosDoInicio - 1; indice < expressao.length(); indice++) {
            expressaoSemDadosIniciais += expressao.charAt(indice);
        }

        return expressaoSemDadosIniciais;
    }

    public static String substituirVariaveis(int[] valores, String expressao) {
        String novaExpressao = "";

        for (int indice = 0; indice < expressaoSemValoresIniciais.length(); indice++) {
            switch (expressao.charAt(indice)) {
            case 'A':
                novaExpressao += valores[0];
                break;
            case 'B':
                novaExpressao += valores[1];
                break;
            case 'C':
                novaExpressao += valores[2];
                break;
            case 'D':
                novaExpressao += valores[3];
                break;
            default:
                novaExpressao += expressao.charAt(indice);
            }
        }

        return novaExpressao;
    }

    public static String resolverOperacoesNot(String expressao) {
        String novaExpressao = "";

        for (int indice = 0; indice < expressao.length(); indice++) {
            if (indice + 3 < expressao.length() && expressao.charAt(indice) == 'n'
                    && expressao.charAt(indice + 1) == 'o' && expressao.charAt(indice + 2) == 't') {
                // compensa os indices removidos da expressão not(X) a partir do 'o'
                indice += 5;
                // obtem o valor a ser negado (que está entre os parenteses)
                String valorASerNegado = expressao.charAt(indice + 4);
                if (valorASerNegado == '1') {
                    novaExpressao += '0';
                } else if (valorASerNegado == '0') {
                    novaExpressao += '1';
                }
            } else {
                novaExpressao += expressao.charAt(indice);
            }
        }

        return novaExpressao;
    }

    public static String resolverOperacoesAndEOr(String expressao) {
        String novaExpressao = "";

        for (int indice = expressao.length() - 1; indice >= 0; indice--) {
            if (expressao.charAt(indice) == 'a' && expressao.charAt(indice - 1) == 'n'
                    && expressao.charAt(indice - 2) == 'd') {

                int[] valoresExpressao = new int[1000];
                int ultimoIndiceValorLido = 0;
                // soma 1 ao indice para começar a leitura a partir do primeiro parêntese
                int aux = indice + 1;
                int quantidadeCaracteresLidos = 4;

                do {
                    if (expressao.charAt(aux) != ',' && expressao.charAt(aux) != ')') {
                        valoresExpressao[ultimoIndiceValorLido] = (int) expressao.charAt(aux);
                        ultimoIndiceValorLido++;
                        quantidadeCaracteresLidos++;
                    }
                } while (expressao.charAt(aux) == ')');

                int resultado = valoresExpressao[ultimoIndiceValorLido--];

                do {
                    resultado = (boolean) resultado && (boolean) valoresExpressao[ultimoIndiceValorLido];
                } while (--ultimoIndiceValorLido >= 0);

                novaExpressao += resultado;
                indice = indice - quantidadeCaracteresLidos;
            }
        }

        return novaExpressao;
    }

    public static void main(String args[]) {
        int posicaoUltimaExpressaoLida = 0;
        String[] expressoes = new String[1000];

        MyIO.setCharset("ISO-8859-1");

        // Realiza leitura das entradas
        do {
            expressoes[posicaoUltimaExpressaoLida] = MyIO.readLine();
        } while (!verificarFlagFimLeitura(expressoes[posicaoUltimaExpressaoLida++]));

        // Desconsidera o índice da entrada com a flag 0 na quantidade de valores da
        // expressão
        posicaoUltimaExpressaoLida--;

        // Imprime o resultado de cada uma das expressões: 1 para VERDADEIRO e 0 para
        // FALSO
        for (int indicePalavra = 0; indicePalavra <= posicaoUltimaExpressaoLida; indicePalavra++) {
            MyIO.println(codificarCiframento(entradas[indicePalavra], chave));
        }
    }
}

class MyIO {

    private static BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in, Charset.forName("ISO-8859-1")));
    private static String charset = "ISO-8859-1";

    public static void setCharset(String charset_) {
        charset = charset_;
        in = new BufferedReader(new InputStreamReader(System.in, Charset.forName(charset)));
    }

    public static void print() {
    }

    public static void print(int x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void print(double x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void print(String x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void print(boolean x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void print(char x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void println() {
    }

    public static void println(int x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.println(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void println(double x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.println(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void println(String x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.println(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void println(boolean x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.println(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void println(char x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.println(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void printf(String formato, double x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.printf(formato, x);// "%.2f"
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static double readDouble() {
        double d = -1;
        try {
            d = Double.parseDouble(readString().trim().replace(",", "."));
        } catch (Exception e) {
        }
        return d;
    }

    public static double readDouble(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readDouble();
    }

    public static float readFloat() {
        return (float) readDouble();
    }

    public static float readFloat(String str) {
        return (float) readDouble(str);
    }

    public static int readInt() {
        int i = -1;
        try {
            i = Integer.parseInt(readString().trim());
        } catch (Exception e) {
        }
        return i;
    }

    public static int readInt(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readInt();
    }

    public static String readString() {
        String s = "";
        char tmp;
        try {
            do {
                tmp = (char) in.read();
                if (tmp != '\n' && tmp != ' ' && tmp != 13) {
                    s += tmp;
                }
            } while (tmp != '\n' && tmp != ' ');
        } catch (IOException ioe) {
            System.out.println("lerPalavra: " + ioe.getMessage());
        }
        return s;
    }

    public static String readString(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readString();
    }

    public static String readLine() {
        String s = "";
        char tmp;
        try {
            do {
                tmp = (char) in.read();
                if (tmp != '\n' && tmp != 13) {
                    s += tmp;
                }
            } while (tmp != '\n');
        } catch (IOException ioe) {
            System.out.println("lerPalavra: " + ioe.getMessage());
        }
        return s;
    }

    public static String readLine(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readLine();
    }

    public static char readChar() {
        char resp = ' ';
        try {
            resp = (char) in.read();
        } catch (Exception e) {
        }
        return resp;
    }

    public static char readChar(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readChar();
    }

    public static boolean readBoolean() {
        boolean resp = false;
        String str = "";

        try {
            str = readString();
        } catch (Exception e) {
        }

        if (str.equals("true") || str.equals("TRUE") || str.equals("t") || str.equals("1") || str.equals("verdadeiro")
                || str.equals("VERDADEIRO") || str.equals("V")) {
            resp = true;
        }

        return resp;
    }

    public static boolean readBoolean(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readBoolean();
    }

    public static void pause() {
        try {
            in.read();
        } catch (Exception e) {
        }
    }

    public static void pause(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        pause();
    }
}