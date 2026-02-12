# Filmoteca 🎬
Módulo Desktop (Java Swing)

<p align="center">
<img src="https://img.shields.io/badge/Java-22-orange" />
<img src="https://img.shields.io/badge/IDE-Eclipse-blue" />
<img src="https://img.shields.io/badge/UI-Swing-green" />
<img src="https://img.shields.io/badge/UTF--8-Enabled-brightgreen" />
</p>
📝 Descrição

Aplicativo Desktop desenvolvido para a gestão de coleções de filmes. O sistema permite que o usuário organize sua biblioteca pessoal através da importação de arquivos de texto e consulta de dados detalhados via interface gráfica.

O projeto foi desenvolvido como parte da disciplina de Linguagem de Programação Visual (4º Período).<br>
🚀 Tecnologias Utilizadas

    Java 22 

    Swing (GUI) 

    Eclipse IDE (v2024-06+) 

    Manipulação de Arquivos (I/O) 

    Programação Orientada a Objetos 

📌 Funcionalidades Principais

    Importação de Dados: Carregamento de filmes a partir de arquivos .txt via JFileChooser.

    Pesquisa Inteligente: Busca dinâmica por título, artista, autor ou diretor.

    Navegação entre Resultados: Sistema de paginação (Anterior/Próximo) para múltiplos resultados encontrados.

    Gestão de Mídia e Avaliação: Alteração em tempo real da nota pessoal e tipo de mídia (DVD/Blu-ray) com salvamento automático.

    Visualização Detalhada: Exibição de pôsteres, sinopses e listagem completa do elenco em abas separadas.

    Atalhos de Teclado: Suporte a teclas de atalho (Ex: Ctrl+P para pesquisar, Ctrl+M para melhores filmes).

⚙️ Estrutura do Arquivo de Dados

O programa consome arquivos no formato UTF-8 seguindo o padrão abaixo:
Plaintext

#Filme 1<br>
título; ano; data de lançamento; classificação; duração; avaliação IMDB <br>
sinopse=Descrição do filme... <br>
gêneros=Ação; Drama <br>
diretor=Nome do Diretor<br>
autores=Autor 1; Autor 2<br>
elenco=Ator 1; Ator 2<br>

    Nota: As imagens dos pôsteres devem seguir a nomenclatura filme1.png, filme2.png, etc., com resolução máxima de 250×371 pixels.

📦 Como Executar

    Certifique-se de ter o JDK 22 instalado em sua máquina.

    Importe o projeto na IDE Eclipse.

    Execute a classe principal para iniciar a interface gráfica.

    No menu Filmoteca, selecione Obter filmes... para carregar o seu arquivo de dados.
