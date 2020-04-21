#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define bool short
#define true 1
#define false 0
#define NUMENTRADA 1000
#define TAMLINHA 1000

/* Nome: Maria Carolina Resende Jaudacy - Matrícula: 667477 
 * 2º período do curso de Ciência da Computação PUC MINAS - 2/2019 */

bool isPalindromo(char *palavra)
{
    bool palindromo = true;
    int posicaoFinal = strlen(palavra) - 2;

    for (int posicaoInicial = 0; posicaoInicial < strlen(palavra) / 2; posicaoInicial++)
    {
        if (palavra[posicaoInicial] == palavra[posicaoFinal])
        {
            posicaoFinal--;
        }
        else
        {
            palindromo = false;
            posicaoInicial = strlen(palavra) / 2;
        }
    }

    return palindromo;
}

bool isFim(char *s)
{
    return (strlen(s) >= 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int main()
{
    int posicaoUltimaPalavraLida = 0;
    char entradas[NUMENTRADA][TAMLINHA];

    // Realiza leitura das entradas
    do
    {
        fgets(entradas[posicaoUltimaPalavraLida], TAMLINHA, stdin);
    } while (isFim(entradas[posicaoUltimaPalavraLida++]) == false);

    // Desconsidera o índice da entrada com a flag FIM
    posicaoUltimaPalavraLida--;

    for (int indicePalavra = 0; indicePalavra < posicaoUltimaPalavraLida; indicePalavra++)
    {
        if (isPalindromo(entradas[indicePalavra]))
            printf("SIM\n");
        else
            printf("NAO\n");
    }

    return 0;
}