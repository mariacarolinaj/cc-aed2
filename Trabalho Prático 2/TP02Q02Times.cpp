#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct
{
    char *Nome;
    char *Apelidos;
    char *Estadios;
    char *Tecnico;
    char *Ligas;
    char *NomeArquivo;
    int Capacidade;
    int FundacaoDia;
    int FundacaoMes;
    int FundacaoAno;
    long TamanhoPagina;
} Time;

Time processarArquivo(char *caminhoArquivo)
{
    char *linha = NULL;
    FILE *arquivo = fopen(caminhoArquivo, "rb");
    size_t tamanho = 0;

    // verifica se o arquivo foi aberto com sucesso; se não, encerra a execução do programa
    if (arquivo == NULL)
    {
        printf("Arquivo não encontrado.");
        exit(1);
    }
    else
    {
        getLine(&linha, &tamanho, arquivo);
        // procura pela linha que possui a informação "vcard", que é onde estão as informações dos times
        while (!feof(arquivo) && strstr(linha, "vcard") == 0)
        {
            getLine(&linha, &tamanho, arquivo);
        }
    }

    fclose(arquivo);

    linha = removerTodasTags(linha);

    return linha;
}

char *removerTodasTags(char *linha)
{
    char *linhaSemTags = "";
    int indice = 0;

    // busca todos os dados que não estiverem dentro de tags enquanto uma seção não
    // for fechada, ou seja, enquanto não encontrar o </td>
    while (indice + 1 < strlen(linha))
    {
        // procura pelo fechamento das tags que indicam o fim de seções de dados
        if (indice + 4 < strlen(linha) && obterSubstring(linha, indice, indice + 4).equals("/td>"))
        {
            // coloca separador entre as seções
            linhaSemTags += ">>";
        }

        if (linha[indice] == '>' && linha[indice + 1] != '<')
        {
            // pula para a próxima posição após o fechamento de uma tag
            indice++;
            // armazena todos os valores enquanto a tag não for fechada

            while (indice < strlen(linha) && linha[indice] != '<')
            {
                linhaSemTags += linha[indice];
                indice++;
            }
        }
        indice++;
    }

    return linhaSemTags;
}

Time analisarLinha(char *linha)
{
    Time time = new Time();
    char *secoes[100];

    // quebra a linha em todos os caracteres '>>', que armazenam cada uma das
    // informações que serão processadas
    secoes = linha.split(">>");

    for (int i = 0; i < 100; i++)
    {
        if (secao != null)
        {
            if (strstr(secoes[i], "Full name") != 0)
            {
                time.setNome(buscaDado(secoes[i], "Full name"));
            }
            else if (strstr(secoes[i], "Nickname(s)") != 0)
            {
                time.setApelidos(buscaDado(secoes[i], "Nickname(s)"));
            }
            else if (strstr(secoes[i], "Ground") != 0)
            {
                time.setEstadios(buscaDado(secoes[i], "Ground"));
            }
            else if (strstr(secoes[i], "Head coach") != 0)
            {
                time.setTecnico(buscaDado(secoes[i], "Head coach"));
            }
            else if (strstr(secoes[i], "Manager") != 0)
            {
                time.setTecnico(buscaDado(secoes[i], "Manager"));
            }
            else if (strstr(secoes[i], "League") != 0)
            {
                time.setLigas(buscaDado(secoes[i], "League"));
            }
            else if (strstr(secoes[i], "Capacity") != 0)
            {
                time.setCapacidade(obtemCapacidadeEstadio(secoes[i]));
            }
            else if (strstr(secoes[i], "Founded") != 0)
            {
                time = obtemFundacao(secoes[i], time);
            }
        }
    }

    return time;
}

char *buscaDado(char *linha, char *busca)
{
    // remove lixo dos dados, como codigos especiais de formatação
    linha = linha.replaceAll("1*&#.*?;", "");

    // procura pelo índice inicial do valor de busca, presente na seção em que
    // está contido
    int indiceInicio = indexOf(linha, busca) + strlen(busca);

    // corta a string somente entre os índices de fim do nome e fim da linha
    linha = linha.substring(indiceInicio, strlen(linha));

    // retorna a linha tratada, contendo apenas o dado
    return linha;
}

long int indexOf(char *linha, char *busca)
{
    char *pointer = strstr(linha, busca);
    return pointer - linha;
}

int obtemCapacidadeEstadio(char *secao)
{
    char *capacidade = buscaDado(secao, "Capacity");

    // remove vírgulas e pontos que indicam separador de milhar em alguns padrões
    capacidade = capacidade.replace(",", "");
    capacidade = capacidade.replace(".", "");

    int indiceAuxiliar = 0;
    char *valorNumericoCapacidade = "";

    while (indiceAuxiliar < strlen(capacidade) && capacidade[indiceAuxiliar] != ' ' && ehNumero(capacidade[indiceAuxiliar]) == 1)
    {
        valorNumericoCapacidade += capacidade[indiceAuxiliar];
        indiceAuxiliar++;
    }
    // obtem apenas o dado numerico da capacidade, sem informações adicionais
    return (int)(valorNumericoCapacidade);
}

