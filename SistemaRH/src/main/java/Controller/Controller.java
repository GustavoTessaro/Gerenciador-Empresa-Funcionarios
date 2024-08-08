/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import Model.Conexao;
import Model.Empresa;
import Model.Funcionario;
import View.Janela;
import View.LoginUsuario;
import View.MenuEmpresas;
import View.MenuFuncionarios;
import java.awt.BorderLayout;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author alunolages
 */
public class Controller {
    
    //Objetos das views.
    private LoginUsuario p1;
    private MenuEmpresas p2;
    private MenuFuncionarios p3;
    private Janela janela;
    private String url;
    private String usuario;
    private String senha;
    private String driver;
    private int idUsuario;
    private int idEmpresa;
    private Conexao conexao;
    private ArrayList <Empresa> empresas = new ArrayList();
    private ArrayList <Funcionario> funcionarios = new ArrayList();
    
    public Controller() {
        janela = new Janela();
        janela.setLocationRelativeTo(null); // Centraliza a janela na tela
        janela.setVisible(true); // Torna a janela visível
    }
    
    public void mostraTela(JPanel newPanel){
        this.janela.getContentPane().removeAll();       //Remove o conteúdo atual do frame.
        this.janela.add(newPanel, BorderLayout.CENTER); //Adiciona o novo painel ao frame.
        this.janela.pack();                             //Redimensiona o frame.
        janela.setLocationRelativeTo(null);
    }

    public void start(){
        LoginUsuario();
        this.janela.setVisible(true);
        
        this.url = "jdbc:mysql://localhost:3306/bancodedadosdofrontend";
        this.usuario = "root";
        this.senha = "";
        this.driver = "com.mysql.cj.jdbc.Driver";

        this.conexao = new Conexao();
        
        conexao.conecta(url, usuario, senha, driver);
        
    }

