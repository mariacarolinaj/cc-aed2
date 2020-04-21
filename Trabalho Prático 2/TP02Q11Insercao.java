import java.io.*;
import java.nio.charset.*;

/* Código desenvolvido por Maria Carolina Resende Jaudacy em Setembro/2019
 * Aluna do 2º período de Ciência da Computação - PUC Minas */

public class TP02Q11Insercao {

    public static String obterSubstring(String linha, int indiceInicial, int indiceFinal) {
        String novaLinha = "";

        for (int indice = indiceInicial; indice < indiceFinal; indice++) {
            novaLinha += linha.charAt(indice);
        }

        return novaLinha;
    }

    public static boolean isFim(String valor) {
        return valor.length() >= 3 && valor.charAt(0) == 'F' && valor.charAt(1) == 'I' && valor.charAt(2) == 'M';
    }

    public static Time processarArquivo(String caminhoArquivo) throws IOException {
        FileReader arquivo = new FileReader(caminhoArquivo);
        BufferedReader leitor = new BufferedReader(arquivo);
        String linha = leitor.readLine();

        while (!linha.contains("infobox vcard")) {
            linha = leitor.readLine();
        }

        leitor.close();

        linha = removerTodasTags(linha);

        Time time = analisarLinha(linha);
        time.setNomeArquivo(caminhoArquivo);
        time.setTamanhoPagina(new File(caminhoArquivo).length());

        return time;
    }

    public static String removerTodasTags(String linha) {
        String linhaSemTags = "";
        int indice = 0;

        // busca todos os dados que não estiverem dentro de tags enquanto uma seção não
        // for fechada, ou seja, enquanto não encontrar o </td>
        while (indice + 1 < linha.length()) {
            // procura pelo fechamento das tags que indicam o fim de seções de dados
            if (indice + 4 < linha.length() && linha.substring(indice, indice + 4).equals("/td>")) {
                // coloca separador entre as seções
                linhaSemTags += ">>";
            }

            if (linha.charAt(indice) == '>' && linha.charAt(indice + 1) != '<') {
                // pula para a próxima posição após o fechamento de uma tag
                indice++;
                // armazena todos os valores enquanto a tag não for fechada

                while (indice < linha.length() && linha.charAt(indice) != '<') {
                    linhaSemTags += linha.charAt(indice);
                    indice++;
                }
            }
            indice++;
        }

        return linhaSemTags;
    }

    public static Time analisarLinha(String linha) {
        Time time = new Time();
        String[] secoes;

        // quebra a linha em todos os caracteres '>>', que armazenam cada uma das
        // informações que serão processadas
        secoes = linha.split(">>");

        for (String secao : secoes) {
            if (secao != null) {
                if (secao.contains("Full name")) {
                    time.setNome(obtemNomeCompleto(secao));
                } else if (secao.contains("Nickname(s)")) {
                    time.setApelidos(obtemApelidos(secao));
                } else if (secao.contains("Ground")) {
                    time.setEstadios(buscaDado(secao, "Ground"));
                } else if (secao.contains("anager") && time.getTecnico() == null) {
                    time.setTecnico(buscaDado(secao, "anager"));
                } else if (secao.contains("oach") && time.getTecnico() == null) {
                    time.setTecnico(buscaDado(secao, "oach"));
                } else if (secao.contains("League")) {
                    time.setLigas(obtemLigas(secao));
                } else if (secao.contains("Capacity")) {
                    time.setCapacidade(obtemCapacidadeEstadio(secao));
                } else if (secao.contains("Founded")) {
                    time = obtemFundacao(secao, time);
                }
            }
        }

        return time;
    }

    public static String buscaDado(String linha, String busca) {
        // remove lixo dos dados, como codigos especiais de formatação
        linha = linha.replaceAll("1*&#.*?;", "");

        // procura pelo índice inicial do valor de busca, presente na seção em que
        // está contido
        int indiceInicio = linha.indexOf(busca) + busca.length();

        // corta a string somente entre os índices de fim do nome e fim da linha
        linha = linha.substring(indiceInicio, linha.length());

        // retorna a linha tratada, contendo apenas o dado
        return linha;
    }

    public static String obtemNomeCompleto(String secao) {
        secao = buscaDado(secao, "Full name");

        String nome = "";

        for (int indice = 0; indice < secao.length(); indice++) {
            if (!ehNumero(secao.charAt(indice))) {
                nome += secao.charAt(indice);
            }
        }

        return nome;
    }

    public static String obtemApelidos(String secao) {
        secao = buscaDado(secao, "Nickname(s)");

        if (secao.contains("\"")) {
            secao = secao.replaceAll("\"", "");
        }

        return secao;
    }

