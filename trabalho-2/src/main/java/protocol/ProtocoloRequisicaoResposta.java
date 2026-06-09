package protocol;

import rmi.PixelArtService;
import java.rmi.RemoteException;

public class ProtocoloRequisicaoResposta {
    private PixelArtService service;

    public ProtocoloRequisicaoResposta(PixelArtService service) {
        this.service = service;
    }

    // Equivalente ao doOperation do livro: envia requisicao ao objeto remoto e recebe resposta.
    public String doOperation(RemoteObjectRef o, String methodId, String argumentsJson) throws RemoteException {
        return service.executarOperacao(o, methodId, argumentsJson);
    }
}
