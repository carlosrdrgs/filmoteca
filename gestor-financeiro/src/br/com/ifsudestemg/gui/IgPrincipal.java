package br.com.ifsudestemg.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.com.ifsudestemg.dao.Database;
import br.com.ifsudestemg.dao.DespesaDAO;
import br.com.ifsudestemg.dao.OrcamentoDAO;
import br.com.ifsudestemg.dao.ReceitaDAO;
import br.com.ifsudestemg.model.Orcamento;
import br.com.ifsudestemg.model.Receita;

public class IgPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel corpoPane;
	private JTextField totalMensalField;
	private JTextField totalAPagarField;
	private JTextField totalPagoField;
	private JTextField saldoField;
	private JTable despesasMensaisTable;
	private JTextField valorField;
	
	private JComboBox<String> mesComboBox;
	private JComboBox<String> tipoComboBox;
	
	private Database database;
	private OrcamentoDAO orcamentoDAO;
	private ReceitaDAO receitaDAO;
	private DespesaDAO despesaDAO;
	
	private Orcamento orcamentoAtual;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IgPrincipal frame = new IgPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IgPrincipal() {
		inicializarBancoDados();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fecharBancoDados();
			}
		});

		setResizable(false);
		setTitle("Planejamento Financeiro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(816, 460);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu orcamentoMenu = new JMenu("Orçamento");
		orcamentoMenu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(orcamentoMenu);
		
		JMenuItem mntmImportar = new JMenuItem("Importar...");
		mntmImportar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
		orcamentoMenu.add(mntmImportar);
		
		orcamentoMenu.addSeparator();
		
		JMenuItem mntmNovo = new JMenuItem("Novo");
		mntmNovo.setMnemonic('N');
		mntmNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoNovo();
			}
		});
		orcamentoMenu.add(mntmNovo);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir...");
		mntmAbrir.setMnemonic('A');
		mntmAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoAbrir();
			}
		});
		orcamentoMenu.add(mntmAbrir);
		
		JMenuItem mntmGravar = new JMenuItem("Gravar...");
		mntmGravar.setMnemonic('G');
		mntmGravar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		mntmGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoGravar();
			}
		});
		orcamentoMenu.add(mntmGravar);
		
		orcamentoMenu.addSeparator();
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.setMnemonic('S');
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharBancoDados();
				System.exit(0);
			}
		});
		orcamentoMenu.add(mntmSair);
		
		JMenu pesquisarMenu = new JMenu("Pesquisar");
		pesquisarMenu.setMnemonic(KeyEvent.VK_P);
		menuBar.add(pesquisarMenu);
		
		JMenuItem mntmReceita = new JMenuItem("Receita");
		mntmReceita.setEnabled(false);
		pesquisarMenu.add(mntmReceita);
		
		JMenuItem mntmDespesa = new JMenuItem("Despesa...");
		mntmDespesa.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		pesquisarMenu.add(mntmDespesa);
		
		JMenu ajudaMenu = new JMenu("Ajuda");
		ajudaMenu.setMnemonic('A');
		menuBar.add(ajudaMenu);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre o Planejamento Financeiro");
		ajudaMenu.add(mntmSobre);
		
		corpoPane = new JPanel();
		corpoPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(corpoPane);
		corpoPane.setLayout(new BorderLayout(0, 0));
		
		JPanel balancoMensalPanel = new JPanel();
		balancoMensalPanel.setBorder(new TitledBorder(null, "Balan\u00E7o Mensal", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		corpoPane.add(balancoMensalPanel, BorderLayout.SOUTH);
		
		JLabel totalMensalLabel = new JLabel("Total Mensal:");
		balancoMensalPanel.add(totalMensalLabel);
		
		totalMensalField = new JTextField();
		totalMensalField.setEditable(false);
		balancoMensalPanel.add(totalMensalField);
		totalMensalField.setColumns(10);
		
		JLabel totalAPagarLabel = new JLabel("Total a pagar:");
		balancoMensalPanel.add(totalAPagarLabel);
		
		totalAPagarField = new JTextField();
		totalAPagarField.setEditable(false);
		balancoMensalPanel.add(totalAPagarField);
		totalAPagarField.setColumns(10);
		
		JLabel totalPagoLabel = new JLabel("Total pago:");
		balancoMensalPanel.add(totalPagoLabel);
		
		totalPagoField = new JTextField();
		totalPagoField.setEditable(false);
		balancoMensalPanel.add(totalPagoField);
		totalPagoField.setColumns(10);
		
		JLabel saldoLabel = new JLabel("Saldo:");
		balancoMensalPanel.add(saldoLabel);
		
		saldoField = new JTextField();
		saldoField.setEditable(false);
		balancoMensalPanel.add(saldoField);
		saldoField.setColumns(10);
		
		JPanel receitaEDespesasMensaisPane = new JPanel();
		corpoPane.add(receitaEDespesasMensaisPane, BorderLayout.CENTER);
		receitaEDespesasMensaisPane.setLayout(new BorderLayout(0, 0));
		
		JPanel receitaPanel = new JPanel();
		FlowLayout fl_receitaPanel = (FlowLayout) receitaPanel.getLayout();
		fl_receitaPanel.setAlignment(FlowLayout.LEFT);
		receitaPanel.setBorder(new TitledBorder(null, "Receita", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		receitaEDespesasMensaisPane.add(receitaPanel, BorderLayout.NORTH);
		
		JLabel mesLabel = new JLabel("Mês:");
		mesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		mesLabel.setDisplayedMnemonic(KeyEvent.VK_M);
		receitaPanel.add(mesLabel);
		
		mesComboBox = new JComboBox<>();
		mesComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
			"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", 
			"Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
		}));
		mesLabel.setLabelFor(mesComboBox);
		receitaPanel.add(mesComboBox);
		
		JLabel tipoLabel = new JLabel("Tipo:");
		tipoLabel.setDisplayedMnemonic(KeyEvent.VK_T);
		receitaPanel.add(tipoLabel);
		
		tipoComboBox = new JComboBox<>();
		tipoComboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Salário", "13º", "Férias", "Outros"}));
		tipoLabel.setLabelFor(tipoComboBox);
		receitaPanel.add(tipoComboBox);
		
		JLabel valorLabel = new JLabel("Valor:");
		valorLabel.setDisplayedMnemonic(KeyEvent.VK_V);
		receitaPanel.add(valorLabel);
		
		valorField = new JTextField();
		receitaPanel.add(valorField);
		valorField.setColumns(10);
		
		JScrollPane despesasMensaisScrollPane = new JScrollPane();
		despesasMensaisScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		despesasMensaisScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		despesasMensaisScrollPane.setBorder(new TitledBorder(null, "Despesas Mensais", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		receitaEDespesasMensaisPane.add(despesasMensaisScrollPane, BorderLayout.CENTER);
		
		despesasMensaisTable = new JTable();
		despesasMensaisTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		despesasMensaisTable.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] { "Data", "Descrição", "Valor", "Situação" }
		) {
			boolean[] columnEditables = new boolean[] { false, false, false, false };
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		despesasMensaisTable.getColumnModel().getColumn(0).setPreferredWidth(80);
		despesasMensaisTable.getColumnModel().getColumn(1).setPreferredWidth(250);
		despesasMensaisTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		despesasMensaisTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		
		despesasMensaisScrollPane.setViewportView(despesasMensaisTable);
	}
	
	private void inicializarBancoDados() {
		try {
			this.database = new Database();
			this.orcamentoDAO = new OrcamentoDAO(database.getConexao());
			this.receitaDAO = new ReceitaDAO(database.getConexao());
			this.despesaDAO = new DespesaDAO(database.getConexao());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Erro ao conectar no banco: " + e.getMessage());
			System.exit(1);
		}
	}
	
	private void fecharBancoDados() {
		if (database != null) database.close();
	}

	private void limparTela() {
		valorField.setText("");
		totalMensalField.setText("");
		totalAPagarField.setText("");
		totalPagoField.setText("");
		saldoField.setText("");
		
		if (mesComboBox.getItemCount() > 0) mesComboBox.setSelectedIndex(0);
		if (tipoComboBox.getItemCount() > 0) tipoComboBox.setSelectedIndex(0);

		DefaultTableModel model = (DefaultTableModel) despesasMensaisTable.getModel();
		model.setRowCount(0);
	}

	private void acaoNovo() {
		this.orcamentoAtual = null;
		limparTela();
		JOptionPane.showMessageDialog(this, "Novo orçamento iniciado.\nPreencha os dados e clique em Gravar.", "Novo", JOptionPane.INFORMATION_MESSAGE);
	}

	private void acaoAbrir() {
		try {
			List<Orcamento> lista = orcamentoDAO.listarTodos();
			
			if (lista.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Nenhum orçamento encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			Object[] opcoes = lista.toArray();
			Orcamento selecionado = (Orcamento) JOptionPane.showInputDialog(
					this, 
					"Escolha o orçamento:", 
					"Abrir", 
					JOptionPane.QUESTION_MESSAGE, 
					null, 
					opcoes, 
					opcoes[0]);
			
			if (selecionado != null) {
				this.orcamentoAtual = selecionado;
				limparTela();
				setTitle("Planejamento Financeiro - " + orcamentoAtual.getNome());
				JOptionPane.showMessageDialog(this, "Orçamento carregado: " + selecionado.getNome());
				
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Erro ao buscar orçamentos: " + e.getMessage());
		}
	}
	
	/**
	 * Salva o Orçamento (nome) e a Receita atual.
	 */
	private void acaoGravar() {
		try {
			double valorReceita = 0.0;
			try {
				String textoValor = valorField.getText().replace(",", ".");
				if (!textoValor.isEmpty()) {
					valorReceita = Double.parseDouble(textoValor);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Valor inválido! Digite apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (orcamentoAtual == null) {
				String nome = JOptionPane.showInputDialog(this, "Digite o nome para este novo Orçamento:", "Gravar Orçamento", JOptionPane.QUESTION_MESSAGE);
				if (nome == null || nome.trim().isEmpty()) return;

				orcamentoAtual = new Orcamento();
				orcamentoAtual.setNome(nome);
			}

			orcamentoDAO.salvar(orcamentoAtual);
			setTitle("Planejamento Financeiro - " + orcamentoAtual.getNome());

			salvarReceitaAtual(valorReceita);

			JOptionPane.showMessageDialog(this, "Orçamento e Receita gravados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Erro ao gravar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * verifica se já existe uma receita desse tipo nesse mês para atualizar o ID
	 */
	private void salvarReceitaAtual(double valor) throws SQLException {
		String mes = (String) mesComboBox.getSelectedItem();
		String tipo = (String) tipoComboBox.getSelectedItem();

		Receita receita = new Receita();
		receita.setIdOrcamento(orcamentoAtual.getId());
		receita.setMes(mes);
		receita.setTipo(tipo);
		receita.setValor(valor);

		List<Receita> listaDoMes = receitaDAO.listarPorMes(orcamentoAtual.getId(), mes);
		
		for (Receita r : listaDoMes) {
			if (r.getTipo().equals(tipo)) {
				receita.setId(r.getId());
				break;
			}
		}

		receitaDAO.salvar(receita);
	}
}