    public static String obtemLigas(String secao) {
        secao = buscaDado(secao, "League");

        if (secao.contains(",")) {
            secao = secao.split(",")[0].trim();
        }

        return secao;
    }

    public static int obtemCapacidadeEstadio(String secao) {
        String capacidade = buscaDado(secao, "Capacity");

        // remove vírgulas e pontos que indicam separador de milhar em alguns padrões
        capacidade = capacidade.replace(",", "");
        capacidade = capacidade.replace(".", "");

        int indiceAuxiliar = 0;
        String valorNumericoCapacidade = "";

        while (indiceAuxiliar < capacidade.length() && capacidade.charAt(indiceAuxiliar) != ' '
                && ehNumero(capacidade.charAt(indiceAuxiliar))) {
            valorNumericoCapacidade += capacidade.charAt(indiceAuxiliar);
            indiceAuxiliar++;
        }
        // obtem apenas o dado numerico da capacidade, sem informações adicionais
        int valorCapacidade = 0;
        try {
            valorCapacidade = Integer.parseInt(valorNumericoCapacidade);
        } catch (Exception e) {
            valorCapacidade = -1;
        }

        return valorCapacidade;
    }

    public static Time obtemFundacao(String secao, Time time) {
        String fundacao = "Founded";
        int indiceInicio = secao.indexOf(fundacao) + fundacao.length();

        // corta a string somente entre os índices de fim do nome e fim da linha
        secao = secao.substring(indiceInicio, secao.length());

        int dia = 0, mes = 0, ano = 0;

        // realiza substituição de códigos específicos de html presentes nas datas
        secao = secao.replace("&#160;", " ");
        secao = secao.replace("&#59;", ";");
        secao = secao.replace("&#32;", " ");

        // troca parentese por tag que dá para ser removida no split
        secao = secao.replace("(", "remover");

        // ignora tudo que estiver após o primeiro ; se ele existir
        if (secao.contains(";")) {
            secao = secao.split(";")[0];
        }

        // retira lixo com a tag 'remover'
        if (secao.contains("remover")) {
            secao = secao.split("remover")[0];
        }

        // remove vírgulas e espaços
        secao = secao.replace(",", "");
        secao = secao.replace(" ", "");

        // o ano sempre será a última informação após os tratamentos dados acima
        String anoString = obterSubstring(secao, secao.length() - 4, secao.length());
        if (possuiApenasNumeros(anoString)) {
            ano = Integer.parseInt(anoString);
        }

        // remove ano da string de fundacao
        secao = obterSubstring(secao, 0, secao.length() - 4);

        String diaString = "";

        if (secao.length() > 0) {
            if (ehNumero(secao.charAt(secao.length() - 1))) {
                // variável criada para auxiliar a validação, a partir da última posição da
                // string e encontrar o valor referente ao dia de fundação
                int indiceAuxiliar = secao.length() - 1;

                while (indiceAuxiliar >= 0 && ehNumero(secao.charAt(indiceAuxiliar))) {
                    diaString += secao.charAt(indiceAuxiliar);
                    indiceAuxiliar--;
                }

                dia = Integer.parseInt(inverterString(diaString));
                mes = obterValorNumericoMes(obterSubstring(secao, 0, secao.length() - diaString.length()));
            } else if (ehNumero(secao.charAt(0))) {
                // variável criada para auxiliar a validação, a partir da primeira posição da
                // string e encontrar o valor referente ao dia de fundação
                int indiceAuxiliar = 0;

                while (ehNumero(secao.charAt(indiceAuxiliar))) {
                    diaString += secao.charAt(indiceAuxiliar);
                    indiceAuxiliar++;
                }
                dia = Integer.parseInt(diaString);
                mes = obterValorNumericoMes(obterSubstring(secao, diaString.length(), secao.length()));
            } else {
                mes = obterValorNumericoMes(secao);
            }
        }

        time.setFundacaoDia(dia);
        time.setFundacaoMes(mes);
        time.setFundacaoAno(ano);

        return time;
    }

    public static int obterValorNumericoMes(String mes) {
        int valorMes = 0;

        if (mes.equals("January") || mes.equals("Jan")) {
            valorMes = 1;
        }
        if (mes.equals("February") || mes.equals("Feb")) {
            valorMes = 2;
        }
        if (mes.equals("March") || mes.equals("Mar")) {
            valorMes = 3;
        }
        if (mes.equals("April") || mes.equals("Apr")) {
            valorMes = 4;
        }
        if (mes.equals("May")) {
            valorMes = 5;
        }
        if (mes.equals("June") || mes.equals("Jun")) {
            valorMes = 6;
        }
        if (mes.equals("July") || mes.equals("Jul")) {
            valorMes = 7;
        }
        if (mes.equals("August") || mes.equals("Aug")) {
            valorMes = 8;
        }
        if (mes.equals("September") || mes.equals("Sep")) {
            valorMes = 9;
        }
        if (mes.equals("October") || mes.equals("Oct")) {
            valorMes = 10;
        }
        if (mes.equals("November") || mes.equals("Nov")) {
            valorMes = 11;
        }
        if (mes.equals("December") || mes.equals("Dec")) {
            valorMes = 12;
        }

        return valorMes;
    }

