package client;

import model.Coordenada;
import rmi.PixelArtService;
import protocol.ProtocoloRequisicaoResposta;
import protocol.RemoteObjectRef;
import tools.Artista;
import tools.Borracha;
import tools.Pincel;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TesteAutomaticoRMI {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        PixelArtService service = (PixelArtService) registry.lookup("PixelArtService");

        System.out.println(service.criarMural(6, 6, new Artista("Victor")));
        System.out.println(service.pintarPixel(1, 1, "VERMELHO"));
        System.out.println(service.aplicarFerramenta(new Pincel("AZUL"), new Coordenada(2, 2)));
        System.out.println(service.aplicarFerramenta(new Borracha(), new Coordenada(1, 1)));
        System.out.println(service.visualizarMural());

        ProtocoloRequisicaoResposta protocolo = new ProtocoloRequisicaoResposta(service);
        System.out.println(protocolo.doOperation(
                new RemoteObjectRef("PixelArtService"),
                "pintarPixel",
                "{\"x\":3,\"y\":3,\"cor\":\"VERDE\"}"
        ));
        System.out.println(protocolo.doOperation(
                new RemoteObjectRef("PixelArtService"),
                "listarPixels",
                "{}"
        ));
    }
}
