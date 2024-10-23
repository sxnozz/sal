package seginfo.gerenciadorSenhas;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("1) Cadastrar usuário");
            System.out.println("2) Autenticar usuário");
            System.out.println("3) Encerrar");
            System.out.print("Escolha uma opção: ");
            int escolha = teclado.nextInt();
            teclado.nextLine();

            if (escolha == 1) {
                System.out.print("Digite o nome do usuário: ");
                String nome = teclado.nextLine();
                System.out.print("Digite o email do usuário: ");
                String email = teclado.nextLine();
                System.out.print("Digite a senha do usuário: ");
                String senha = teclado.nextLine();

                if (GerenciadorSenhas.cadastrar(nome, email, senha)) {
                    System.out.println("Usuário cadastrado com sucesso!");
                } else {
                    System.out.println("Erro ao cadastrar usuário. Email já existe ou houve um erro.");
                }
            } else if (escolha == 2) {
                System.out.print("Digite o email do usuário: ");
                String email = teclado.nextLine();
                System.out.print("Digite a senha do usuário: ");
                String senha = teclado.nextLine();

                if (GerenciadorSenhas.autenticar(email, senha)) {
                    System.out.println("Autenticação realizada com sucesso!");
                } else {
                    System.out.println("Autenticação falhou.");
                }
            } else {
                break;
            }
        }
        System.out.println("Ok, tenha um bom dia!");
        teclado.close();
    }
}