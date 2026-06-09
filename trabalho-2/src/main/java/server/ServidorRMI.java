package server;

import rmi.PixelArtService;
import rmi.PixelArtServiceImpl;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorRMI {
    public static void main(String[] args) {
        try {
            PixelArtService service = new PixelArtServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("PixelArtService", service);
            System.out.println("Servidor RMI iniciado na porta 1099.");
            System.out.println("Objeto remoto registrado como PixelArtService.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