    public static String inverterString(String base) {
        String invertida = "";

        for (int indice = base.length() - 1; indice >= 0; indice--) {
            invertida += base.charAt(indice);
        }

        return invertida;
    }

    public static boolean ehLetra(char caractere) {
        return (caractere >= 'a' && caractere <= 'z') || (caractere >= 'A' && caractere <= 'Z');
    }

    public static boolean ehNumero(char caractere) {
        // 48 é o valor ascii para o número 0 e 57 o valor para o número 9
        return (int) caractere >= 48 && (int) caractere <= 57;
    }

    public static boolean possuiApenasNumeros(String dado) {
        boolean ehNumero = true;

        for (int indice = 0; indice < dado.length(); indice++) {
            if (!ehNumero(dado.charAt(indice))) {
                ehNumero = false;
                indice = dado.length();
            }
        }

        return ehNumero;
    }

    public static void main(String args[]) throws Exception {
        String[] entradas = new String[1000];
        int posicaoUltimoDadoLido = 0;

        MyIO.setCharset("UTF-8");

        do {
            entradas[posicaoUltimoDadoLido] = MyIO.readLine();
        } while (!isFim(entradas[posicaoUltimoDadoLido++]));

        // Desconsidera a ultima linha em que foi inserido a palavra FIM
        posicaoUltimoDadoLido--;

        Lista times = new Lista();

        // processa o arquivo e imprime os dados encontrados
        for (int indice = 0; indice < posicaoUltimoDadoLido; indice++) {
            times.inserirNoFim(processarArquivo(entradas[indice]));
        }

        times.ordenarPorInsercao();
        times.imprimir();
    }
}

class Time {
    private String Nome;
    private String Apelidos;
    private String Estadios;
    private String Tecnico;
    private String Ligas;
    private String NomeArquivo;
    private int Capacidade;
    private int FundacaoDia;
    private int FundacaoMes;
    private int FundacaoAno;
    private long TamanhoPagina;

    public Time() {
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getApelidos() {
        return Apelidos;
    }

    public void setApelidos(String apelidos) {
        Apelidos = apelidos;
    }

    public String getEstadios() {
        return Estadios;
    }

    public void setEstadios(String estadios) {
        Estadios = estadios;
    }

    public String getTecnico() {
        return Tecnico;
    }

    public void setTecnico(String tecnico) {
        Tecnico = tecnico;
    }

    public String getNomeArquivo() {
        return NomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        NomeArquivo = nomeArquivo;
    }

    public String getLigas() {
        return Ligas;
    }

    public void setLigas(String ligas) {
        Ligas = ligas;
    }

    public int getCapacidade() {
        return Capacidade;
    }

    public void setCapacidade(int capacidade) {
        Capacidade = capacidade;
    }

    public int getFundacaoDia() {
        return FundacaoDia;
    }

    public void setFundacaoDia(int dia) {
        if (dia == 0) {
            FundacaoDia = 0;
        } else {
            FundacaoDia = dia;
        }
    }

    public int getFundacaoMes() {
        return FundacaoMes;
    }

    public void setFundacaoMes(int mes) {
        if (mes == 0) {
            FundacaoMes = 0;
        } else {
            FundacaoMes = mes;
        }
    }

    public int getFundacaoAno() {
        return FundacaoAno;
    }

    public void setFundacaoAno(int ano) {
        FundacaoAno = ano;
    }

    public long getTamanhoPagina() {
        return TamanhoPagina;
    }

    public void setTamanhoPagina(long tamanho) {
        TamanhoPagina = tamanho;
    }

    public void imprimirDados() {
        MyIO.println(getNome() + " ## " + getApelidos() + " ## " + obterNumeroAjustado(getFundacaoDia()) + "/"
                + obterNumeroAjustado(getFundacaoMes()) + "/" + getFundacaoAno() + " ## " + getEstadios() + " ## "
                + getCapacidade() + " ## " + getTecnico() + " ## " + getLigas() + " ## " + getNomeArquivo() + " ## "
                + getTamanhoPagina() + " ## ");
    }

    public String obterNumeroAjustado(int numero) {
        String numeroString = String.valueOf(numero);

        if (numeroString.length() == 1) {
            numeroString = "0" + numeroString;
        }

        return numeroString;
    }
}

class Lista {
    private Time[] Itens;
    private int QuantidadeItens = 0;

