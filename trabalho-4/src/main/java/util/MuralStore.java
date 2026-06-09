package util;

import model.Mural;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Persiste o estado do Mural em arquivo .ser entre sessões do WorkerFila.
 */
public class MuralStore {
    private final Path arquivo;

    public MuralStore(String caminho) {
        this.arquivo = Path.of(caminho);
        try { Files.createDirectories(this.arquivo.getParent()); }
        catch (Exception e) { throw new RuntimeException("Erro ao preparar armazenamento: " + e.getMessage(), e); }
    }

    public synchronized Mural carregar() {
        if (!Files.exists(arquivo)) return new Mural();
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(arquivo))) {
            return (Mural) in.readObject();
        } catch (Exception e) {
            System.out.println("[STORE] Nao foi possivel carregar mural. Criando mural padrao. Erro: " + e.getMessage());
            return new Mural();
        }
    }

    public synchronized void salvar(Mural mural) {
        try {
            Files.createDirectories(arquivo.getParent());
            try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(arquivo))) {
                out.writeObject(mural);
            }
        } catch (Exception e) { throw new RuntimeException("Erro ao salvar mural: " + e.getMessage(), e); }
    }
}
