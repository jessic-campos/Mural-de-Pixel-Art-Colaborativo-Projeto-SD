package queue;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Fila de mensagens persistida em arquivo texto (uma linha JSON por evento).
 * Usa FileLock para segurança entre processos concorrentes.
 */
public class FilaPersistente {
    private static final Path FILA = Paths.get("dados/fila_eventos.txt");
    private static final Path LOCK = Paths.get("dados/fila_eventos.lock");

    public FilaPersistente() {
        try {
            Files.createDirectories(Paths.get("dados"));
            if (!Files.exists(FILA)) Files.createFile(FILA);
            if (!Files.exists(LOCK)) Files.createFile(LOCK);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar fila persistente: " + e.getMessage(), e);
        }
    }

    public void publicar(EventoFila evento) {
        executarComLock(() -> {
            try {
                Files.writeString(FILA, evento.toJson() + System.lineSeparator(),
                    StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                return null;
            } catch (IOException e) { throw new RuntimeException(e); }
        });
    }

    public EventoFila consumir() {
        return executarComLock(() -> {
            try {
                List<String> linhas = Files.readAllLines(FILA, StandardCharsets.UTF_8);
                if (linhas.isEmpty()) return null;
                String primeira = linhas.get(0);
                Files.write(FILA, linhas.subList(1, linhas.size()), StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
                return EventoFila.fromJson(primeira);
            } catch (IOException e) { throw new RuntimeException(e); }
        });
    }

    public int tamanho() {
        return executarComLock(() -> {
            try {
                if (!Files.exists(FILA)) return 0;
                return Files.readAllLines(FILA, StandardCharsets.UTF_8).size();
            } catch (IOException e) { throw new RuntimeException(e); }
        });
    }

    private <T> T executarComLock(Supplier<T> acao) {
        try (FileChannel ch = FileChannel.open(LOCK, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
             FileLock lock = ch.lock()) {
            return acao.get();
        } catch (IOException e) {
            throw new RuntimeException("Erro na fila persistente: " + e.getMessage(), e);
        }
    }
}