    public Lista(int quantidadeElementos) {
        Itens = new Time[quantidadeElementos];
    }

    public Lista() {
        Itens = new Time[1000];
    }

    public void inserirNoInicio(Time time) throws Exception {
        // chama o método de inserção informando que esta deve ser feita na primeira
        // posição do vetor
        this.inserir(0, time);
    }

    public void inserirNoFim(Time time) throws Exception {
        // chama o método de inserção informando que esta deve ser feita na última
        // posição válida do vetor
        this.inserir(QuantidadeItens, time);
    }

    public void inserir(int posicao, Time time) throws Exception {
        // verifica se a lista ainda tem capacidade de inserir mais um elemento e se a
        // posição informada por parâmetro é válida
        if (QuantidadeItens >= Itens.length || posicao < 0) {
            throw new Exception("A lista está cheia.");
        }

        if (Itens.length > 1) {
            // movimenta itens da lista uma posição adiante
            for (int indice = QuantidadeItens; indice > posicao; indice--) {
                Itens[indice] = Itens[indice - 1];
            }
        }

        // preenche a posição informada com o valor do time do parâmetro
        Itens[posicao] = time;
        QuantidadeItens++;
    }

    public Time removerNoInicio() throws Exception {
        // chama o método de remoção informando que esta deve ser feita na primeira
        // posição válida do vetor
        return this.remover(0);
    }

    public Time removerNoFim() throws Exception {
        // chama o método de remoção informando que esta deve ser feita na última
        // posição válida do vetor
        return this.remover(QuantidadeItens - 1);
    }

    public Time remover(int posicao) throws Exception {
        // verifica se existe algum elemento na lista
        if (QuantidadeItens == 0) {
            throw new Exception("A lista está vazia.");
        }

        // verifica se a posição informada por parâmetro é válida
        if (posicao < 0 || posicao > QuantidadeItens) {
            throw new Exception("A posição informada é inválida.");
        }

        Time removido = Itens[posicao];

        // copia os itens para uma posição anterior
        for (int indice = posicao; indice < QuantidadeItens; indice++) {
            Itens[indice] = Itens[indice + 1];
        }

        // decrementa da quantidade de itens do vetor
        QuantidadeItens--;

        return removido;
    }

    public boolean pesquisar(String nome) {
        boolean elementoExiste = false;
        for (int indice = 0; indice < QuantidadeItens && elementoExiste == false; indice++) {
            elementoExiste = (Itens[indice].getNome().replaceAll(" ", "").equals(nome.replaceAll(" ", "")));
        }

        return elementoExiste;
    }

    private Time desempateAlfabetico(Time time1, Time time2) {
        Time menor = null;

        for (int indice = 0; indice < time1.getNome().length() && indice < time2.getNome().length(); indice++) {
            if ((int) time1.getNome().charAt(indice) != (int) time2.getNome().charAt(indice)) {
                if ((int) time1.getNome().charAt(indice) < (int) time2.getNome().charAt(indice)) {
                    menor = time1;
                } else {
                    menor = time2;
                }

                indice = time1.getNome().length();
            }
        }
        return menor;
    }

    private Time obtemMaiorOrdem(Time time1, Time time2) {
        Time maiorOrdem = time2;
        if (time1.getFundacaoAno() > time2.getFundacaoAno()) {
            maiorOrdem = time1;
        } else if (time1.getFundacaoAno() == time2.getFundacaoAno()) {
            Time menor = desempateAlfabetico(time1, time2);
            if (menor.equals(time2)) {
                maiorOrdem = time1;
            }
        }
        return maiorOrdem;
    }

    public void ordenarPorInsercao() {
        for (int i = 1; i < QuantidadeItens; i++) {
            Time menor = Itens[i];
            int j = i - 1;
            // busca pelo menor item a partir do índice i
            while (j >= 0 && obtemMaiorOrdem(Itens[j], menor).getNome().equals(Itens[j].getNome())) {
                Itens[j + 1] = Itens[j];
                j--;
            }
            // troca de posição o menor item com a posição atual
            Itens[j + 1] = menor;
        }
    }

    public void imprimir() {
        // imprime todos os elementos a partir da primeira posição, de forma recursiva
        imprimir(0);
    }

    public void imprimir(int posicao) {
        // imprime de forma recursiva todos os itens a partir de determinada posição
        if (posicao < QuantidadeItens) {
            Itens[posicao].imprimirDados();
            imprimir(posicao + 1);
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
