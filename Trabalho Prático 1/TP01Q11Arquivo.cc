#include <stdlib.h>
#include <stdio.h>
#include <string.h>

void escreverArquivo(char *nomeArquivo)
{
    FILE *arquivo = fopen(nomeArquivo, "w");

    int quantidadeValoresAGravar;
    scanf("%d", &quantidadeValoresAGravar);

    // Realiza leitura das entradas e as grava em linhas separadas no arquivo
    for (int indice = 0; indice < quantidadeValoresAGravar; indice++)
    {
        double valorAGravar;
        scanf("%lf", &valorAGravar);
        fprintf(arquivo, "%lf\n", valorAGravar);
    }

    // fecha o arquivo
    fclose(arquivo);
}

void lerArquivo(char *caminhoArquivo)
{
    FILE *arquivo = fopen(caminhoArquivo, "r");

    if (arquivo == NULL)
    {
        printf("\nArquivo inexistente.");
    }
    else
    {
        // inicializa posição do leitor na primeira posição do arquivo
        fseek(arquivo, 0, SEEK_SET);

        // chama método para imprimir recursivamente os valores a partir da posição 0
        imprimirValor(arquivo);
    }

    // fecha o arquivo
    fclose(arquivo);
}

void imprimirValor(FILE *arquivo)
{
    double valor;
    fscanf(arquivo, "%lf", &valor);

    // enquanto não for atingida a ultima posição do arquivo, continua escrevendo
    // recursivamente o próximo valor
    if (valor != EOF)
    {
        fseek(arquivo, sizeof(double), SEEK_CUR);
        imprimirValor(arquivo);
        printf("%lf\n", valor);
    }
}

int main()
{
    char *caminhoNovoArquivo = "./Arquivo.txt";
    // lê e grava o arquivo
    escreverArquivo(caminhoNovoArquivo);
    // escreve conteudo na tela
    lerArquivo(caminhoNovoArquivo);
    return 0;
}