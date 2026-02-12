package br.com.filmoteca.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import br.com.filmoteca.model.Filme;
import mos.textfile.TextFile;

public class LeitorArquivo {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String HASHTAG = "#";
    private static final String DELIMITADORES = ";=";

    /**
     * Le o arquivo e retorna uma lista de Filmes
     * @param caminhoArquivo
     * @return
     * @throws IOException
     */
    public List<Filme> carregarFilmes(String caminhoArquivo) throws IOException {
        List<Filme> biblioteca = new ArrayList<>();
        Filme filmeAtual = null;

        try (TextFile textFile = new TextFile()) {
            textFile.open(caminhoArquivo);

            String linha;

            while ((linha = textFile.readLine()) != null) {

                if (linha.trim().isEmpty()) {
                    continue;
                }

                if (linha.startsWith(HASHTAG)) {
                    if (filmeAtual != null) {
                        biblioteca.add(filmeAtual);
                    }
                    filmeAtual = new Filme();
                    continue;
                }

                if (filmeAtual != null) {
                    processarLinha(filmeAtual, linha);
                }
            }

            if (filmeAtual != null) {
                biblioteca.add(filmeAtual);
            }
        }

        return biblioteca;
    }

    /**
     * Processa uma unica linha e preenche os dados no objeto
     * @param filme
     * @param linha
     */
    private void processarLinha(Filme filme, String linha) {
        StringTokenizer stringTokenizer = new StringTokenizer(linha, DELIMITADORES);

        if (stringTokenizer.hasMoreTokens()) {
            String tokenInicial = stringTokenizer.nextToken().trim();

            switch (tokenInicial) {
            case "sinopse":
                if (stringTokenizer.hasMoreTokens()) {
                    String texto = stringTokenizer.nextToken().trim();
                    if (texto.endsWith(".")) {
                        texto = texto.substring(0, texto.length() - 1);
                    }
                    filme.setSinopse(texto);
                }
                break;

            case "gêneros":
                filme.setGeneros(extrairListaRestante(stringTokenizer));
                break;

            case "diretor":
                if (stringTokenizer.hasMoreTokens()) {
                    filme.setDiretor(stringTokenizer.nextToken().trim());
                }
                break;

            case "autores":
                filme.setAutores(extrairListaRestante(stringTokenizer));
                break;

            case "elenco":
                filme.setElenco(extrairListaRestante(stringTokenizer));
                break;

            default:
                if (!filme.getTitulo().equals("Sem Título")) {
                    String sinopseAtual = filme.getSinopse();
                    if (sinopseAtual.equals("Sem Sinopse")) {
                        filme.setSinopse(linha.trim());
                    } else {
                        filme.setSinopse(sinopseAtual + " " + linha.trim());
                    }
                } else {
                    preencherMetadados(filme, tokenInicial, stringTokenizer);
                }
                break;
            }
        }
    }

    /**
     * Preenche os metadados do filme
     * @param filme
     * @param titulo
     * @param st
     */
    private void preencherMetadados(Filme filme, String titulo, StringTokenizer st) {
        filme.setTitulo(titulo);

        try {
            if (st.hasMoreTokens()) 
                filme.setAno(Integer.parseInt(st.nextToken().trim()));
            if (st.hasMoreTokens()) 
                filme.setDataLancamento(LocalDate.parse(st.nextToken().trim(), FORMATO_DATA));
            if (st.hasMoreTokens()) 
                filme.setClassificacaoIndicativa(st.nextToken().trim());
            if (st.hasMoreTokens()) 
                filme.setDuracao(st.nextToken().trim());
            if (st.hasMoreTokens()) 
                filme.setAvaliacaoIMDB(Double.parseDouble(st.nextToken().trim()));
        } catch (Exception e) {
            System.err.println("Erro ao converter metadados do filme: " + e.getMessage());
        }
    }

    private List<String> extrairListaRestante(StringTokenizer stringTokenizer) {
        List<String> lista = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            lista.add(stringTokenizer.nextToken().trim());
        }
        return lista;
    }
}