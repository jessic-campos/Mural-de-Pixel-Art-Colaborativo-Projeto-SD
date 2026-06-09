package worker;

import util.PixelArtService;

public class WorkerFila {
    public static void main(String[] args) throws Exception {
        PixelArtService service = new PixelArtService();
        System.out.println("WorkerFila iniciado.");
        System.out.println("Consumindo mensagens da fila persistente e atualizando o mural.");
        System.out.println("Desligue este worker, envie acoes pelos clientes e ligue-o novamente para demonstrar desacoplamento temporal.\n");

        while (true) {
            boolean processou = service.processarProximoEvento();
            Thread.sleep(processou ? 500 : 1500);
        }
    }
}
