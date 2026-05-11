# Relatorio tecnico - APS RC

## Objetivo

O objetivo do projeto e desenvolver uma ferramenta de comunicacao em rede que permita a troca de informacoes entre equipes de inspetores ambientais e a Secretaria de Estado do Meio Ambiente. O cenario adotado envolve o acompanhamento de atividades industriais relacionadas a poluicao do Rio Tiete, desde Salesopolis ate a regiao da Grande Sao Paulo.

## Linguagem e tecnologia

A ferramenta foi desenvolvida em Java, usando apenas a biblioteca padrao da linguagem. A comunicacao foi implementada com `ServerSocket` e `Socket`, classes derivadas do conceito de sockets de Berkeley citado no enunciado da APS.

## Protocolo TCP/IP

O TCP foi escolhido porque fornece entrega confiavel, controle de ordem e controle de conexao. Isso e importante para um sistema de troca de informacoes entre equipes de campo e a Secretaria, pois mensagens de ocorrencia, alertas e arquivos nao devem ser perdidos silenciosamente.

Cada cliente abre uma conexao TCP com o servidor. O servidor mantem uma lista de participantes conectados e retransmite as mensagens recebidas para os demais clientes. Dessa forma, duas ou mais pessoas podem participar do mesmo canal de comunicacao.

## Modulos do programa

- `ChatServer`: cria o `ServerSocket`, aceita conexoes, cria uma thread para cada cliente e distribui mensagens para os demais participantes.
- `ChatClient`: conecta ao servidor com `Socket`, envia o nome do participante, le o teclado e mostra as mensagens recebidas.

## Tratamento de erros

O sistema trata falha de conexao e desconexao inesperada. Quando um cliente sai ou perde a conexao, o servidor remove esse cliente da lista ativa e avisa os demais participantes.

## Recursos adicionais

Alem do chat em tempo real, a aplicacao possui identificacao dos participantes e comando de encerramento `/sair`. Esses recursos sao suficientes para demonstrar a comunicacao TCP/IP entre duas ou mais pessoas, mantendo o projeto simples para apresentacao.

## Interdisciplinaridade

O projeto relaciona Redes de Computadores, Programacao Orientada a Objetos, Sistemas Operacionais e aspectos ambientais. A parte de redes aparece no uso de TCP/IP, sockets, portas e conexoes. A parte de programacao aparece na divisao em classes, no uso de threads e no tratamento de eventos da interface grafica. O tema ambiental aproxima a solucao de um problema real de fiscalizacao e comunicacao institucional.

## Efeito na formacao

O desenvolvimento da ferramenta ajuda a transformar conceitos teoricos de redes em uma aplicacao funcional. Durante o projeto, sao praticados conceitos como cliente-servidor, confiabilidade do TCP, concorrencia, interface grafica, validacao de dados e tratamento de falhas. Esses elementos sao importantes para a formacao em Ciencia da Computacao porque aparecem em sistemas reais distribuidos e em ferramentas corporativas.
