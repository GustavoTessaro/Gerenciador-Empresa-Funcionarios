/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.awt.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author alunolages
 */
public class Conexao {
    
    private String url;
    private String usuario;
    private String senha;
    private String driver;
    private Connection conexao;
    public Statement statement;
    public static ResultSet resultset;

    public void conecta(String urlP, String usuarioP, String senhaP, String driverP) {
        this.url = urlP;
        this.usuario = usuarioP;
        this.senha = senhaP;
        this.driver = driverP;
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException Fonte) {
            JOptionPane.showMessageDialog(null, "Driver nao localizado");
        } catch (SQLException Fonte) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor MYSQL", "ATENÇAO", (2));
        }
    }
    
    public void desconecta() {
        try {
            conexao.close();
        } catch (SQLException fech) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexao com o banco de dados" + fech);
        }
    }

    public void executaSQL(String sql) {
        try {
            statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultset = statement.executeQuery(sql);
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "Nao foi possível executar o comando sql"
                    + "" + sqlex + "O comando passado foi" + sql);
        }
    }
    
    public boolean verificaUsuario(String nome) {
        String sql = "SELECT * FROM Usuario WHERE nome = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Retorna true se encontrar um usuário com o mesmo nome
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar usuário: " + e.getMessage());
            return false;
        }
    }
    
    public boolean verificaUsuario(String nome, String senha) {
        String sql = "SELECT * FROM Usuario WHERE nome = ? AND senha = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Retorna true se encontrar um usuário com o mesmo nome e senha
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar usuário: " + e.getMessage());
            return false;
        }
    }

    // Método para inserir um novo usuário
    public void insereUsuario(String nome, String senha) {
        if (verificaUsuario(nome)) {
            JOptionPane.showMessageDialog(null, "Usuário já existe!");
        } else {
            String sql = "INSERT INTO Usuario (nome, senha) VALUES (?, ?)";
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setString(2, senha);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuário inserido com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir usuário: " + e.getMessage());
            }
        }
    }
    
    public boolean verificaEmpresa(String nome, String cnpj) {
    String sql = "SELECT * FROM Empresa WHERE nome = ? AND cnpj = ?";
    try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
        pstmt.setString(1, nome);
        pstmt.setString(2, cnpj);
        ResultSet rs = pstmt.executeQuery();
        return rs.next(); // Retorna true se encontrar uma empresa com o mesmo nome e CNPJ
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao verificar empresa: " + e.getMessage());
        return false;
    }
}
    
    public boolean verificaFuncionario(String nome, Date dataNascimento, String cpf, int idEmpresa) {
        String sql = "SELECT * FROM Funcionarios WHERE nome = ? AND data_nascimento = ? AND cpf = ? AND empresa_id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setDate(2, dataNascimento);
            pstmt.setString(3, cpf);
            pstmt.setInt(4, idEmpresa);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Retorna true se encontrar um funcionário com os mesmos dados
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar funcionário: " + e.getMessage());
            return false;
        }
    }

    
    public void insereEmpresa(String nome, String cnpj, int usuarioId) {
        if (verificaEmpresa(nome, cnpj)) {
            JOptionPane.showMessageDialog(null, "Empresa já cadastrada com este CNPJ!");
        } else {
            String sql = "INSERT INTO Empresa (nome, cnpj, usuario_id) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setString(2, cnpj);
                pstmt.setInt(3, usuarioId);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Empresa cadastrada com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar empresa: " + e.getMessage());
            }
        }
    }
    
    public void insereFuncionario(String nome, Date dataNascimento, String cpf, String cargo, double salario, String beneficios, int idEmpresa) {
        if (verificaFuncionario(nome, dataNascimento, cpf,idEmpresa)) {
            JOptionPane.showMessageDialog(null, "Funcionário já cadastrado!");
        } else {
            String sql = "INSERT INTO Funcionarios (nome, data_nascimento, cpf, cargo, salario, beneficios, empresa_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setDate(2, dataNascimento);
                pstmt.setString(3, cpf);
                pstmt.setString(4, cargo);
                pstmt.setDouble(5, salario);
                pstmt.setString(6, beneficios);
                pstmt.setInt(7, idEmpresa);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar funcionário: " + e.getMessage());
            }
        }
    }

    public int obterIdUsuario(String nome, String senha) {
    String sql = "SELECT id FROM Usuario WHERE nome = ? AND senha = ?";
    try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
        pstmt.setString(1, nome);
        pstmt.setString(2, senha);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id"); // Retorna o ID do usuário se encontrado
        } else {
            return -1; // Retorna -1 se o usuário não for encontrado
        }
    } catch (SQLException e) {
        return -1;
    }
}
    
    public int obterIdEmpresa(String nome, String CNPJ) {
    String sql = "SELECT id FROM Empresa WHERE nome = ? AND CNPJ = ?";
    try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
        pstmt.setString(1, nome);
        pstmt.setString(2, CNPJ);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id"); // Retorna o ID do usuário se encontrado
        } else {
            return -1; // Retorna -1 se o usuário não for encontrado
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar ID da Empresa: " + e.getMessage());
        return -1;
    }
}
    
    public ArrayList<Empresa> obterEmpresasPorUsuario(int usuarioId) {
        ArrayList<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT * FROM Empresa WHERE usuario_id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cnpj = rs.getString("cnpj");
                Empresa empresa = new Empresa(id, nome, cnpj);
                empresas.add(empresa);
            }
        } catch (SQLException e) {

        }
        return empresas;
    }
    
    public ArrayList<Funcionario> obterFuncionariosPorEmpresa(int empresaId) {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM Funcionarios WHERE empresa_id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, empresaId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                Date dataNascimento = rs.getDate("data_nascimento");
                String cpf = rs.getString("cpf");
                String cargo = rs.getString("cargo");
                double salario = rs.getDouble("salario");
                String beneficios = rs.getString("beneficios");
                Funcionario funcionario = new Funcionario(id, nome, cpf, cargo, beneficios, dataNascimento, salario);
                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar funcionários: " + e.getMessage());
        }
        return funcionarios;
    }

    public void excluirUsuario(int id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir usuário: " + e.getMessage());
        }
    }
    
    public String obterNomeUsuario(int id) {
        String nome = null;
        String sql = "SELECT nome FROM Usuario WHERE id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                nome = rs.getString("nome");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar o nome do usuário: " + e.getMessage());
        }
        return nome;
    }
    
    public void excluirEmpresa(String nome, String cnpj, int idUsuario) {
        String sql = "DELETE FROM Empresa WHERE nome = ? AND cnpj = ? AND usuario_id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, cnpj);
            pstmt.setInt(3, idUsuario);
            int linhasAfetadas = pstmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Empresa excluída com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma empresa encontrada com o nome e CNPJ fornecidos.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir empresa: " + e.getMessage());
        }
    }
    
    public void excluirFuncionario(String nome, String cpf, int idEmpresa) {
        String sql = "DELETE FROM Funcionarios WHERE nome = ? AND cpf = ? AND empresa_id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, cpf);
            pstmt.setInt(3, idEmpresa);
            int linhasAfetadas = pstmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Funcionário excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum funcionário encontrado com os dados fornecidos.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir funcionário: " + e.getMessage());
        }
    }
    
    public void atualizarFuncionario(String nome, String cpf, int idEmpresa, String novoCargo, double novoSalario, String novosBeneficios) {
        String sql = "UPDATE Funcionarios SET cargo = ?, salario = ?, beneficios = ? WHERE nome = ? AND cpf = ? AND empresa_id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, novoCargo);
            pstmt.setDouble(2, novoSalario);
            pstmt.setString(3, novosBeneficios);
            pstmt.setString(4, nome);
            pstmt.setString(5, cpf);
            pstmt.setInt(6, idEmpresa);
            
            int linhasAfetadas = pstmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum funcionário encontrado com os dados fornecidos.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar funcionário: " + e.getMessage());
        }
    }
    
}
