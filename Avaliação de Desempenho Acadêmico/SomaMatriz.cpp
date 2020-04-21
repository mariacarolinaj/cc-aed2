#include <stdio.h>
#include <stdlib.h>

int main(){
    int linhas, colunas;

    scanf("%i", &linhas);
    scanf("%i", &colunas);

    int matriz[linhas][colunas];

    for(int l = 0; l < linhas; l++) {
        for (int c = 0; c < colunas; c++) {
            scanf("%i", &matriz[l][c]);
        }
    }

    int soma = 0;

    for(int l = 0; l < linhas; l++) {
        for (int c = 0; c < colunas; c++) {
            soma += matriz[l][c];
        }
    }

    printf("%i", soma);

    return 0;
}
