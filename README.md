# APS RC - Ferramenta de Comunicacao em Rede

Projeto Java simples para a APS de Redes de Computadores. A aplicacao permite que a Secretaria de Estado do Meio Ambiente e equipes de inspetores troquem mensagens em tempo real usando TCP/IP.

## Funcionalidades

- Servidor TCP com suporte a varios clientes simultaneos.
- Cliente em console, simples de compilar e apresentar.
- Identificacao por nome, equipe ou Secretaria.
- Mensagens em tempo real para todos os participantes conectados.
- Comando `/sair` para encerrar a conexao.
- Uso direto de `ServerSocket` e `Socket`, componentes derivados das primitivas de Berkeley.

## Estrutura

```text
src/main/java/br/com/aps/rede/
  ChatServer.java       Servidor TCP multiusuario.
  ChatClient.java       Cliente em console.
docs/
  relatorio-tecnico.md  Texto de apoio para o relatorio da APS.
```

## Como compilar

Instale um JDK 8 ou superior e execute os comandos abaixo na pasta do projeto:

```powershell
New-Item -ItemType Directory -Force out
javac -encoding UTF-8 -d out src/main/java/br/com/aps/rede/*.java
```

## Como executar

Abra um terminal para o servidor:

```powershell
java -cp out br.com.aps.rede.ChatServer 5050
```

Abra um ou mais terminais para os clientes:

```powershell
java -cp out br.com.aps.rede.ChatClient
```

Tambem e possivel informar IP e porta:

```powershell
java -cp out br.com.aps.rede.ChatClient 192.168.0.10 5050
```
