package resource;

import com.sun.net.httpserver.HttpExchange;
import util.HttpResponder;
import util.JsonUtil;
import util.PixelArtService;

import java.io.IOException;

public class FilaResource {
    private final PixelArtService service;

    public FilaResource(PixelArtService service) { this.service = service; }

    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            HttpResponder.enviarJson(exchange, 200, service.statusFilaJson());
        } else {
            HttpResponder.enviarJson(exchange, 405, JsonUtil.erro("Metodo nao permitido"));
        }
    }
}
