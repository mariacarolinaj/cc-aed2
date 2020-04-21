import java.io.*;
import java.nio.charset.*;
import java.net.*;

/* Nome: Maria Carolina Resende Jaudacy - Matrícula: 667477 
 * 2º período do curso de Ciência da Computação PUC MINAS - 2/2019 */

public class TP01Q09LeituraPaginaHTML {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String avaliarArquivo(PaginaHtml pagina) {
        // declaração de variáveis com valores a serem avaliados
        int a = 0, e = 0, i = 0, o = 0, u = 0;
        int aAgudo = 0, eAgudo = 0, iAgudo = 0, oAgudo = 0, uAgudo = 0;
        int aGrave = 0, eGrave = 0, iGrave = 0, oGrave = 0, uGrave = 0;
        int aTil = 0, oTil = 0;
        int aCircunflexo = 0, eCircunflexo = 0, iCircunflexo = 0, oCircunflexo = 0, uCircunflexo = 0;
        int consoantes = 0, br = 0, table = 0;

        try {

            URL url = new URL(pagina.Endereco);

            InputStream inputStream = url.openStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha = bufferedReader.readLine();

            while (linha != null) {

                a += contaOcorrencias(converterCharParaInt('a'), linha);
                e += contaOcorrencias(converterCharParaInt('e'), linha);
                i += contaOcorrencias(converterCharParaInt('i'), linha);
                o += contaOcorrencias(converterCharParaInt('o'), linha);
                u += contaOcorrencias(converterCharParaInt('u'), linha);
                // valor ascii para á: 225
                aAgudo += contaOcorrencias(225, linha);
                // valor ascii para é: 233
                eAgudo += contaOcorrencias(233, linha);
                // valor ascii para í: 237
                iAgudo += contaOcorrencias(237, linha);
                // valor ascii para ó: 243
                oAgudo += contaOcorrencias(243, linha);
                // valor ascii para ú: 250
                uAgudo += contaOcorrencias(250, linha);
                // valor ascii para à: 224
                aGrave += contaOcorrencias(224, linha);
                // valor ascii para è: 232
                eGrave += contaOcorrencias(232, linha);
                // valor ascii para ì: 236
                iGrave += contaOcorrencias(236, linha);
                // valor ascii para ò: 242
                oGrave += contaOcorrencias(242, linha);
                // valor ascii para ù: 249
                uGrave += contaOcorrencias(249, linha);
                // valor ascii para ã: 227
                aTil += contaOcorrencias(227, linha);
                // valor ascii para õ: 245
                oTil += contaOcorrencias(245, linha);
                // valor ascii para â: 226
                aCircunflexo += contaOcorrencias(226, linha);
                // valor ascii para ê: 234
                eCircunflexo += contaOcorrencias(234, linha);
                // valor ascii para î: 238
                iCircunflexo += contaOcorrencias(238, linha);
                // valor ascii para ô: 244
                oCircunflexo += contaOcorrencias(244, linha);
                // valor ascii para û: 251
                uCircunflexo += contaOcorrencias(251, linha);
                consoantes += contaConsoantes(linha);
                br += contaBr(linha);
                table += contaTable(linha);

                linha = bufferedReader.readLine();
            }

        } catch (IOException excecao) {
            excecao.printStackTrace();
        }

        // remove da contagem do char 'a' a ocorrência presente na tag <table>
        a -= table;
        // remove da contagem do char 'e' a ocorrência presente na tag <table>
        e -= table;
        // remove da contagem de consoantes as letras presentes nas tags <table> e <br>
        consoantes -= (table * 3 * 2) + (br * 2 * 2);

        return "a(" + a + ") e(" + e + ") i(" + i + ") o(" + o + ") u(" + u + ") á(" + aAgudo + ") é(" + eAgudo + ") í("
                + iAgudo + ") ó(" + oAgudo + ") ú(" + uAgudo + ") à(" + aGrave + ") è(" + eGrave + ") ì(" + iGrave
                + ") ò(" + oGrave + ") ù(" + uGrave + ") ã(" + aTil + ") õ(" + oTil + ") â(" + aCircunflexo + ") ê("
                + eCircunflexo + ") î(" + iCircunflexo + ") ô(" + oCircunflexo + ") û(" + uCircunflexo + ") consoante("
                + consoantes + ") <br>(" + br + ") <table>(" + table + ") " + pagina.Nome;
    }

    public static int converterCharParaInt(char caracter) {
        return (int) caracter;
    }

    public static int contaOcorrencias(int valorCaracter, String frase) {
        int quantidadeOcorrencias = 0;

        for (int indice = 0; indice < frase.length(); indice++) {
            if ((int) frase.charAt(indice) == valorCaracter) {
                quantidadeOcorrencias++;
            }
        }

        return quantidadeOcorrencias;
    }

    public static int contaConsoantes(String frase) {
        int quantidadeConstantes = 0;

        for (int indice = 0; indice < frase.length(); indice++) {
            if (ehConsoante(frase.charAt(indice))) {
                quantidadeConstantes++;
            }
        }

        return quantidadeConstantes;
    }

    public static boolean ehConsoante(char caracter) {
        return caracter != 'a' && caracter != 'A' && caracter != 'e' && caracter != 'E' && caracter != 'i'
                && caracter != 'I' && caracter != 'o' && caracter != 'O' && caracter != 'u' && caracter != 'U'
                && ((caracter > 'a' && caracter <= 'z') || (caracter > 'A' && caracter <= 'Z'));
    }

    public static int contaBr(String frase) {
        int quantidadeBr = 0;

        for (int indice = 0; indice < frase.length(); indice++) {
            if (frase.charAt(indice) == '<' && frase.charAt(indice + 1) == 'b' && frase.charAt(indice + 2) == 'r'
                    && frase.charAt(indice + 3) == '>') {
                quantidadeBr++;
                indice += 3;
            }
        }

        return quantidadeBr;
    }

    public static int contaTable(String frase) {
        int quantidadeTable = 0;

        for (int indice = 0; indice < frase.length(); indice++) {
            if (frase.charAt(indice) == '<' && frase.charAt(indice + 1) == 't' && frase.charAt(indice + 2) == 'a'
                    && frase.charAt(indice + 3) == 'b' && frase.charAt(indice + 4) == 'l'
                    && frase.charAt(indice + 5) == 'e' && frase.charAt(indice + 6) == '>') {
                quantidadeTable++;
                indice += 6;
            }
        }

        return quantidadeTable;
    }

    public static void main(String args[]) {
        int posicaoUltimaEntradaLida = 0;
        PaginaHtml[] entradas = new PaginaHtml[1000];

        MyIO.setCharset("ISO-8859-1");

        // Realiza leitura das entradas
        do {
            entradas[posicaoUltimaEntradaLida] = new PaginaHtml();
            entradas[posicaoUltimaEntradaLida].Nome = MyIO.readLine();
            if (!isFim(entradas[posicaoUltimaEntradaLida].Nome)) {
                entradas[posicaoUltimaEntradaLida].Endereco = MyIO.readLine();
            }
        } while (!isFim(entradas[posicaoUltimaEntradaLida++].Nome));

        // Desconsidera o índice da entrada com a flag FIM
        posicaoUltimaEntradaLida--;

        for (int indice = 0; indice < posicaoUltimaEntradaLida; indice++) {
            MyIO.println(avaliarArquivo(entradas[indice]));
        }
    }
}

class PaginaHtml {
    public String Nome;
    public String Endereco;

    public PaginaHtml() {
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