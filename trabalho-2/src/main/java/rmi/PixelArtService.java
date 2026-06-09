package rmi;

import model.Coordenada;
import model.Pixel;
import model.Usuario;
import protocol.RemoteObjectRef;
import tools.Ferramenta;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PixelArtService extends Remote {
    String criarMural(int largura, int altura, Usuario usuario) throws RemoteException;
    String pintarPixel(int x, int y, String cor) throws RemoteException;
    String apagarPixel(int x, int y) throws RemoteException;
    List<Pixel> listarPixels() throws RemoteException;
    String aplicarFerramenta(Ferramenta ferramenta, Coordenada coordenada) throws RemoteException;
    String executarOperacao(RemoteObjectRef objeto, String methodId, String argumentsJson) throws RemoteException;
    String visualizarMural() throws RemoteException;
}
