#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <String.h>

#define MAX_TAM 1000

using namespace std;

bool encontrouFIM(char* entrada){
    bool ehFim = false;
    if (strlen(entrada) >= 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M') {
        ehFim = true;
    }

    return ehFim;
}

int quantidadeMaiusculas(char* entrada) {
    int maiusculas = 0;
    for (int posicao = 0; posicao < strlen(entrada); posicao++) {
        if (entrada[posicao] >= 'A' && entrada[posicao] <= 'Z') {
            maiusculas++;
        }
    }
    return maiusculas;
}

int main(){
    char entradas[MAX_TAM][MAX_TAM];
    int quantidadeEntradas = 0;

    do {
        scanf("%s", &entradas[quantidadeEntradas]);
        quantidadeEntradas++;
    } while (!encontrouFIM(entradas[quantidadeEntradas - 1]));

    for (int i = 0; i < quantidadeEntradas - 1; i++) {
        printf("%i\n", quantidadeMaiusculas(entradas[i]));
    }

    return 0;
}
