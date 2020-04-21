#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

class No
{
public:
    int valor;
    No* esq;
    No* dir;

    No()
    {
        esq = NULL;
        dir = NULL;
    }

    No(int valor)
    {
        esq = NULL;
        dir = NULL;
        this->valor = valor;
    }

    void setValor(int valor)
    {
        this->valor = valor;
    }
};

class Arvore
{
public :
    No* raiz;

    Arvore() {}

    void mostrarPre()
    {
        mostrarPre(this->raiz);
    }

    void mostrarPre(No* no)
    {
        if (no != NULL)
        {
            printf("%i ", no->valor);
            mostrarPre(no->esq);
            mostrarPre(no->dir);
        }
    }
};

void inserirValor (int valor, No* noInsercao)
{
    if(noInsercao != NULL)
    {
        // caminha até o nó vazio ideal a partir do raiz
        printf("\nno atual: %i", noInsercao->valor);
        printf("\nprocurou");
        if (noInsercao->valor > valor)
        {
            inserirValor(valor, noInsercao->esq);
            printf("\nesq");
        }
        else
        {
             inserirValor(valor, noInsercao->dir);
            printf("\ndir");
        }
    } else {
        noInsercao = (No*)malloc(sizeof(No));
        noInsercao->setValor(valor);
        noInsercao->esq = NULL;
        noInsercao->dir = NULL;
        printf("\ninseriu");
    }
}

void lerArvore(int quantidadeElementos, Arvore* arvore)
{
    for (int i = 0; i < quantidadeElementos; i++)
    {
        int valor;
        scanf("%i", &valor);
        printf("\nleu valor %i", valor);
        inserirValor(valor, arvore->raiz);
    }
}

int main()
{
    Arvore* arvores[1000];
    int qtdElementosLinha, quantidadeArvores = 0;

    do
    {
        scanf("%i", &qtdElementosLinha);
        if (qtdElementosLinha > 0)
        {
            arvores[quantidadeArvores] = (Arvore*)malloc(sizeof(Arvore));
            lerArvore(qtdElementosLinha, arvores[quantidadeArvores]);
            quantidadeArvores++;
        }
    }
    while (qtdElementosLinha > 0);

    for (int i = 0; i < quantidadeArvores; i++)
    {
        printf("\nvai mostrar %i",i);
        arvores[i]->mostrarPre();
        printf("\n");
    }

    return 0;
}


