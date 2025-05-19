/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * -Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
 *
 * Neither the name of Oracle nor the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */
package example.hello;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Hello stub = (Hello) registry.lookup("Hello");

            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("\n--- MENU ---");
                System.out.println("1 - Contar vogais e consoantes");
                System.out.println("2 - Verificar se é palíndromo");
                System.out.println("3 - Gerar senha segura");
                System.out.println("4 - Converter texto para binário");
                System.out.println("5 - Frequência de palavras");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // limpa buffer

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o texto: ");
                        String texto1 = scanner.nextLine();
                        System.out.println(stub.contarLetras(texto1));
                        break;
                    case 2:
                        System.out.print("Digite a string: ");
                        String texto2 = scanner.nextLine();
                        System.out.println("É palíndromo? " + stub.isPalindromo(texto2));
                        break;
                    case 3:
                        System.out.print("Digite o tamanho da senha: ");
                        int tam = scanner.nextInt();
                        System.out.println("Senha gerada: " + stub.gerarSenhaSegura(tam));
                        break;
                    case 4:
                        System.out.print("Digite o texto para converter em binário: ");
                        String textoBin = scanner.nextLine();
                        System.out.println("Texto em binário: " + stub.converterParaBinario(textoBin));
                        break;
                    case 5:
                        System.out.print("Digite o texto: ");
                        String texto5 = scanner.nextLine();
                        System.out.println("Frequência: " + stub.frequenciaPalavras(texto5));
                        break;
                    case 0:
                        System.out.println("Encerrando...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } while (opcao != 0);

            scanner.close();
        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e);
            e.printStackTrace();
        }
    }
}
