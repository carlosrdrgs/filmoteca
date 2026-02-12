package br.com.filmoteca.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static mos.plaf.LookAndFeel.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class IgFilmoteca extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField pesquisaTextField;
	private JTextField diretorTextField;
	private JTextField autoresTextField;
	private JTextArea sinopseTextArea;
	private JTextField imdbTextField;

	private JLabel capaLabel;
	private JLabel tituloLabel;
	private JLabel anoLabel;
	private JLabel classificacaoLabel;
	private JLabel duracaoLabel;
	private JLabel generosLabel;

	private JSpinner avaliacaoSpinner;
	private JRadioButton blurayRadioButton;
	private JRadioButton dvdRadioButton;
	private ButtonGroup midiaButtonGroup;

	private JButton anteriorButton;
	private JButton proximoButton;

	private JTable elencoJTable;
	private DefaultTableModel elencoTableModel;

	private JMenuItem obterFilmesMenuItem;
	private JMenuItem pesquisarMenuItem;
	private JMenuItem melhoresFilmesMenuItem;
	private JMenuItem sairMenuItem;
	private JMenuItem sobreMenuItem;

	public IgFilmoteca() {
		setTitle("Filmoteca");
		setSize(712, 548);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu filmotecaMenu = new JMenu("Filmoteca");
		menuBar.add(filmotecaMenu);

		obterFilmesMenuItem = new JMenuItem("Obter filmes...");
		filmotecaMenu.add(obterFilmesMenuItem);

		pesquisarMenuItem = new JMenuItem("Pesquisar");
		pesquisarMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		filmotecaMenu.add(pesquisarMenuItem);

		melhoresFilmesMenuItem = new JMenuItem("Melhores filmes...");
		melhoresFilmesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
		filmotecaMenu.add(melhoresFilmesMenuItem);

		filmotecaMenu.addSeparator();

		sairMenuItem = new JMenuItem("Sair");
		filmotecaMenu.add(sairMenuItem);

		JMenu ajudaMenu = new JMenu("Ajuda");
		menuBar.add(ajudaMenu);

		sobreMenuItem = new JMenuItem("Sobre a Filmoteca");
		ajudaMenu.add(sobreMenuItem);

		pesquisaTextField = new JTextField();
		pesquisaTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pesquisaTextField.setText("");
				pesquisaTextField.setForeground(new Color(0,0,0));
			}
		});
		pesquisaTextField.setForeground(Color.LIGHT_GRAY);
		pesquisaTextField.setText("Pesquise por filme, artista, autor ou diretor");
		getContentPane().add(pesquisaTextField, BorderLayout.NORTH);
		pesquisaTextField.setColumns(10);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel filmeJPanel = new JPanel();
		filmeJPanel.setLayout(null);
		tabbedPane.addTab("Filme", null, filmeJPanel, null);

		capaLabel = new JLabel("");
		capaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		capaLabel.setBorder(null);
		capaLabel.setBounds(10, 11, 250, 371);
		filmeJPanel.add(capaLabel);

		tituloLabel = new JLabel("Título do Filme");
		tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
		tituloLabel.setBounds(270, 11, 419, 30);
		filmeJPanel.add(tituloLabel);

		anoLabel = new JLabel("2000");
		anoLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		anoLabel.setBounds(270, 50, 40, 20);
		filmeJPanel.add(anoLabel);

		classificacaoLabel = new JLabel("12 anos");
		classificacaoLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		classificacaoLabel.setBounds(320, 50, 60, 20);
		filmeJPanel.add(classificacaoLabel);

		duracaoLabel = new JLabel("2h 00m");
		duracaoLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		duracaoLabel.setBounds(390, 50, 60, 20);
		filmeJPanel.add(duracaoLabel);

		generosLabel = new JLabel("Ação Crime Drama");
		generosLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		generosLabel.setBounds(270, 75, 419, 20);
		filmeJPanel.add(generosLabel);

		JLabel diretorLabel = new JLabel("Diretor:");
		diretorLabel.setBounds(270, 106, 50, 14);
		filmeJPanel.add(diretorLabel);

		diretorTextField = new JTextField();
		diretorTextField.setEditable(false);
		diretorTextField.setBounds(325, 103, 365, 25);
		filmeJPanel.add(diretorTextField);
		diretorTextField.setColumns(10);

		JLabel autoresLabel = new JLabel("Autores:");
		autoresLabel.setBounds(270, 137, 50, 14);
		filmeJPanel.add(autoresLabel);

		autoresTextField = new JTextField();
		autoresTextField.setEditable(false);
		autoresTextField.setBounds(325, 134, 364, 25);
		filmeJPanel.add(autoresTextField);
		autoresTextField.setColumns(10);

		JPanel sinopseJPanel = new JPanel();
		sinopseJPanel.setBorder(new TitledBorder(null, "Sinopse", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sinopseJPanel.setBounds(270, 170, 419, 130);
		sinopseJPanel.setLayout(new BorderLayout(0, 0));
		filmeJPanel.add(sinopseJPanel);

		sinopseTextArea = new JTextArea();
		sinopseTextArea.setEditable(false);
		sinopseTextArea.setLineWrap(true);
		sinopseTextArea.setWrapStyleWord(true);
		sinopseTextArea.setBackground(new Color(240, 240, 240));

		JScrollPane sinopseScrollPane = new JScrollPane(sinopseTextArea);
		sinopseJPanel.add(sinopseScrollPane, BorderLayout.CENTER);

		JPanel avaliacaoJPanel = new JPanel();
		avaliacaoJPanel.setBorder(new TitledBorder(null, "Avaliação", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		avaliacaoJPanel.setBounds(270, 311, 230, 70);
		avaliacaoJPanel.setLayout(null);
		filmeJPanel.add(avaliacaoJPanel);

		JLabel imdbLabel = new JLabel("IMDB:");
		imdbLabel.setBounds(10, 25, 40, 14);
		avaliacaoJPanel.add(imdbLabel);

		imdbTextField = new JTextField();
		imdbTextField.setEditable(false);
		imdbTextField.setBounds(45, 22, 40, 20);
		avaliacaoJPanel.add(imdbTextField);
		imdbTextField.setColumns(10);

		JLabel suaAvaliacaoLabel = new JLabel("Sua avaliação:");
		suaAvaliacaoLabel.setBounds(95, 25, 80, 14);
		avaliacaoJPanel.add(suaAvaliacaoLabel);

		avaliacaoSpinner = new JSpinner();
		avaliacaoSpinner.setModel(new SpinnerNumberModel(0, 0, 10, 1)); 
		avaliacaoSpinner.setBounds(180, 22, 40, 20);
		avaliacaoJPanel.add(avaliacaoSpinner);

		JPanel midiaJPanel = new JPanel();
		midiaJPanel.setBorder(new TitledBorder(null, "Mídia", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		midiaJPanel.setBounds(510, 311, 179, 70);
		filmeJPanel.add(midiaJPanel);
		FlowLayout fl_midiaJPanel = new FlowLayout(FlowLayout.CENTER, 5, 5);
		midiaJPanel.setLayout(fl_midiaJPanel);

		blurayRadioButton = new JRadioButton("Blu-ray");
		midiaJPanel.add(blurayRadioButton);

		dvdRadioButton = new JRadioButton("DVD");
		midiaJPanel.add(dvdRadioButton);

		midiaButtonGroup = new ButtonGroup();
		midiaButtonGroup.add(blurayRadioButton);
		midiaButtonGroup.add(dvdRadioButton);

		JPanel navegacaoJPanel = new JPanel();
		navegacaoJPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		navegacaoJPanel.setBounds(0, 395, 700, 40); 
		filmeJPanel.add(navegacaoJPanel);

		anteriorButton = new JButton("< Anterior");
		anteriorButton.setEnabled(false);
		navegacaoJPanel.add(anteriorButton);

		proximoButton = new JButton("Próximo >");
		proximoButton.setEnabled(false);
		navegacaoJPanel.add(proximoButton);
		
		JPanel elencoJPanel = new JPanel();
		tabbedPane.addTab("Elenco", null, elencoJPanel, null);
		elencoJPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane elencoScrollPane = new JScrollPane();
		elencoJPanel.add(elencoScrollPane, BorderLayout.CENTER);

		elencoJTable = new JTable();
		elencoTableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] { "Nº", "Artista" }
				) {
			boolean[] columnEditables = new boolean[] { false, false };
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		elencoJTable.setModel(elencoTableModel);

		elencoJTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		elencoJTable.getColumnModel().getColumn(0).setMaxWidth(80);

		elencoScrollPane.setViewportView(elencoJTable);
		modifyLookAndFeel(NIMBUS, IgFilmoteca.this);
	}

	public JSpinner getAvaliacaoSpinner() {
		return avaliacaoSpinner;
	}

	public void setAvaliacaoSpinner(JSpinner avaliacaoSpinner) {
		this.avaliacaoSpinner = avaliacaoSpinner;
	}

	public JRadioButton getBlurayRadioButton() {
		return blurayRadioButton;
	}

	public void setBlurayRadioButton(JRadioButton blurayRadioButton) {
		this.blurayRadioButton = blurayRadioButton;
	}

	public JButton getAnteriorButton() {
		return anteriorButton;
	}

	public void setAnteriorButton(JButton anteriorButton) {
		this.anteriorButton = anteriorButton;
	}

	public JTextField getPesquisaTextField() {
		return pesquisaTextField;
	}

	public JRadioButton getDvdRadioButton() {
		return dvdRadioButton;
	}

	public ButtonGroup getMidiaButtonGroup() {
		return midiaButtonGroup;
	}

	public JButton getProximoButton() {
		return proximoButton;
	}

	public DefaultTableModel getElencoTableModel() {
		return elencoTableModel;
	}

	public JMenuItem getObterFilmesMenuItem() {
		return obterFilmesMenuItem;
	}

	public JMenuItem getPesquisarMenuItem() {
		return pesquisarMenuItem;
	}

	public JMenuItem getMelhoresFilmesMenuItem() {
		return melhoresFilmesMenuItem;
	}

	public JMenuItem getSairMenuItem() {
		return sairMenuItem;
	}

	public JMenuItem getSobreMenuItem() {
		return sobreMenuItem;
	}

	public void setCapa(javax.swing.Icon icon) { 
		if (this.capaLabel != null) this.capaLabel.setIcon(icon); 
	}

	public void setTitulo(String t) {
		if (tituloLabel != null) tituloLabel.setText(t); 
	}

	public void setAno(String a) {
		if (anoLabel != null) anoLabel.setText(a); 
	}
	public void setClassificacao(String c) { 
		if (classificacaoLabel != null) classificacaoLabel.setText(c); 
	}
	public void setDuracao(String d) { 
		if (duracaoLabel != null) duracaoLabel.setText(d); 
	}
	public void setGeneros(String g) {
		if (generosLabel != null) generosLabel.setText(g); 
	}
	public void setAnoTooltip(String dataFormatada) { 
		this.anoLabel.setToolTipText(dataFormatada); 
	}
	public void setDiretor(String d) { 
		if (diretorTextField != null) diretorTextField.setText(d); 
	}
	public void setAutores(String a) { 
		if (autoresTextField != null) autoresTextField.setText(a); 
	}
	public void setSinopse(String s) { 
		if (sinopseTextArea != null) sinopseTextArea.setText(s); 
	}
	public void setImdb(String i) { 
		if (imdbTextField != null) imdbTextField.setText(i); 
	}
}