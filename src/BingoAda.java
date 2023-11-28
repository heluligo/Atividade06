//34567 | 2345678 | 2345678 | 2345678 | 2345678 | 2345678 | 2345678 | 2345678 | 2345678 |
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BingoAda {
    static int  ctd = 5;
    static int[][] marcacao;
    static int numDeParticipantes;
    static int[][] cartelas; //= new int[numDeParticipantes][5];
    static int qtdBolas;
    static int[] numDaBola;// = new int[qtdBolas];
    //static String nome;
    static String[] nomesDosParticipantes;

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        Random numeros = new Random();
        int[] sorteio = new int[5];

        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("*            B I N G O   T e c h   5 0 +            *");
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");

        //Entra com os nome dos participantes como string e os armazena em um array
        System.out.println("Informe os nomes das pessoas irão participar dessa rodada. \n" +
                "Utilize o hifem \"-\" como separador e não use espaços. Ex: " +
                "jogador01-jogador02-jogador03-...-jogadorNN ");
        String nome = scn.next();
        nomesDosParticipantes = nome.split("-");

        //Conta e exibe qtd de participantes e mostra seus nomes em um array
        int numDeParticipantes = nomesDosParticipantes.length;
        System.out.println("Nesta rodada temos " + numDeParticipantes + " participantes.");
        System.out.print("Os participantes desta rodada são: ");
        System.out.println(Arrays.toString(nomesDosParticipantes));

        //Gera aleatoriamente os cinco múmeros de cada cartela
        cartelas = new int[numDeParticipantes][5];
        int numeroTmp = 0;
        for (int i = 0; i < numDeParticipantes; i++) {
            for (int j = 0; j < 5; j++) {
                cartelas[i][j] = numeroTmp = numeros.nextInt(60) + 1;

                //Verifica a duplicidade dos números
                for (int l = 0; l < numDeParticipantes; l++) {
                    for (int c = 0; c < 5; c++) {
                        if (numeroTmp == cartelas[l][c] && c != j) {
                            numeroTmp = numeros.nextInt(60) + 1;
                        } else {
                            cartelas[i][j] = numeroTmp;
                        }
                    }
                }
            }
            //Ordena o array de forma crescente por linha
            for (int k = 0; k < cartelas.length; k++) {
            Arrays.sort(cartelas[k]);
            }
        }
        //Gera as cartelas de marcação
        marcacao = new int[numDeParticipantes][5];
        for (int i = 0; i < numDeParticipantes; i++) {
            for (int j = 0; j < 5; j++) {
                marcacao[i][j] = 0;
            }
        }
        //Exibe as cartelas com seus números (Não manter no código)
        //System.out.println("As cartelas são: \n" + Arrays.deepToString(cartelas));

        //Informa os nomes dos participantes e as cartelas de cada um
        for (int i = 0; i < nomesDosParticipantes.length; i++) {
            System.out.println(nomesDosParticipantes[i] + ", seus numeros da sorte são: ");
            System.out.print("[");
            for (int j = 0; j < 5; j++) {
                System.out.print("\t" + cartelas[i][j]);
            }
            System.out.println("\t]");
        }
        //Define e mistura as bolas no globo
        System.out.println("\nQuantas bolas terão no globo? Use múltiplo de 5");
        int qtdBolas = scn.nextInt();
        numDaBola = new int[qtdBolas]; //define o tamanho do vetor
        for (int i = 0; i < qtdBolas;) {
            numDaBola[i] = numeros.nextInt(qtdBolas) + 1; //sorteia um número na faixa definida excluindo o zero
            if(!verificarDuplicidade(qtdBolas, numDaBola, numeros, i)){
                i++;
            }
        }
        //System.out.println("As bolas para sorteio são: \n" + Arrays.toString(numDaBola));

        //Realiza o 1º sorteio de 5 bolas
        System.out.println("Vamos começar o sorteio? \n" +
                "Presssione qualquer tecla seguida de ENTER para a 1ª rodada");
        String opc = scn.next();
        for (int i = 0; i < 5; i++){
            sorteio[i] = numDaBola[i];
        }
        //Marca os acertos na cartela de marcaçao
        gerarCartelaDeMarcacao(cartelas, marcacao, sorteio);

        //Conta qtos acertos cada jogador teve e verifica se alguém fez Bingo
        analisaSorteio(nomesDosParticipantes, marcacao, sorteio);

        while (ctd < numDaBola.length ){
            fazerNovoSorteio(scn, numDaBola, sorteio);
        }
        System.exit(0);
    }
    private static boolean verificarDuplicidade(int qtdBolas, int[] numDaBola, Random numeros, int i) {
        for (int j = 0; j < qtdBolas; j++) {
            if (numDaBola[i] == numDaBola[j] && i != j) {
                return true;
            }
        }
        return false;
    }
    private static void gerarCartelaDeMarcacao(int[][] cartelas, int[][] marcacao, int[] sorteio) {
        for (int i = 0; i < cartelas.length; i++){
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if (cartelas[i][k] == sorteio[j]) {
                        marcacao[i][k] = 1;
                    }
                }
            }
        }
    }
    private static void analisaSorteio(String[] nomesDosParticipantes, int[][] marcacao, int[] sorteio) {
        int soma;
        System.out.println("======================================");
        System.out.println("Números sorteados = " + Arrays.toString(sorteio));
        //System.out.println("Marcação da cartela = " + Arrays.deepToString(marcacao));

        //Conta qtos acertos cada jogador teve e verifica se alguém fez Bingo
        System.out.println("--------------------------------------");
        for (int i = 0; i < nomesDosParticipantes.length; i++) {
            soma = 0;
            for (int col = 0; col < 5; col++){
                soma = marcacao[i][col] + soma;
                if (soma >= 5){
                    System.out.println("BINGO!!!    BINGO!!!    BINGO!!!    BINGO!!!    ");
                    System.out.println("PARBÉNS " + nomesDosParticipantes[i] + " você é o(a) grande vencedor(a)!");
                    System.out.println("======================================");
                    System.exit(0);
                }
            }
            System.out.println(nomesDosParticipantes[i] + " acertou " + soma);
        }
        System.out.println("======================================");
    }
//  Helder-Maria-Luís-Denise-Lúcia

    public static void fazerNovoSorteio(Scanner scn, int[] bolas, int[] sorteio) {
        String opc2;
        System.out.print("Pressione qualquer tecla para sortear nova rodada ou \"P\" parar.");
        opc2 = scn.next();
        if (opc2.equals("p")){
            System.exit(0);
        }
        for (int x = ctd ; x < ctd + 4;) { //5 - 10 - 15
            for (int y = 0; y < 5; y++) {
                sorteio[y] = numDaBola[x];
                x++;
            }
            if(x >= numDaBola.length) {
                return;
            }
            ctd = x;
            gerarCartelaDeMarcacao(cartelas, marcacao, sorteio);
            analisaSorteio(nomesDosParticipantes, marcacao, sorteio);
            return;
        }

    }
}
