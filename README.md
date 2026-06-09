# Mural Pixel Art — Sistemas Distribuídos (QXD0043)

Monorepo Maven multi-módulo com as quatro implementações progressivas do sistema Pixel Art.

## Estrutura do projeto

```
mural-pixel-art/
├── pom.xml              ← pai (gerencia todos os módulos)
├── shared/              ← POJOs e interfaces comuns (Trabalhos 1 e 2)
├── trabalho-1/          ← Comunicação via Sockets TCP
├── trabalho-2/          ← RMI (Remote Method Invocation)
├── trabalho-3/          ← REST API HTTP/JSON
├── trabalho-4/          ← Comunicação Indireta – Fila de Mensagens
├── docs/                ← Enunciados PDF dos 4 trabalhos
└── README.md
```

---

## Trabalho 1 — Sockets TCP

Comunicação direta cliente-servidor usando sockets Java (`Socket` / `ServerSocket`) com streams de objetos serializados.

### Compilar e executar

```bash
# Na raiz do monorepo
mvn -pl shared,trabalho-1 package -DskipTests

# Terminal 1 – servidor
java -cp trabalho-1/target/classes app.Main servidor

# Terminal 2 – cliente
java -cp trabalho-1/target/classes:shared/target/classes app.Main cliente
```

---

## Trabalho 2 — RMI

Invocação de métodos remotos via Java RMI sem dependências externas.

### Compilar e executar

```bash
mvn -pl shared,trabalho-2 package -DskipTests

# Terminal 1
java -cp trabalho-2/target/classes:shared/target/classes server.ServidorRMI

# Terminal 2
java -cp trabalho-2/target/classes:shared/target/classes client.ClienteRMI
```

---

## Trabalho 3 — REST API HTTP/JSON

Servidor HTTP puro (`com.sun.net.httpserver`) expondo recursos REST. Sem frameworks externos.

### Estrutura interna

```
trabalho-3/src/main/
├── java/
│   ├── model/          ← Entidades (Mural, Pixel, Coordenada, etc.)
│   ├── resource/       ← MuralResource, PixelResource, FerramentaResource
│   ├── server/         ← ServidorAPI (main)
│   └── util/           ← JsonUtil, HttpResponder, PixelArtService
├── java-cliente/       ← ClienteJavaAPI.java (standalone, sem Maven)
└── python/             ← cliente_python_api.py
```

### Compilar e executar (Maven)

```bash
mvn -pl trabalho-3 package -DskipTests

# Terminal 1 – servidor
java -cp trabalho-3/target/classes server.ServidorAPI

# Terminal 2 – cliente Java (standalone)
cd trabalho-3/src/main/java-cliente
javac -encoding UTF-8 ClienteJavaAPI.java
java ClienteJavaAPI           # localhost
java ClienteJavaAPI 192.168.x.x  # outro host

# Terminal 2 – cliente Python
cd trabalho-3/src/main/python
python cliente_python_api.py           # localhost
python cliente_python_api.py 192.168.x.x
```

### Endpoints

| Método | Endpoint          | Função                        |
|--------|-------------------|-------------------------------|
| POST   | `/mural`          | Criar/recriar mural           |
| GET    | `/mural`          | Visualizar mural (JSON/ANSI)  |
| POST   | `/pixel`          | Pintar pixel                  |
| DELETE | `/pixel/{x}/{y}`  | Apagar pixel                  |
| GET    | `/pixels`         | Listar todos os pixels        |
| POST   | `/pincel`         | Pintar área retangular        |
| POST   | `/borracha`       | Apagar pixel                  |

---

## Trabalho 4 — Comunicação Indireta (Fila de Mensagens)

Evolução do Trabalho 3 com desacoplamento temporal via fila persistente em arquivo.

### Arquitetura

```
Cliente Java/Python
        │
        ▼ HTTP/JSON
  ServidorAPI (Produtor)
        │
        ▼ publica JSON
  fila_eventos.txt  ←── FilaPersistente (intermediário com FileLock)
        │
        ▼ consome JSON
  WorkerFila (Consumidor)
        │
        ▼ salva estado
  mural_estado.ser
```

### Estrutura interna

```
trabalho-4/src/main/
├── java/
│   ├── model/          ← mesmas entidades do T3
│   ├── queue/          ← EventoFila, FilaPersistente
│   ├── resource/       ← MuralResource, PixelResource, FerramentaResource, FilaResource
│   ├── server/         ← ServidorAPI (produtor)
│   ├── util/           ← JsonUtil, HttpResponder, MuralStore, PixelArtService
│   └── worker/         ← WorkerFila (consumidor)
├── java-cliente/       ← ClienteJavaAPI.java (standalone)
└── python/             ← cliente_python_api.py
```

### Compilar e executar (Maven)

```bash
mvn -pl trabalho-4 package -DskipTests

# Os processos devem ser executados a partir do diretório trabalho-4/
cd trabalho-4

# Terminal 1 – servidor (produtor)
java -cp target/classes server.ServidorAPI

# Terminal 2 – worker (consumidor)
java -cp target/classes worker.WorkerFila

# Terminal 3 – cliente Java
cd src/main/java-cliente
javac -encoding UTF-8 ClienteJavaAPI.java
java ClienteJavaAPI

# Terminal 3 – cliente Python
cd src/main/python
python cliente_python_api.py
```

### Demonstração de desacoplamento temporal

1. Inicie apenas o `ServidorAPI`.
2. Deixe o `WorkerFila` **desligado**.
3. Use o cliente para criar mural, pintar pixels, etc.
4. Verifique opção `8 - Status da fila` → mensagens pendentes acumuladas.
5. Inicie o `WorkerFila` — ele consumirá todos os eventos.
6. Visualize o mural atualizado.

### Endpoint extra (T4)

| Método | Endpoint       | Função                           |
|--------|----------------|----------------------------------|
| GET    | `/fila/status` | Qtd. de mensagens pendentes      |

---

## Comparativo das abordagens

| Critério            | T1 Sockets     | T2 RMI         | T3 REST API    | T4 Fila        |
|---------------------|----------------|----------------|----------------|----------------|
| Protocolo           | TCP binário    | RMI/JRMP       | HTTP/JSON      | HTTP + arquivo |
| Acoplamento         | Alto           | Alto           | Médio          | Baixo          |
| Desacoplamento temp.| Não            | Não            | Não            | Sim            |
| Clientes externos   | Difícil        | Difícil        | Fácil (curl)   | Fácil (curl)   |
| Resiliência         | Baixa          | Baixa          | Média          | Alta           |

---

## Entidades do domínio

```
Usuario ◄── Artista, Administrador
Ferramenta ◄── Pincel, Borracha
Mural ──► List<Pixel>
Pixel ──► Coordenada
ProjetoPixelArt ──► Usuario + Mural  (T1/T2 via shared)
EventoFila  (T4 – mensagem da fila)
```

## Cores disponíveis

`VERMELHO`, `AZUL`, `VERDE`, `AMARELO`, `PRETO`, `BRANCO`, `ROSA`, `MARROM`, `LARANJA`
