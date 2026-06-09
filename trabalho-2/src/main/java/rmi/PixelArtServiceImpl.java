package rmi;

import model.Coordenada;
import model.Mural;
import model.Pixel;
import model.ProjetoPixelArt;
import model.Usuario;
import protocol.RemoteObjectRef;
import tools.Artista;
import tools.Ferramenta;
import util.JsonUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PixelArtServiceImpl extends UnicastRemoteObject implements PixelArtService {
    private static final long serialVersionUID = 1L;

    private ProjetoPixelArt projeto;

    public PixelArtServiceImpl() throws RemoteException {
        super();
        this.projeto = new ProjetoPixelArt(new Artista("Servidor"), new Mural(10, 10));
    }

    @Override
    public synchronized String criarMural(int largura, int altura, Usuario usuario) throws RemoteException {
        this.projeto = new ProjetoPixelArt(usuario, new Mural(largura, altura));
        return JsonUtil.resposta(true, "Mural criado por " + usuario.getNome() + " com tamanho " + largura + "x" + altura);
    }

    @Override
    public synchronized String pintarPixel(int x, int y, String cor) throws RemoteException {
        projeto.getMural().pintarPixel(x, y, cor);
        return JsonUtil.resposta(true, "Pixel pintado em (" + x + ", " + y + ") com a cor " + cor);
    }

    @Override
    public synchronized String apagarPixel(int x, int y) throws RemoteException {
        projeto.getMural().pintarPixel(x, y, "BRANCO");
        return JsonUtil.resposta(true, "Pixel apagado em (" + x + ", " + y + ")");
    }

    @Override
    public synchronized List<Pixel> listarPixels() throws RemoteException {
        return projeto.getMural().listarPixelsColoridos();
    }

    @Override
    public synchronized String aplicarFerramenta(Ferramenta ferramenta, Coordenada coordenada) throws RemoteException {
        ferramenta.aplicar(projeto.getMural(), coordenada);
        return JsonUtil.resposta(true, "Ferramenta " + ferramenta.getNome() + " aplicada em (" + coordenada.getX() + ", " + coordenada.getY() + ")");
    }

    @Override
    public synchronized String executarOperacao(RemoteObjectRef objeto, String methodId, String argumentsJson) throws RemoteException {
        if (!"PixelArtService".equals(objeto.getObjectReference())) {
            return JsonUtil.resposta(false, "Objeto remoto desconhecido: " + objeto.getObjectReference());
        }
        try {
            switch (methodId) {
                case "criarMural":
                    return criarMural(
                            JsonUtil.getInt(argumentsJson, "largura"),
                            JsonUtil.getInt(argumentsJson, "altura"),
                            new Artista(JsonUtil.getString(argumentsJson, "usuario"))
                    );
                case "pintarPixel":
                    return pintarPixel(
                            JsonUtil.getInt(argumentsJson, "x"),
                            JsonUtil.getInt(argumentsJson, "y"),
                            JsonUtil.getString(argumentsJson, "cor")
                    );
                case "apagarPixel":
                    return apagarPixel(
                            JsonUtil.getInt(argumentsJson, "x"),
                            JsonUtil.getInt(argumentsJson, "y")
                    );
                case "listarPixels":
                    return JsonUtil.pixelsToJson(listarPixels());
                case "visualizarMural":
                    return visualizarMural();
                default:
                    return JsonUtil.resposta(false, "Metodo remoto desconhecido: " + methodId);
            }
        } catch (Exception e) {
            return JsonUtil.resposta(false, e.getMessage());
        }
    }

    @Override
    public synchronized String visualizarMural() throws RemoteException {
        Mural mural = projeto.getMural();
        StringBuilder sb = new StringBuilder();
        sb.append("Dono: ").append(projeto.getDono().getNome()).append("\n");
        for (int y = 0; y < mural.getAltura(); y++) {
            for (int x = 0; x < mural.getLargura(); x++) {
                String cor = mural.getPixel(x, y).getCor().toUpperCase();
                sb.append(simboloCor(cor)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String simboloCor(String cor) {
        switch (cor.toUpperCase()) {
            case "VERMELHO": return "\u001B[41m  \u001B[0m";
            case "AZUL":     return "\u001B[44m  \u001B[0m";
            case "VERDE":    return "\u001B[42m  \u001B[0m";
            case "AMARELO":  return "\u001B[43m  \u001B[0m";
            case "PRETO":    return "\u001B[40m  \u001B[0m";
            case "BRANCO":   return "\u001B[47m  \u001B[0m";
            case "ROSA":     return "\u001B[45m  \u001B[0m";
            case "MARROM":   return "\u001B[48;5;94m  \u001B[0m";
            case "LARANJA":  return "\u001B[48;5;208m  \u001B[0m";
            default:         return " .";
        }
    }
}
