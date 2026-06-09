package util;

import model.Pixel;
import java.util.List;

public class JsonUtil {

    public static String pixelToJson(Pixel p) {
        return "{\"x\":" + p.getX() + ",\"y\":" + p.getY() + ",\"cor\":\"" + escapar(p.getCor()) + "\"}";
    }

    public static String pixelsToJson(List<Pixel> pixels) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < pixels.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(pixelToJson(pixels.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }

    public static String resposta(boolean ok, String mensagem) {
        return "{\"ok\":" + ok + ",\"mensagem\":\"" + escapar(mensagem) + "\"}";
    }

    public static int getInt(String json, String chave) {
        return Integer.parseInt(getRaw(json, chave));
    }

    public static String getString(String json, String chave) {
        String alvo = "\"" + chave + "\":";
        int inicio = json.indexOf(alvo);
        if (inicio < 0) throw new IllegalArgumentException("Campo nao encontrado: " + chave);
        inicio += alvo.length();
        while (inicio < json.length() && Character.isWhitespace(json.charAt(inicio))) inicio++;
        if (json.charAt(inicio) != '"') throw new IllegalArgumentException("Campo nao e string: " + chave);
        inicio++;
        int fim = json.indexOf('"', inicio);
        if (fim < 0) throw new IllegalArgumentException("String invalida: " + chave);
        return json.substring(inicio, fim);
    }

    private static String getRaw(String json, String chave) {
        String alvo = "\"" + chave + "\":";
        int inicio = json.indexOf(alvo);
        if (inicio < 0) throw new IllegalArgumentException("Campo nao encontrado: " + chave);
        inicio += alvo.length();
        while (inicio < json.length() && Character.isWhitespace(json.charAt(inicio))) inicio++;
        int fim = inicio;
        while (fim < json.length() && "0123456789-".indexOf(json.charAt(fim)) >= 0) fim++;
        return json.substring(inicio, fim);
    }

    private static String escapar(String texto) {
        return texto == null ? "" : texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
