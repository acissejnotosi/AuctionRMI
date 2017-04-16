/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leilaoversao2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Jessica
 */
public class LeilaoVersao2 {

    static ArrayList<Processo> processList = new ArrayList<>();
    static List<Controle> procesosInteresados = new ArrayList<>();
    static List<String> produtosLancados = new ArrayList<>();
    static List<Produto> listaProdutos = new ArrayList<>();
    static List<Produto> listaProdutosLeiloando = new ArrayList<>();
    static List<Processo> listaProcessosLeiloeros = new ArrayList<>();
    static Map<String, Autenticacao> assinatura = new HashMap<String, Autenticacao>();
    static PublicKey mychavePublica = null;
    static PrivateKey myChavePrivada = null;
    static boolean lance = false;
    static char tipo;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException {

        //******************************************
        // Declarações e inicializações
        int PORT_MULTICAST = 6789;
        String IP_MULTICAST = "228.5.6.7";
        MulticastSocket s = null;
        DatagramSocket socket = null;
        Processo process = null;
        Produto product = null;
        Chaves gera_chave = null;
        PrivateKey chave_privada = null;
        String nomeProcesso = " ";
        String port = " ";
        String nomeProduto = " ";
        String idProduto = " ";
        String descProduto = " ";
        String precoProduto = " ";
        String tempoLeilao = " ";
        Scanner in = new Scanner(System.in);
        gera_chave = new Chaves();

        //********************************************
        // Insere o processo no grupo Multicast
        InetAddress group = InetAddress.getByName(IP_MULTICAST);
        s = new MulticastSocket(PORT_MULTICAST);
        s.joinGroup(group);
        socket = new DatagramSocket();

        //**********************************************
        //Informa o nome e o port do processo
        System.out.println("Informe o NOME do participante:");
        nomeProcesso = in.nextLine();
        clearConsole();

        System.out.println("Informe a PORTA para comunicação UNICAST:");
        port = in.nextLine();
        clearConsole();

        // ********************************************
        // Gera as chaves pra este processo.
        gera_chave.geraChave();
        myChavePrivada = gera_chave.getChavePrivada();                          //Chave Privada
        mychavePublica = gera_chave.getChavePublica();                          //Chave Pública

        //*********************************************
        //Cria um novo processo.
        process = new Processo(nomeProcesso, port, mychavePublica, listaProdutos, listaProdutosLeiloando);

        //*********************************************
        //Adiciona o processo a lista de processos.
        LeilaoVersao2.processList.add(process);

        //  Controle controle = new Controle(idProduto, precoProduto);
        //procesosInteresados.add(controle);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        //*********************************************
        //Tipo N refere-se a dados enviados sobre este processo.
        oos.writeChar('N');
        oos.writeUTF(nomeProcesso);
        oos.writeUTF(port);
        oos.writeObject(mychavePublica);
        oos.writeObject(process.getListaProduto());
        oos.writeObject(process.getListaProdutosLeiloando());
        oos.flush();

        // *********************************************
        // Inicialização da comunicação Multicast
        ServidorMultiCast multCastComm = new ServidorMultiCast(process, IP_MULTICAST, PORT_MULTICAST);
        multCastComm.start();

        System.out.println("Porta é: " + port);
        //**********************************************
        //Inicialização da comunicação Unicast
        ServidorUniCast uniCastComm = new ServidorUniCast(process, IP_MULTICAST, PORT_MULTICAST);
        uniCastComm.start();

        // *********************************************
        // Enviando através do multicast as informações sobre o processo
        byte[] m = bos.toByteArray();
        DatagramPacket messageOut = new DatagramPacket(m, m.length, group, PORT_MULTICAST);

        //***********************************************
        //Verificação se a mensagem está sendo enviada.
        System.out.println("\n[MULTICAST enviando] Enviando a informação sobre este novo processo:");
        System.out.print("[MULTICAST enviando]");
        System.out.print(" ID do participante: " + nomeProcesso);
        System.out.print(", Porta: " + port);
        System.out.print(", Chave publica: - ");
        s.send(messageOut);

        // *********************************************
        // Fase de interação do processo sobre o sistema.
        while (true) {
            String cmd;

            System.out.println("MENU");
            System.out.println("Pressione a tecla desejada:");
            System.out.println("[1] Dar um lance em um produto ");
            System.out.println("[2] Lista os processos ");
            System.out.println("[3] Cadastrar um novo produto na sua lista de produtos");
            System.out.println("[4] Leiloar um produto ");
            System.out.println("[5] Lista dos leilões que estão ocorrendo ");
            System.out.println("[6] Listar seus produtos");
            System.out.println("[7] Sair");
            cmd = in.nextLine().trim().toUpperCase();
            System.out.println("");

            switch (cmd) {

                case "1": ///B

                    String nomeProRecebeLance;
                    String nomeProdRecebeLance;
                    String lance;

                    // *******************************************
                    //Verifica quantidade de processos ativos
                    if (processList.size() < 2) {
                        System.out.println("É necessário pelo menos 3 processos ativos para continuar!");
                        break;
                    }

                    System.out.println("NOME DOS PROCESSOS LEILOEIROS:");
                    nomeProcessosLeiloeiros();

                    System.out.println("Digite o NOME de um dos processos leiloeiros para dar lance em 1 produto:");
                    nomeProRecebeLance = in.nextLine();

                    if (verificaSeExisteProesso(nomeProRecebeLance)) {
                        System.out.println("Processo para dar lance selecionado com sucesso!");
                        System.out.println("Agora selecione o PRODUTO desejado:");
                        mostraProdutosLeiloandoDesseProcesso(nomeProRecebeLance);
                        nomeProdRecebeLance = in.nextLine();
                        if (verificaProdutoListaLeiloando(nomeProRecebeLance, nomeProdRecebeLance)) {
                            System.out.println("Digite o valor de lance desejado:");
                            lance = in.nextLine();

                            System.out.println("Seu lance foi registrado");

                            //Autenticação unicast processo leiloador
                        } else {
                            System.out.println("Esse produto não está em leilão!");
                            break;
                        }

                    } else {
                        System.out.println("Esse processo não está leiloando");
                        break;
                    }

                    break;
                case "2":
                    System.out.println("LISTA DE PROCESSOS ATIVOS:");
                    iterarSobreListaProcessos();
                    break;
                case "3":

                    System.out.println("CADASTRO DE UM NOVO PRODUTO");
                    System.out.println("Informe o NOME do produto:");
                    nomeProduto = in.nextLine();
                    clearConsole();

                    System.out.println("CADASTRO DE UM NOVO PRODUTO");
                    System.out.println("Informe o ID do produto:");
                    idProduto = in.nextLine();
                    clearConsole();

                    System.out.println("CADASTRO DE UM NOVO PRODUTO");
                    System.out.println("Informe DESCRIÇÃO do produto:");
                    descProduto = in.nextLine();
                    clearConsole();

                    System.out.println("CADASTRO DE UM NOVO PRODUTO");
                    System.out.println("Informe o PREÇO do produto:");
                    precoProduto = in.nextLine();
                    clearConsole();

                    System.out.println("CADASTRO DE UM NOVO PRODUTO");
                    System.out.println("Informe o TEMPO de LEILAO do produto:");
                    tempoLeilao = in.nextLine();
                    clearConsole();

                    //*********************************************
                    //Cria um novo produto.
                    product = new Produto(idProduto, nomeProduto, descProduto, precoProduto, tempoLeilao, nomeProcesso);

                    //*********************************************
                    //Adiciona o produto a lista de produtos.
                    process.getListaProduto().add(product);

                    break;
                case "4":
                    if (process.getListaProduto().isEmpty()) {
                        System.out.println("SUA LISTA DE PRODUTOS ESTÁ VAZIA!");
                        System.out.println("CADASTRO DE UM NOVO PRODUTO");
                        System.out.println("Informe o NOME do produto:");
                        nomeProduto = in.nextLine();
                        clearConsole();

                        System.out.println("CADASTRO DE UM NOVO PRODUTO");
                        System.out.println("Informe o ID do produto:");
                        idProduto = in.nextLine();
                        clearConsole();

                        System.out.println("CADASTRO DE UM NOVO PRODUTO");
                        System.out.println("Informe DESCRIÇÃO do produto:");
                        descProduto = in.nextLine();
                        clearConsole();

                        System.out.println("CADASTRO DE UM NOVO PRODUTO");
                        System.out.println("Informe o PREÇO do produto:");
                        precoProduto = in.nextLine();
                        clearConsole();

                        System.out.println("CADASTRO DE UM NOVO PRODUTO");
                        System.out.println("Informe o TEMPO de LEILAO do produto:");
                        tempoLeilao = in.nextLine();
                        clearConsole();

                        //*********************************************
                        //Cria um novo produto.
                        product = new Produto(idProduto, nomeProduto, descProduto, precoProduto, tempoLeilao, nomeProcesso);

                        //*********************************************
                        //Adiciona o produto a lista de produtos.
                        process.getListaProduto().add(product);
                    } else {
                        String produtoDesejado;

                        System.out.println("SUA LISTA DE PRODUTOS:");
                        iterarSobreListaProdutos(process);
                        System.out.println("Digite um nome de produto da sua lista para leiloar:");
                        produtoDesejado = in.nextLine();

                        if (verificaSeoProdutoExisteNaLista(produtoDesejado, process)) {
                            System.out.println("Produto" + produtoDesejado + "selecionado com sucesso!");

                            // process
                            // caracteristícas
                            //listaprodutosleiloando         
                            //Envio dados para multicast 
                        } else {
                            System.out.println("Esse produto não existe na lista, para cadastrá-lo utilize a opção 3 do menu.");
                            break;
                        }

                        for (Produto p : process.getListaProduto()) {
                            if (p.getName().equals(produtoDesejado)) {
                                process.getListaProdutosLeiloando().add(p);
                                listaProcessosLeiloeros.add(process);
                            }
                        }
                    }
                    break;
                case "5":

                    mostraLeiloesOcorrendo();

                    break;

                case "6":

                    System.out.println("- seus PRODUTOS:");
                    iterarSobreListaProdutos(process);

                    System.out.println("");
                    System.out.println("- seus PRODUTOS em LEILÃO:");

                    mostraProdutosLeiloandoDesseProcesso(process.getId());

                    break;

                case "7":
                    //**********************************************
                    //Sai do programa
                    System.out.println("Estou saindo!");
                    s.leaveGroup(group);
                    s.close();
                    System.exit(0);

                    break;
            }

        }
    }

