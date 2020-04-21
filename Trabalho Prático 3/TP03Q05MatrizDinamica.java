import java.io.*;
import java.nio.charset.*;

/* Código desenvolvido por Maria Carolina Resende Jaudacy em Outubro/2019
 * Aluna do 2º período de Ciência da Computação - PUC Minas */

public class TP03Q05MatrizDinamica {
    public static void main(String[] args) {
        Matriz m = new Matriz(2, 2);
        m.mostrar();
    }
}

class Celula {
    int Elemento;
    Celula Superior, Inferior, Esquerda, Direita;

    public Celula() {
        this.Superior = null;
        this.Inferior = null;
        this.Esquerda = null;
        this.Direita = null;
    }

    public Celula(int elemento, Celula sup, Celula inf, Celula esq, Celula dir) {
        this.Elemento = elemento;
        this.Superior = sup;
        this.Inferior = inf;
        this.Esquerda = esq;
        this.Direita = dir;
    }

    public Celula(int elemento) {
        this(elemento, null, null, null, null);
    }
}

class Matriz {
    int Linha, Coluna;
    Celula Inicio;

    public Matriz() {
        this(3, 3);
    }

    public Matriz(int linhas, int colunas) {
        this.Linha = linhas;
        this.Coluna = colunas;
        this.inicializaMatriz();
    }

    private void inicializaMatriz() {
        Celula ultima = this.Inicio, celulaAnteriorEsquerda = null, celulaAnteriorSuperior = null;
        int teste = 0;

        for (int i = 0; i < this.Linha; i++, ultima.Superior = ultima, ultima = ultima.Inferior) {
            for (int j = 0; j < this.Coluna; j++, ultima.Esquerda = ultima, ultima = ultima.Direita) {
                ultima = new Celula(teste++);
                if (celulaAnteriorEsquerda != null) {
                    celulaAnteriorEsquerda.Direita = ultima;
                }
                ultima.Esquerda = celulaAnteriorEsquerda;

                if (celulaAnteriorEsquerda != null && celulaAnteriorEsquerda.Superior != null) {
                    ultima.Superior = celulaAnteriorSuperior;
                }

                celulaAnteriorSuperior = celulaAnteriorSuperior.Direita;
                celulaAnteriorEsquerda = ultima;
            }
        }
    }

    public Matriz soma(Matriz outra) throws Exception {
        if (outra.Linha != this.Linha || outra.Coluna != this.Coluna) {
            throw new Exception("Não é possível somar. As matrizes tem tamanhos diferentes.");
        }

        Matriz resultado = new Matriz(this.Linha, this.Coluna);
        Celula c1, c2, soma;

        for (c1 = this.Inicio, c2 = outra.Inicio, soma = resultado.Inicio; c1 != null || c2 != null
                || soma != null; c1 = c1.Inferior, c2 = c2.Inferior) {
            for (Celula d1 = c1, d2 = c2, d3 = soma; d1 != null || d2 != null
                    || d3 != null; d1 = d1.Direita, d2 = d2.Direita, d3 = d3.Direita) {
                d3.Elemento = d1.Elemento + d2.Elemento;
            }
        }

        return resultado;
    }

    public Matriz multiplica(Matriz outra) throws Exception {
        if (outra.Linha != this.Linha || outra.Coluna != this.Coluna) {
            throw new Exception("Não é possível multiplicar. As matrizes tem tamanhos diferentes.");
        }

        Matriz resultado = new Matriz(this.Linha, this.Coluna);
        Celula c1, c2, soma;

        for (c1 = this.Inicio, c2 = outra.Inicio, soma = resultado.Inicio; c1 != null || c2 != null
                || soma != null; c1 = c1.Inferior, c2 = c2.Inferior) {
            for (Celula d1 = c1, d2 = c2, d3 = soma; d1 != null || d2 != null
                    || d3 != null; d1 = d1.Direita, d2 = d2.Direita, d3 = d3.Direita) {
                d3.Elemento = d1.Elemento * d2.Elemento;
            }
        }

        return resultado;
    }

    public void mostrarDiagonalPrincipal() {
        for (Celula c = this.Inicio; c.Inferior != null; c = c.Inferior.Direita) {
            MyIO.println(c.Elemento);
        }
    }

    public void mostrarDiagonalSecundaria() {

    }

    public void mostrar() {
        for (Celula c1 = this.Inicio; c1 != null; c1 = c1.Inferior) {
            for (Celula d1 = c1; d1 != null; d1 = d1.Direita) {
                MyIO.print(d1.Elemento + " ");
            }
            MyIO.println();
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
