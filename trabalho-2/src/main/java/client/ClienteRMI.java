package client;

import model.Coordenada;
import model.Pixel;
import model.Usuario;
import rmi.PixelArtService;
import protocol.ProtocoloRequisicaoResposta;
import protocol.RemoteObjectRef;
import tools.Artista;
import tools.Borracha;
import tools.Pincel;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class ClienteRMI {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            PixelArtService service = (PixelArtService) registry.lookup("PixelArtService");
            ProtocoloRequisicaoResposta protocolo = new ProtocoloRequisicaoResposta(service);
            RemoteObjectRef ref = new RemoteObjectRef("PixelArtService");

            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("\n=== Pixel Art RMI ===");
                System.out.println("1 - Criar mural");
                System.out.println("2 - Pintar pixel");
                System.out.println("3 - Apagar pixel");
                System.out.println("4 - Listar pixels coloridos");
                System.out.println("5 - Aplicar pincel por valor");
                System.out.println("6 - Aplicar borracha por valor");
                System.out.println("7 - Visualizar mural");
                System.out.println("8 - Chamada via protocolo request-reply JSON");
                System.out.println("0 - Sair");
                System.out.print("Opcao: ");
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.print("Nome do usuario: ");
                        String nome = scanner.nextLine();
                        System.out.print("Largura: ");
                        int largura = Integer.parseInt(scanner.nextLine());
                        System.out.print("Altura: ");
                        int altura = Integer.parseInt(scanner.nextLine());
                        System.out.println(service.criarMural(largura, altura, new Artista(nome)));
                        break;
                    case 2:
                        System.out.print("x: ");
                        int x = Integer.parseInt(scanner.nextLine());
                        System.out.print("y: ");
                        int y = Integer.parseInt(scanner.nextLine());
                        System.out.print("cor: ");
                        String cor = scanner.nextLine();
                        System.out.println(service.pintarPixel(x, y, cor));
                        break;
                    case 3:
                        System.out.print("x: ");
                        x = Integer.parseInt(scanner.nextLine());
                        System.out.print("y: ");
                        y = Integer.parseInt(scanner.nextLine());
                        System.out.println(service.apagarPixel(x, y));
                        break;
                    case 4:
                        List<Pixel> pixels = service.listarPixels();
                        for (Pixel p : pixels) {
                            System.out.println(p);
                        }
                        break;
                    case 5:
                        System.out.print("x: ");
                        x = Integer.parseInt(scanner.nextLine());
                        System.out.print("y: ");
                        y = Integer.parseInt(scanner.nextLine());
                        System.out.print("cor: ");
                        cor = scanner.nextLine();
                        System.out.println(service.aplicarFerramenta(new Pincel(cor), new Coordenada(x, y)));
                        break;
                    case 6:
                        System.out.print("x: ");
                        x = Integer.parseInt(scanner.nextLine());
                        System.out.print("y: ");
                        y = Integer.parseInt(scanner.nextLine());
                        System.out.println(service.aplicarFerramenta(new Borracha(), new Coordenada(x, y)));
                        break;
                    case 7:
                        System.out.println(service.visualizarMural());
                        break;
                    case 8:
                        System.out.println("Exemplo: pintarPixel via protocolo JSON");
                        String resposta = protocolo.doOperation(ref, "pintarPixel", "{\"x\":1,\"y\":1,\"cor\":\"VERMELHO\"}");
                        System.out.println(resposta);
                        break;
                    case 0:
                        System.out.println("Encerrando cliente.");
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                }
            } while (opcao != 0);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
