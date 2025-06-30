package view;

import dao.FuncionárioDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Funcionário;

public class FuncionárioView extends JFrame {
    private JTextField txtCPF, txtNome, txtFuncao, txtSalario;
    private JTable tableFuncionarios;
    private DefaultTableModel tableModel;
    private FuncionárioDAO funcionarioDAO;
    private JButton btnSalvar, btnEditar, btnExcluir, btnLimpar, btnAtualizar;

    public FuncionárioView() {
        funcionarioDAO = new FuncionárioDAO();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        atualizarTabela();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Sistema de Gerenciamento de Funcionários");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Campos de entrada
        txtCPF = new JTextField(15);
        txtNome = new JTextField(20);
        txtFuncao = new JTextField(15);
        txtSalario = new JTextField(10);

        // Botões
        btnSalvar = new JButton("Salvar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");
        btnAtualizar = new JButton("Atualizar Tabela");

        // Estilizando botões
        styleButton(btnSalvar, new Color(46, 125, 50));
        styleButton(btnEditar, new Color(255, 152, 0));
        styleButton(btnExcluir, new Color(244, 67, 54));
        styleButton(btnLimpar, new Color(96, 125, 139));
        styleButton(btnAtualizar, new Color(63, 81, 181));

        // Tabela
        String[] colunas = {"CPF", "Nome", "Função", "Salário"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableFuncionarios = new JTable(tableModel);
        tableFuncionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableFuncionarios.getTableHeader().setReorderingAllowed(false);
        
        // Estilizando tabela
        tableFuncionarios.setRowHeight(25);
        tableFuncionarios.getTableHeader().setBackground(new Color(63, 81, 181));
        tableFuncionarios.getTableHeader().setForeground(Color.WHITE);
        tableFuncionarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tableFuncionarios.setFont(new Font("Arial", Font.PLAIN, 11));
        tableFuncionarios.setGridColor(new Color(224, 224, 224));
        tableFuncionarios.setSelectionBackground(new Color(197, 202, 233));
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de formulário
        JPanel formPanel = createFormPanel();
        
        // Panel da tabela
        JPanel tablePanel = createTablePanel();

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(63, 81, 181)), 
            "Dados do Funcionário", 
            0, 0, 
            new Font("Arial", Font.BOLD, 14), 
            new Color(63, 81, 181)
        ));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        // Linha 1
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(createLabel("CPF:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtCPF, gbc);

        gbc.gridx = 2;
        formPanel.add(createLabel("Nome:"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtNome, gbc);

        // Linha 2
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Função:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtFuncao, gbc);

        gbc.gridx = 2;
        formPanel.add(createLabel("Salário:"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtSalario, gbc);

        // Panel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnLimpar);
        buttonPanel.add(btnAtualizar);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(new Color(33, 33, 33));
        return label;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(63, 81, 181)), 
            "Lista de Funcionários", 
            0, 0, 
            new Font("Arial", Font.BOLD, 14), 
            new Color(63, 81, 181)
        ));

        JScrollPane scrollPane = new JScrollPane(tableFuncionarios);
        scrollPane.setPreferredSize(new Dimension(750, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void setupEventListeners() {
        btnSalvar.addActionListener(e -> salvarFuncionario());
        btnEditar.addActionListener(e -> editarFuncionario());
        btnExcluir.addActionListener(e -> excluirFuncionario());
        btnLimpar.addActionListener(e -> limparCampos());
        btnAtualizar.addActionListener(e -> atualizarTabela());

        // Listener para seleção na tabela
        tableFuncionarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherCamposComSelecao();
            }
        });
    }

    private void salvarFuncionario() {
        if (!validarCampos()) return;

        try {
            Funcionário funcionario = new Funcionário();
            funcionario.setCPF(txtCPF.getText().trim());
            funcionario.setNome(txtNome.getText().trim());
            funcionario.setFunção(txtFuncao.getText().trim());
            funcionario.setSalário(Double.parseDouble(txtSalario.getText().trim()));

            if (funcionarioDAO.insertFuncionário(funcionario)) {
                showMessage("Funcionário salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                atualizarTabela();
            } else {
                showMessage("Erro ao salvar funcionário!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            showMessage("Salário deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarFuncionario() {
        if (!validarCampos()) return;

        try {
            Funcionário funcionario = new Funcionário();
            funcionario.setCPF(txtCPF.getText().trim());
            funcionario.setNome(txtNome.getText().trim());
            funcionario.setFunção(txtFuncao.getText().trim());
            funcionario.setSalário(Double.parseDouble(txtSalario.getText().trim()));

            if (funcionarioDAO.editFuncionário(funcionario)) {
                showMessage("Funcionário editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                atualizarTabela();
            } else {
                showMessage("Erro ao editar funcionário!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            showMessage("Salário deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirFuncionario() {
        String cpf = txtCPF.getText().trim();
        if (cpf.isEmpty()) {
            showMessage("Selecione um funcionário para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir este funcionário?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (resposta == JOptionPane.YES_OPTION) {
            if (funcionarioDAO.removeFuncionário(cpf)) {
                showMessage("Funcionário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                atualizarTabela();
            } else {
                showMessage("Erro ao excluir funcionário!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        txtCPF.setText("");
        txtNome.setText("");
        txtFuncao.setText("");
        txtSalario.setText("");
        txtCPF.setEditable(true);
        tableFuncionarios.clearSelection();
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Funcionário> funcionarios = funcionarioDAO.readFuncionários();
        
        for (Funcionário funcionario : funcionarios) {
            Object[] row = {
                funcionario.getCPF(),
                funcionario.getNome(),
                funcionario.getFunção(),
                String.format("R$ %.2f", funcionario.getSalário())
            };
            tableModel.addRow(row);
        }
    }

    private void preencherCamposComSelecao() {
        int selectedRow = tableFuncionarios.getSelectedRow();
        if (selectedRow >= 0) {
            txtCPF.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtNome.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtFuncao.setText(tableModel.getValueAt(selectedRow, 2).toString());
            
            String salarioStr = tableModel.getValueAt(selectedRow, 3).toString();
            salarioStr = salarioStr.replace("R$ ", "").replace(",", ".");
            txtSalario.setText(salarioStr);
            
            txtCPF.setEditable(false); // CPF não pode ser editado
        }
    }

    private boolean validarCampos() {
        if (txtCPF.getText().trim().isEmpty()) {
            showMessage("CPF é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtCPF.requestFocus();
            return false;
        }

        if (txtNome.getText().trim().isEmpty()) {
            showMessage("Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtNome.requestFocus();
            return false;
        }

        if (txtFuncao.getText().trim().isEmpty()) {
            showMessage("Função é obrigatória!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtFuncao.requestFocus();
            return false;
        }

        if (txtSalario.getText().trim().isEmpty()) {
            showMessage("Salário é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtSalario.requestFocus();
            return false;
        }

        try {
            double salario = Double.parseDouble(txtSalario.getText().trim());
            if (salario < 0) {
                showMessage("Salário deve ser um valor positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
                txtSalario.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showMessage("Salário deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtSalario.requestFocus();
            return false;
        }

        return true;
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }


}