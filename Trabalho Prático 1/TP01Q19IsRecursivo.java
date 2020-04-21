import java.io.*;
import java.nio.charset.*;

/* Nome: Maria Carolina Resende Jaudacy - Matrícula: 667477 
 * 2º período do curso de Ciência da Computação PUC MINAS - 2/2019 */

public class TP01Q19IsRecursivo {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static boolean ehVogal(char letra) {
        return letra == 'a' || letra == 'A' || letra == 'e' || letra == 'E' || letra == 'i' || letra == 'I'
                || letra == 'o' || letra == 'O' || letra == 'u' || letra == 'U';
    }

    public static boolean verificarSePossuiApenasVogais(int indice, String palavra) {
        boolean possuiApenasVogais = true;

        if (indice < palavra.length()) {
            if (palavra.charAt(indice) != ' ' && !ehVogal(palavra.charAt(indice))) {
                possuiApenasVogais = false;
                indice = palavra.length();
            }
            possuiApenasVogais = possuiApenasVogais && verificarSePossuiApenasVogais(indice + 1, palavra);
        }

        return possuiApenasVogais;
    }

    public static boolean ehLetra(char letra) {
        return (letra >= 'a' && letra <= 'z') || (letra >= 'A' && letra <= 'Z');
    }

    public static boolean verificarSePossuiApenasConsoantes(int indice, String palavra) {
        boolean possuiApenasConsoantes = true;

        if (indice < palavra.length()) {
            if (palavra.charAt(indice) != ' '
                    && (ehVogal(palavra.charAt(indice)) || !ehLetra(palavra.charAt(indice)))) {
                possuiApenasConsoantes = false;
                indice = palavra.length();
            }

            possuiApenasConsoantes = possuiApenasConsoantes && verificarSePossuiApenasConsoantes(indice + 1, palavra);
        }

        return possuiApenasConsoantes;
    }

    public static boolean ehNumero(char dado) {
        // na tabela ascii decimal, o número 0 é representado por 48 e o número 9 por
        // 57, sendo os valores entre esses dois números a representação de 0 a 9
        return (int) dado >= 48 && (int) dado <= 57;
    }

    public static boolean verificarSeEhNumeroInteiro(int indice, String palavra) {
        boolean ehInteiro = true;

        if (indice < palavra.length()) {
            if (indice == ',' || indice == '.' || !ehNumero(palavra.charAt(indice))) {
                ehInteiro = false;
                indice = palavra.length();
            }

            ehInteiro = ehInteiro && verificarSeEhNumeroInteiro(indice + 1, palavra);
        }

        return ehInteiro;
    }

    public static boolean verificarSeEhNumeroReal(int indice, boolean temVirgula, String palavra) {
        boolean ehReal = true;

        // realiza validação para saber se o número tem mais de uma vírgula
        if (indice < palavra.length()) {
            if (palavra.charAt(indice) == ',' || palavra.charAt(indice) == '.') {
                if (temVirgula) {
                    ehReal = false;
                    indice = palavra.length();
                } else {
                    temVirgula = true;
                }
            } else if (!ehNumero(palavra.charAt(indice))) {
                ehReal = false;
                indice = palavra.length();
            }

            ehReal = ehReal && verificarSeEhNumeroReal(indice + 1, temVirgula, palavra);
        }

        return ehReal;
    }

    public static String avaliarString(String palavra) {
        String resultado = "";

        if (verificarSePossuiApenasVogais(0, palavra)) {
            resultado += "SIM";
        } else {
            resultado += "NAO";
        }

        if (verificarSePossuiApenasConsoantes(0, palavra)) {
            resultado += " SIM";
        } else {
            resultado += " NAO";
        }

        if (verificarSeEhNumeroInteiro(0, palavra)) {
            resultado += " SIM";
        } else {
            resultado += " NAO";
        }

        if (verificarSeEhNumeroReal(0, false, palavra)) {
            resultado += " SIM";
        } else {
            resultado += " NAO";
        }

        return resultado;
    }

    public static void main(String args[]) {
        int posicaoUltimaPalavraLida = 0;
        String[] entradas = new String[1000];

        // Realiza leitura das entradas
        do {
            entradas[posicaoUltimaPalavraLida] = MyIO.readLine();
        } while (!isFim(entradas[posicaoUltimaPalavraLida++]));

        // Desconsidera o índice da entrada com a flag FIM
        posicaoUltimaPalavraLida--;

        for (int indice = 0; indice < posicaoUltimaPalavraLida; indice++) {
            MyIO.println(avaliarString(entradas[indice]));
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