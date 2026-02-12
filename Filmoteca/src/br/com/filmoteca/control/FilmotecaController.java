package br.com.filmoteca.control;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;

import br.com.filmoteca.model.Filme;
import br.com.filmoteca.model.TipoMidia;
import br.com.filmoteca.util.LeitorArquivo;
import br.com.filmoteca.view.IgFilmoteca;

public class FilmotecaController {

    private final IgFilmoteca view;
    private List<Filme> acervo = new ArrayList<>();
    private List<Filme> lista = new ArrayList<>();
    private int indice = 0;

    private static final DateTimeFormatter FORMATO =
        DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale.of("pt", "BR"));

    public FilmotecaController(IgFilmoteca view) {
        this.view = view;
        configurarEventos();
    }

    private void configurarEventos() {
        view.getObterFilmesMenuItem().addActionListener(_ -> carregarArquivo());
        view.getSairMenuItem().addActionListener(_ -> System.exit(0));
        view.getPesquisarMenuItem().addActionListener(_ -> view.getPesquisaTextField().requestFocus());
        view.getMelhoresFilmesMenuItem().addActionListener(_ -> ordenarFilmes());
        view.getSobreMenuItem().addActionListener(_ -> exibirSobre());

        view.getProximoButton().addActionListener(_ -> navegar(1));
        view.getAnteriorButton().addActionListener(_ -> navegar(-1));

        view.getPesquisaTextField().addActionListener(_ -> pesquisar());

        view.getAvaliacaoSpinner().addChangeListener(_ -> salvarDados());
        view.getBlurayRadioButton().addActionListener(_ -> salvarDados());
        view.getDvdRadioButton().addActionListener(_ -> salvarDados());
    }

    private void carregarArquivo() {
        JFileChooser chooser = new JFileChooser(".");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("TXT", "txt"));

        if (chooser.showOpenDialog(view) != JFileChooser.APPROVE_OPTION) return;

        try {
            File arquivo = chooser.getSelectedFile();
            acervo = new LeitorArquivo().carregarFilmes(arquivo.getAbsolutePath());

            if (acervo.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Nenhum filme encontrado.");
                return;
            }

            configurarImagens(arquivo.getParentFile());
            lista = new ArrayList<>(acervo);
            indice = 0;
            atualizarTela();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao abrir arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void pesquisar() {
        if (acervo.isEmpty()) return;

        String termo = view.getPesquisaTextField().getText().trim();
        String termoBusca = termo.toLowerCase();

        lista = termoBusca.isEmpty()
            ? new ArrayList<>(acervo)
            : acervo.stream()
                .filter(f -> corresponde(f, termoBusca))
                .toList();

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(view, termo + " não cadastrado.");
            lista = new ArrayList<>(acervo);
        }

        indice = 0;
        atualizarTela();
    }

    private boolean corresponde(Filme f, String termo) {
        return f.getTitulo().toLowerCase().contains(termo) ||
               f.getDiretor().toLowerCase().contains(termo) ||
               f.getAutores().stream().anyMatch(a -> a.toLowerCase().contains(termo)) ||
               f.getElenco().stream().anyMatch(e -> e.toLowerCase().contains(termo));
    }

    private void ordenarFilmes() {
        if (acervo.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Carregue os filmes primeiro.");
            return;
        }

        Object[] opcoes = { "Avaliação Pessoal", "IMDB" };

        int escolha = JOptionPane.showOptionDialog(
            view, "Ordenar por:", "Melhores Filmes",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, opcoes, opcoes[0]
        );

        if (escolha == -1) return;

        lista = new ArrayList<>(acervo);

        lista.sort(escolha == 0
            ? Comparator.comparingInt(Filme::getAvaliacaoPessoal).reversed()
            : Comparator.comparingDouble(Filme::getAvaliacaoIMDB).reversed()
        );

        indice = 0;
        atualizarTela();
    }

    private void navegar(int direcao) {
        if (lista.isEmpty()) return;

        int novo = indice + direcao;

        if (novo >= 0 && novo < lista.size()) {
            indice = novo;
            atualizarTela();
        }
    }

    private void atualizarTela() {
        if (lista.isEmpty()) return;

        Filme f = lista.get(indice);

        view.setTitulo(f.getTitulo());
        view.setAno(String.valueOf(f.getAno()));
        view.setAnoTooltip(f.getDataLancamento().format(FORMATO));
        view.setClassificacao(f.getClassificacaoIndicativa());
        view.setDuracao(f.getDuracao());
        view.setImdb(String.valueOf(f.getAvaliacaoIMDB()));
        view.setSinopse(f.getSinopse());
        view.setDiretor(f.getDiretor());

        view.setGeneros(String.join(" ", f.getGeneros()));
        view.setAutores(String.join(", ", f.getAutores()));

        view.setCapa(f.getCaminhoImagem() != null
            ? new ImageIcon(f.getCaminhoImagem())
            : null
        );

        var model = view.getElencoTableModel();
        model.setRowCount(0);
        for (int i = 0; i < f.getElenco().size(); i++) {
            model.addRow(new Object[]{ i + 1, f.getElenco().get(i) });
        }

        view.getAvaliacaoSpinner().setValue(f.getAvaliacaoPessoal());
        view.getMidiaButtonGroup().clearSelection();

        if (f.getMidia() != null) {
            if (f.getMidia().equalsIgnoreCase("DVD")) {
                view.getDvdRadioButton().setSelected(true);
            } else if (f.getMidia().equalsIgnoreCase("Blu-ray")) {
                view.getBlurayRadioButton().setSelected(true);
            }
        }

        view.getAnteriorButton().setEnabled(indice > 0);
        view.getProximoButton().setEnabled(indice < lista.size() - 1);
    }

    private void salvarDados() {
        if (lista.isEmpty()) return;

        Filme f = lista.get(indice);
        f.setAvaliacaoPessoal((Integer) view.getAvaliacaoSpinner().getValue());

        if (view.getBlurayRadioButton().isSelected()) {
            f.setMidia(TipoMidia.BLURAY);
        } else if (view.getDvdRadioButton().isSelected()) {
            f.setMidia(TipoMidia.DVD);
        }
    }

    private void configurarImagens(File pastaOrigem) {
        File pasta = new File("capa");
        if (!pasta.exists() || !pasta.isDirectory()) pasta = pastaOrigem;

        for (int i = 0; i < acervo.size(); i++) {
            acervo.get(i).setCaminhoImagem(
                new File(pasta, "filme" + (i + 1) + ".png").getAbsolutePath()
            );
        }
    }

    private void exibirSobre() {
        JOptionPane.showMessageDialog(view,
            "Filmoteca\nVersão 1.0\nAno: 2025\nAutor: Carlos Rodrigues",
            "Sobre", JOptionPane.INFORMATION_MESSAGE
        );
    }
}