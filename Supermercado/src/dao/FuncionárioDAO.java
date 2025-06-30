package dao;

import controller.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Funcionário;

public class FuncionárioDAO {
    
    public boolean insertFuncionário(Funcionário funcionario) {
        String sql = "INSERT INTO Funcionário (CPF, Nome, Função, Salário) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, funcionario.getCPF());
            statement.setString(2, funcionario.getNome());
            statement.setString(3, funcionario.getFunção());
            statement.setDouble(4, funcionario.getSalário());
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFuncionário(String CPF) {
        String sql = "DELETE FROM Funcionário WHERE CPF=?";
        
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, CPF);
            
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editFuncionário(Funcionário funcionario) {
        String sql = "UPDATE Funcionário SET Nome=?, Função=?, Salário=? WHERE CPF=?";
        
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, funcionario.getNome());
            statement.setString(2, funcionario.getFunção());
            statement.setDouble(3, funcionario.getSalário());
            statement.setString(4, funcionario.getCPF());
            
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Funcionário> readFuncionários() {
        List<Funcionário> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM Funcionário";
        
        try (Connection conn = ConexaoDB.conectar();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Funcionário funcionario = new Funcionário();
                funcionario.setCPF(resultSet.getString("CPF"));
                funcionario.setNome(resultSet.getString("Nome"));
                funcionario.setFunção(resultSet.getString("Função"));
                funcionario.setSalário(resultSet.getDouble("Salário"));
                
                funcionarios.add(funcionario);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return funcionarios;
    }
    
    public Funcionário buscarPorCPF(String CPF) {
        String sql = "SELECT * FROM Funcionário WHERE CPF=?";
        
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, CPF);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Funcionário funcionario = new Funcionário();
                funcionario.setCPF(resultSet.getString("CPF"));
                funcionario.setNome(resultSet.getString("Nome"));
                funcionario.setFunção(resultSet.getString("Função"));
                funcionario.setSalário(resultSet.getDouble("Salário"));
                return funcionario;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}