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
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server implements Hello {

    public Server() {}

    @Override
    public String contarLetras(String texto) {
        int vogais = 0, consoantes = 0;
        texto = texto.toLowerCase().replaceAll("[^a-z]", "");
        for (char c : texto.toCharArray()) {
            if ("aeiou".indexOf(c) >= 0)
                vogais++;
            else
                consoantes++;
        }
        return "Vogais: " + vogais + ", Consoantes: " + consoantes;
    }

    @Override
    public boolean isPalindromo(String texto) {
        texto = texto.replaceAll("[^a-zA-Z]", "").toLowerCase();
        return texto.equals(new StringBuilder(texto).reverse().toString());
    }

    @Override
    public String gerarSenhaSegura(int tamanho) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";
        StringBuilder senha = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < tamanho; i++) {
            senha.append(caracteres.charAt(rand.nextInt(caracteres.length())));
        }
        return senha.toString();
    }

    @Override
    public String converterParaBinario(String texto) {
        StringBuilder binario = new StringBuilder();
        for (char c : texto.toCharArray()) {
            binario.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0')).append(" ");
        }
        return binario.toString().trim();
    }

    @Override
public String frequenciaPalavras(String texto) {
    texto = texto.toLowerCase().replaceAll("[^a-zA-Z ]", "");
    String[] palavras = texto.split("\\s+"); // corrigido também o regex
    Map<String, Integer> frequencia = new HashMap<>();
    for (String palavra : palavras) {
        if (!palavra.isEmpty()) {
            frequencia.put(palavra, frequencia.getOrDefault(palavra, 0) + 1);
        }
    }
    StringBuilder resultado = new StringBuilder();
    for (Map.Entry<String, Integer> entry : frequencia.entrySet()) {
        resultado.append("\"").append(entry.getKey()).append("\": ").append(entry.getValue()).append(", ");
    }
    return resultado.length() > 0 ? resultado.substring(0, resultado.length() - 2) : "Nenhuma palavra.";
}


    public static void main(String[] args) {
        try {
            Server obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Hello", stub);
            System.out.println("Servidor pronto!");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e);
            e.printStackTrace();
        }
    }
}
