"""
Cliente Python para o Trabalho 4 – Pixel Art com Comunicação Indireta (Fila).
Uso: python cliente_python_api.py [host]   (padrão: localhost)
"""
import json
import sys
import urllib.request
import urllib.error

BASE_URL = "http://" + (sys.argv[1] if len(sys.argv) > 1 else "localhost") + ":8080"


def request(method, path, data=None):
    body, headers = None, {}
    if data is not None:
        body = json.dumps(data).encode("utf-8")
        headers["Content-Type"] = "application/json"
    req = urllib.request.Request(BASE_URL + path, data=body, headers=headers, method=method)
    try:
        with urllib.request.urlopen(req) as resp:
            return resp.read().decode("utf-8")
    except urllib.error.HTTPError as e:
        return e.read().decode("utf-8")
    except urllib.error.URLError:
        return '{"ok": false, "erro": "Servidor indisponivel. Inicie o ServidorAPI."}'


def criar_mural():
    largura = int(input("Largura: ")); altura = int(input("Altura: ")); dono = input("Dono: ")
    print(request("POST", "/mural", {"largura": largura, "altura": altura, "dono": dono}))

def ler_xy():
    return int(input("x: ")), int(input("y: "))

def pintar_pixel():
    x, y = ler_xy(); cor = input("Cor: ")
    print(request("POST", "/pixel", {"x": x, "y": y, "cor": cor}))

def apagar_pixel():
    x, y = ler_xy()
    print(request("DELETE", f"/pixel/{x}/{y}"))

def listar_pixels():
    print(request("GET", "/pixels"))

def aplicar_pincel():
    x1 = int(input("x inicial: ")); y1 = int(input("y inicial: "))
    x2 = int(input("x final: "));   y2 = int(input("y final: "))
    cor = input("Cor: ")
    print(request("POST", "/pincel", {"x1": x1, "y1": y1, "x2": x2, "y2": y2, "cor": cor}))

def aplicar_borracha():
    x, y = ler_xy()
    print(request("POST", "/borracha", {"x": x, "y": y}))

def visualizar_mural():
    dados = json.loads(request("GET", "/mural?ansi=true"))
    mural = dados.get("mural", "")
    mural = mural.replace("\\n", "\n").replace("\\r", "\r")
    mural = mural.replace("\\u001B", "\033").replace("\\u001b", "\033")
    pendentes = dados.get("mensagensPendentes", 0)
    print(mural)
    print(f"[INFO] Mensagens pendentes na fila: {pendentes}")

def status_fila():
    print(request("GET", "/fila/status"))

def menu():
    print("\n=== Cliente Python - Pixel Art API (T4 - Fila) ===")
    for i, op in enumerate(["Criar mural","Pintar pixel","Apagar pixel","Listar pixels",
                             "Aplicar pincel","Aplicar borracha","Visualizar mural","Status da fila"]):
        print(f"{i+1} - {op}")
    print("0 - Sair")
    return input("Opcao: ")


while True:
    op = menu()
    match op:
        case "1": criar_mural()
        case "2": pintar_pixel()
        case "3": apagar_pixel()
        case "4": listar_pixels()
        case "5": aplicar_pincel()
        case "6": aplicar_borracha()
        case "7": visualizar_mural()
        case "8": status_fila()
        case "0": print("Saindo..."); break
        case _:   print("Opcao invalida")