    public static void iterarSobreListaProcessos() {

        for (Processo p : processList) {
            System.out.println(" - processo: " + p.getId());
        }
    }

    public static void iterarSobreListaProdutos(Processo process) {

        if (process.getListaProduto().isEmpty()) {
            System.out.println("...está vazia...");
        } else {
            for (Produto p : process.getListaProduto()) {
                System.out.println(" - produto: " + p.getName());
            }
        }
    }

    public static boolean verificaSeoProdutoExisteNaLista(String produtoDesejado, Processo process) {

        for (Produto p : process.getListaProduto()) {
            if (produtoDesejado.equals(p.getName())) {
                return true;
            }
        }
        return false;
    }

    public final static void clearConsole() {

        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {

                Runtime.getRuntime().exec("cls");

            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Tratar Exceptions
        }
    }

    public static void mostraLeiloesOcorrendo() {

        if (listaProcessosLeiloeros.isEmpty()) {
            System.out.println("Não há processos leiloando produtos!");
        } else {
            for (Processo pro : listaProcessosLeiloeros) {

                System.out.println("PROCESSO" + pro.getId() + " está vendendo o(s) seguintes produto(s): ");

                for (int n = 0; n < pro.getListaProdutosLeiloando().size(); n++) {

                    System.out.print(pro.getListaProdutosLeiloando().get(n).getName() + ", ");
                }
            }
        }
    }

    public static void nomeProcessosLeiloeiros() {
        for (Processo pro : listaProcessosLeiloeros) {
            System.out.print(pro.getId() + ", ");
        }
    }

    public static boolean verificaSeExisteProesso(String processo) {
        for (Processo pro : listaProcessosLeiloeros) {
            if (pro.getId().equals(processo)) {

                return true;
            }
        }
        return false;
    }

    public static void mostraProdutosLeiloandoDesseProcesso(String processo) {
        if (listaProcessosLeiloeros.isEmpty()) {
            System.out.println("...está vazia...");
        } else {

            for (Processo pro : listaProcessosLeiloeros) {
                if (pro.getId().equals(processo)) {
                    for (Produto prod : pro.getListaProdutosLeiloando()) {
                        System.out.print(prod.getName() + ", ");

                    }
                    System.out.print(".");
                }
            }
        }
    }

    public static boolean verificaProdutoListaLeiloando(String processo, String produto) {
        for (Processo pro : listaProcessosLeiloeros) {
            if (pro.getId().equals(processo)) {
                for (Produto prod : pro.getListaProdutosLeiloando()) {
                    if (prod.getName().equals(produto)) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

}