int ehNumero(char caractere)
{
    // 48 é o valor ascii para o número 0 e 57 o valor para o número 9
    return (int)caractere >= 48 && (int)caractere <= 57;
}

int possuiApenasNumeros(char *dado)
{
    int ehNumero = 1;

    for (int indice = 0; indice < strlen(dado); indice++)
    {
        if (ehNumero(dado[indice] == 0))
        {
            ehNumero = 0;
            indice = strlen(dado);
        }
    }

    return ehNumero;
}

Time obtemFundacao(char *secao, Time time)
{
    char *fundacao = "Founded";
    int indiceInicio = indexOf(secao, fundacao) + strlen(fundacao);

    // corta a string somente entre os índices de fim do nome e fim da linha
    secao = secao.substring(indiceInicio, strlen(secao));

    int dia = 0, mes = 0, ano = 0;

    // realiza substituição de códigos específicos de html presentes nas datas
    secao = secao.replace("&#160;", " ");
    secao = secao.replace("&#59;", ";");
    secao = secao.replace("&#32;", " ");

    // ignora tudo que estiver após o primeiro ; se ele existir
    if (strstr(secao, ";") == 0)
    {
        secao = secao.split(";")[0];
    }

    // remove vírgulas
    secao = secao.replace(",", "");

    // o ano sempre será a última informação após os tratamentos dados acima
    String anoString = obterSubstring(secao, strlen(secao) - 4, strlen(secao));

    if (possuiApenasNumeros(anoString) == 0)
    {
        ano = (int)(anoString);
    }

    // remove ano da string de fundacao
    secao = obterSubstring(secao, 0, strlen(secao) - 4);

    // remove espaços; nessa parte da análise, só sobrarão o número referente ao dia
    // e uma string referente ao mês, então os espaços podem atrapalhar a análise
    secao = secao.replace(" ", "");
    char *diaString = "";

    if (strlen(secao) > 0)
    {
        if (ehNumero(secao[strlen(secao) - 1]) == 0)
        {
            // variável criada para auxiliar a validação, a partir da última posição da
            // string e encontrar o valor referente ao dia de fundação
            int indiceAuxiliar = strlen(secao) - 1;

            while (indiceAuxiliar >= 0 && ehNumero(secao[indiceAuxiliar]) == 0)
            {
                diaString += secao[indiceAuxiliar];
                indiceAuxiliar--;
            }

            dia = (int)(inverterString(diaString));

            mes = obterValorNumericoMes(obterSubstring(secao, 0, strlen(secao) - strlen(diaString)));
        }
        else if (ehNumero(secao[0]) == 0)
        {
            // variável criada para auxiliar a validação, a partir da primeira posição da
            // string e encontrar o valor referente ao dia de fundação
            int indiceAuxiliar = 0;

            while (ehNumero(secao[indiceAuxiliar]))
            {
                diaString += secao[indiceAuxiliar];
                indiceAuxiliar++;
            }
            dia = (int)(diaString);
            mes = obterValorNumericoMes(obterSubstring(secao, strlen(diaString), strlen(secao)));
        }
        else
        {
            mes = obterValorNumericoMes(secao);
        }
    }

    // time.setFundacaoDia(dia);
    // time.setFundacaoMes(mes);
    // time.setFundacaoAno(ano);

    return time;
}

char *obterSubstring(char *linha, int indiceInicial, int indiceFinal)
{
    char *novaLinha = "";

    for (int indice = indiceInicial; indice < indiceFinal; indice++)
    {
        novaLinha += linha[indice];
    }

    return novaLinha;
}

int obterValorNumericoMes(char *mes)
{
    int valorMes = 0;

    if (mes.equals("January") || mes.equals("Jan"))
    {
        valorMes = 1;
    }
    if (mes.equals("February") || mes.equals("Feb"))
    {
        valorMes = 2;
    }
    if (mes.equals("March") || mes.equals("Mar"))
    {
        valorMes = 3;
    }
    if (mes.equals("April") || mes.equals("Apr"))
    {
        valorMes = 4;
    }
    if (mes.equals("May"))
    {
        valorMes = 5;
    }
    if (mes.equals("June") || mes.equals("Jun"))
    {
        valorMes = 6;
    }
    if (mes.equals("July") || mes.equals("Jul"))
    {
        valorMes = 7;
    }
    if (mes.equals("August") || mes.equals("Aug"))
    {
        valorMes = 8;
    }
    if (mes.equals("September") || mes.equals("Sep"))
    {
        valorMes = 9;
    }
    if (mes.equals("October") || mes.equals("Oct"))
    {
        valorMes = 10;
    }
    if (mes.equals("November") || mes.equals("Nov"))
    {
        valorMes = 11;
    }
    if (mes.equals("December") || mes.equals("Dec"))
    {
        valorMes = 12;
    }

    return valorMes;
}

char *inverterString(char *base)
{
    char *invertida = "";

    for (int indice = strlen(base) - 1; indice >= 0; indice--)
    {
        invertida += base[indice];
    }

    return invertida;
}

int ehLetra(char caractere)
{
    return (caractere >= 'a' && caractere <= 'z') || (caractere >= 'A' && caractere <= 'Z');
}

int main()
{
    return 0;
}
