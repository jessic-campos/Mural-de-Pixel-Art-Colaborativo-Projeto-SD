import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

/**
 * Cliente Java para o Trabalho 4 – Pixel Art com Comunicação Indireta (Fila).
 * Compilar: javac -encoding UTF-8 ClienteJavaAPI.java
 * Executar:  java ClienteJavaAPI [host]   (padrão: localhost)
 */
public class ClienteJavaAPI {
    private static String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        if (args.length > 0) BASE_URL = "http://" + args[0] + ":8080";
        int opcao;
        do {
            menu();
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1 -> criarMural();
                case 2 -> pintarPixel();
                case 3 -> apagarPixel();
                case 4 -> listarPixels();
                case 5 -> aplicarPincel();
                case 6 -> aplicarBorracha();
                case 7 -> visualizarMural();
                case 8 -> statusFila();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opcao invalida");
            }
        } while (opcao != 0);
    }

    private static void menu() {
        System.out.println("\n=== Cliente Java - Pixel Art API (T4 - Fila) ===");
        System.out.println("1 - Criar mural");
        System.out.println("2 - Pintar pixel");
        System.out.println("3 - Apagar pixel");
        System.out.println("4 - Listar pixels");
        System.out.println("5 - Aplicar pincel");
        System.out.println("6 - Aplicar borracha");
        System.out.println("7 - Visualizar mural");
        System.out.println("8 - Status da fila");
        System.out.println("0 - Sair");
        System.out.print("Opcao: ");
    }

    private static void criarMural() throws Exception {
        System.out.print("Largura: "); int largura = Integer.parseInt(scanner.nextLine());
        System.out.print("Altura: ");  int altura  = Integer.parseInt(scanner.nextLine());
        System.out.print("Dono: ");    String dono = scanner.nextLine();
        System.out.println(post("/mural", "{\"largura\":" + largura + ",\"altura\":" + altura + ",\"dono\":\"" + dono + "\"}"));
    }

    private static void pintarPixel() throws Exception {
        int[] xy = lerCoordenada();
        System.out.print("Cor: "); String cor = scanner.nextLine();
        System.out.println(post("/pixel", "{\"x\":" + xy[0] + ",\"y\":" + xy[1] + ",\"cor\":\"" + cor + "\"}"));
    }

    private static void apagarPixel() throws Exception {
        int[] xy = lerCoordenada();
        System.out.println(delete("/pixel/" + xy[0] + "/" + xy[1]));
    }

    private static void listarPixels() throws Exception { System.out.println(get("/pixels")); }

    private static void aplicarPincel() throws Exception {
        System.out.print("x inicial: "); int x1 = Integer.parseInt(scanner.nextLine());
        System.out.print("y inicial: "); int y1 = Integer.parseInt(scanner.nextLine());
        System.out.print("x final: ");   int x2 = Integer.parseInt(scanner.nextLine());
        System.out.print("y final: ");   int y2 = Integer.parseInt(scanner.nextLine());
        System.out.print("Cor: ");       String cor = scanner.nextLine();
        System.out.println(post("/pincel", "{\"x1\":" + x1 + ",\"y1\":" + y1 + ",\"x2\":" + x2 + ",\"y2\":" + y2 + ",\"cor\":\"" + cor + "\"}"));
    }

    private static void aplicarBorracha() throws Exception {
        int[] xy = lerCoordenada();
        System.out.println(post("/borracha", "{\"x\":" + xy[0] + ",\"y\":" + xy[1] + "}"));
    }

    private static void visualizarMural() throws Exception {
        System.out.println(extrairMural(get("/mural?ansi=true")));
    }

    private static void statusFila() throws Exception { System.out.println(get("/fila/status")); }

    private static int[] lerCoordenada() {
        System.out.print("x: "); int x = Integer.parseInt(scanner.nextLine());
        System.out.print("y: "); int y = Integer.parseInt(scanner.nextLine());
        return new int[]{x, y};
    }

    private static String get(String path) throws Exception {
        return client.send(HttpRequest.newBuilder().uri(URI.create(BASE_URL + path)).GET().build(),
                HttpResponse.BodyHandlers.ofString()).body();
    }

    private static String post(String path, String json) throws Exception {
        return client.send(HttpRequest.newBuilder().uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build(),
                HttpResponse.BodyHandlers.ofString()).body();
    }

    private static String delete(String path) throws Exception {
        return client.send(HttpRequest.newBuilder().uri(URI.create(BASE_URL + path)).DELETE().build(),
                HttpResponse.BodyHandlers.ofString()).body();
    }

    private static String extrairMural(String json) {
        String chave = "\"mural\":\"";
        int inicio = json.indexOf(chave);
        if (inicio < 0) return json;
        inicio += chave.length();
        StringBuilder sb = new StringBuilder();
        boolean escape = false;
        for (int i = inicio; i < json.length(); i++) {
            char c = json.charAt(i);
            if (escape) {
                switch (c) {
                    case 'n' -> sb.append('\n');
                    case 'r' -> sb.append('\r');
                    case 't' -> sb.append('\t');
                    case '"' -> sb.append('"');
                    case '\\' -> sb.append('\\');
                    case 'u' -> {
                        if (i + 4 < json.length()) {
                            try { sb.append((char) Integer.parseInt(json.substring(i+1, i+5), 16)); i += 4; }
                            catch (NumberFormatException e) { sb.append("\\u"); }
                        }
                    }
                    default -> sb.append(c);
                }
                escape = false;
            } else if (c == '\\') { escape = true; }
            else if (c == '"')    { break; }
            else                  { sb.append(c); }
        }
        return sb.toString();
    }
}