    public ArrayList<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(ArrayList<Empresa> empresas) {
        this.empresas = empresas;
    }

    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Conexao getConexao() {
        return conexao;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void LoginUsuario(){
        this.p1 = new LoginUsuario();
        this.mostraTela(p1);
    }
    
    public void LoginUsuarioVoltar(){
        conexao.desconecta();
        this.p1 = new LoginUsuario();
        this.mostraTela(p1);
        this.conexao = new Conexao();
        conexao.conecta(url, usuario, senha, driver);
        this.empresas.clear();
    }
    
    public void VoltarExcluirUsuario(){
        conexao.desconecta();
        this.p1 = new LoginUsuario();
        this.mostraTela(p1);
        this.conexao = new Conexao();
        conexao.conecta(url, usuario, senha, driver);
        this.empresas.clear();
        this.funcionarios.clear();
    }
    
    public void MenuEmrpesas(String nomeP, String senhaP){
        this.p2 = new MenuEmpresas();
        this.mostraTela(p2);
        this.idUsuario = conexao.obterIdUsuario(nomeP, senhaP);
        this.empresas = conexao.obterEmpresasPorUsuario(this.idUsuario);
    }
    
    public void BT_VoltarMenuFuncionarios(){
        this.p2 = new MenuEmpresas();
        this.mostraTela(p2);
        this.empresas = conexao.obterEmpresasPorUsuario(this.idUsuario);
        this.funcionarios.clear();
    }
    
    public void MenuFuncionarios(String nomeP, String CNPJP){
        this.idEmpresa = conexao.obterIdEmpresa(nomeP, CNPJP);
        this.funcionarios = conexao.obterFuncionariosPorEmpresa(this.idEmpresa);
        this.p3 = new MenuFuncionarios();
        this.mostraTela(p3);
    }
    
    public void CadastrarUsuario(String nomeDoUsuario, String senhaDoUsuario){
        conexao.insereUsuario(nomeDoUsuario, senhaDoUsuario);
    }
    
    public boolean BotaoLogar(String nome, String senha){
        boolean verifica;
        verifica = conexao.verificaUsuario(nome, senha);
        return verifica;
    }
    
    public void CadastrarEmpresa(String nomeDaEmpresa, String CNPJDaEmpresa){
        conexao.insereEmpresa(nomeDaEmpresa, CNPJDaEmpresa, idUsuario);
        this.empresas.clear();
        this.empresas = conexao.obterEmpresasPorUsuario(this.idUsuario);
    }
    
    public void CadastrarFuncionario(String nome, Date dataNascimento, String CPF, String cargo, double salario, String beneficios){
        conexao.insereFuncionario(nome, dataNascimento, CPF, cargo, salario, beneficios, idEmpresa);
        this.funcionarios.clear();
        this.funcionarios = conexao.obterFuncionariosPorEmpresa(this.idEmpresa);
    }
    
    public boolean BotaoLogarEmpresa(String nome, String CNPJ){
        boolean verifica;
        verifica = conexao.verificaEmpresa(nome, CNPJ);
        return verifica;
    }
    
    public  String dateParaString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
    
    public  Date stringParaDate(String dateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = inputFormat.parse(dateStr);
            String formattedDate = outputFormat.format(utilDate);
            return Date.valueOf(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void excluirUsuario(){
        String nome = conexao.obterNomeUsuario(this.idUsuario);
        int verifica = JOptionPane.showConfirmDialog(null, "Você quer realmente excluir o usuario " + nome + "?");
        
        if (verifica == JOptionPane.YES_OPTION) {
            verifica = verifica = JOptionPane.showConfirmDialog(null, "Você tem CERTEZA que realmente quer excluir o usuario " + nome + "?");
            if (verifica == JOptionPane.YES_OPTION){
                conexao.excluirUsuario(this.idUsuario);
                VoltarExcluirUsuario();
            }
        }
    }
    
    public void excluirEmpresa(String nome, String CNPJ){
        int verifica = JOptionPane.showConfirmDialog(null, "Você quer realmente excluir a empresa " + nome + " com o CNPJ " + CNPJ + "?");
        
        if (verifica == JOptionPane.YES_OPTION) {
            verifica = verifica = JOptionPane.showConfirmDialog(null, "Você tem CERTEZA que realmente quer excluir a empresa " + nome + " com o CNPJ " + CNPJ + "?");
            if (verifica == JOptionPane.YES_OPTION){
                conexao.excluirEmpresa(nome, CNPJ, this.idUsuario);
                this.empresas.clear();
                this.empresas = conexao.obterEmpresasPorUsuario(this.idUsuario);
            }
        }
    }
    
    public void excluirFuncionario(String nome, String CPF){
        int verifica = JOptionPane.showConfirmDialog(null, "Você quer realmente excluir o funcionario " + nome + " com o CPF " + CPF + "?");
        
        if (verifica == JOptionPane.YES_OPTION) {
            verifica = verifica = JOptionPane.showConfirmDialog(null, "Você tem CERTEZA que realmente quer excluir o funcionario " + nome + " com o CPF " + CPF + "?");
            if (verifica == JOptionPane.YES_OPTION){
                conexao.excluirFuncionario(nome, CPF, this.idEmpresa);
                this.funcionarios.clear();
                this.funcionarios = conexao.obterFuncionariosPorEmpresa(this.idEmpresa);
            }
        }
    }

    public void editarFuncionario(String nome, String CPF, String cargo, double salario, String beneficios) {
        int verifica = JOptionPane.showConfirmDialog(null, "Você quer realmente editar o funcionario " + nome + " com o CPF " + CPF + "?");      
        if (verifica == JOptionPane.YES_OPTION) {
                conexao.atualizarFuncionario(nome, CPF, idEmpresa, cargo, salario, beneficios);
                this.funcionarios.clear();
                this.funcionarios = conexao.obterFuncionariosPorEmpresa(this.idEmpresa);
        }
    }

}
