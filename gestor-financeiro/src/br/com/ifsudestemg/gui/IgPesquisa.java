package br.com.ifsudestemg.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class IgPesquisa extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTextField itemDeDespesaTextField;
	private JRadioButton dataRadioButton;
	private JRadioButton descricaoRadioButton;
	private JRadioButton valorRadioButton;
	private ButtonGroup grupoPesquisa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			IgPesquisa dialog = new IgPesquisa();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public IgPesquisa() {
		setModal(true); 
		setResizable(false);
		setTitle("Pesquisar Despesa");
		setBounds(100, 100, 460, 226);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel pesquisaPanel = new JPanel();
			pesquisaPanel.setBorder(new TitledBorder(null, "Pesquisa", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
			contentPanel.add(pesquisaPanel, BorderLayout.NORTH);
			{
				JLabel itemDeDespesaLabel = new JLabel("Item de despesa:");
				itemDeDespesaLabel.setDisplayedMnemonic(KeyEvent.VK_I);
				pesquisaPanel.add(itemDeDespesaLabel);
			
				itemDeDespesaTextField = new JTextField();
				itemDeDespesaLabel.setLabelFor(itemDeDespesaTextField);
				pesquisaPanel.add(itemDeDespesaTextField);
				itemDeDespesaTextField.setColumns(20);
				
				itemDeDespesaTextField.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						executarPesquisa();
					}
				});
			}
		}
		{
			JPanel pesquisaPorPane = new JPanel();
			pesquisaPorPane.setBorder(new TitledBorder(null, "Pesquisar por", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(pesquisaPorPane);
			
			grupoPesquisa = new ButtonGroup();

			{
				dataRadioButton = new JRadioButton("Data");
				dataRadioButton.setMnemonic(KeyEvent.VK_D);
				pesquisaPorPane.add(dataRadioButton);
				grupoPesquisa.add(dataRadioButton);
			}
			{
				descricaoRadioButton = new JRadioButton("Descrição");
				descricaoRadioButton.setMnemonic(KeyEvent.VK_E);
				descricaoRadioButton.setSelected(true);
				pesquisaPorPane.add(descricaoRadioButton);
				grupoPesquisa.add(descricaoRadioButton);
			}
			{
				valorRadioButton = new JRadioButton("Valor");
				valorRadioButton.setMnemonic(KeyEvent.VK_V);
				pesquisaPorPane.add(valorRadioButton);
				grupoPesquisa.add(valorRadioButton);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Próxima despesa");
				okButton.setMnemonic('P');
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						executarPesquisa();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	
	// Provisório (Apenas para testes)
	private void executarPesquisa() {
		System.out.println("Pesquisando por: " + itemDeDespesaTextField.getText());
		if (dataRadioButton.isSelected()) System.out.println("Filtro: Data");
		if (descricaoRadioButton.isSelected()) System.out.println("Filtro: Descrição");
		if (valorRadioButton.isSelected()) System.out.println("Filtro: Valor");
	}
	
	public String getTextoPesquisa() {
		return itemDeDespesaTextField.getText();
	}
	
	public boolean isPesquisaPorData() {
		return dataRadioButton.isSelected();
	}
	
	public boolean isPesquisaPorDescricao() {
		return descricaoRadioButton.isSelected();
	}
	
	public boolean isPesquisaPorValor() {
		return valorRadioButton.isSelected();
	}
}