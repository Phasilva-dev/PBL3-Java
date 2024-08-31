package model;

import model.exception.*;

import java.io.*;
import java.util.*;

public class ControllerLoja implements Serializable, ControllerLojaInterface {
    @Serial
    private static final long serialVersionUID = 13L;
    private static ControllerLojaInterface instance;
    private IDGenerator idGenerator;
    private transient Usuario logado;
    private EstoqueInterface estoque;
    private Map<String,Usuario> usuarios;

    private ControllerLoja (){
        this.logado = null;
        this.usuarios = new HashMap<>();
        this.estoque = new Estoque();
        this.usuarios.put(Dono.getInstance().login, Dono.getInstance());
        this.idGenerator = IDGenerator.getInstancia();
    }

    @Override
    public IDGenerator getIDGen() {
        return idGenerator;
    }

    public static ControllerLojaInterface getInstance(){
        if (instance == null) {
            instance = new ControllerLoja();
        }
        return instance;
    }

    @Override
    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    @Override
    public EstoqueInterface getEstoque() {
        return estoque;
    }
    //Metodos gerais


    @Override
    public Usuario getLogado() {
        return logado;
    }

    @Override
    public Usuario cadastrarCliente(String nome, String login, String senha, String endereco, String email)
    throws UsuarioNaoCadastrouException {
        if (nome.isBlank() || login.isBlank() || login.equals(Dono.getInstance().login)
        || senha.isBlank() || endereco.isBlank()){
            throw new UsuarioNaoCadastrouException("ALgum dos campos esta invalido");
        }
        if (usuarios.containsKey(login)){
            throw new UsuarioNaoCadastrouException("Login ja existente");
        }
        Cliente cliente = new Cliente(nome,login,senha,endereco,email);
        usuarios.put(login,cliente);
        return cliente;
    }

    @Override
    public void fazerLogin(String login, String senha) throws LoginFalhouException {
        if (!usuarios.containsKey(login)){
            throw new LoginFalhouException("Usuario inexistente");
        }
        if (usuarios.get(login).getSenha().equals(senha)){
            logado = usuarios.get(login);
        }
    }
    @Override
    public Iterator<TuplaEstoque> listarTodosProdutos() {
            return estoque.listarTodosProdutos();
    }

    @Override
    public PedidoInterface buscarPedido(int id){
        if (logado!= null && logado instanceof Cliente){
            return ((Cliente) logado).buscarPedido(id);
        } else if (logado == Dono.getInstance())
            return ((Dono) logado).buscarPedido(id);
        throw new IllegalStateException("Faca login como Cliente");
    }

    @Override
    public Iterator<PedidoInterface> listarTodosPedidos() {
        if (logado!= null && logado instanceof Dono) {
            return ((Dono)logado).listarTodosPedidos();
        } else if (logado instanceof Cliente) {
            return ((Cliente) logado).listarTodosPedidos();
        }
        throw new IllegalStateException("Faca login");
    }
    @Override
    public TuplaEstoque buscarProduto(int id){
        if (logado!= null){
            return estoque.getProduto(id);
        }
        throw new IllegalStateException("Faca login");
    }


    //Metodos do Usuario


    @Override
    public void adicionarPagamentoCartao(String numeroCartao, String titular, String validade, String cvv)
            throws PagamentoNaoCadastrouException {
        if (logado!= null && logado instanceof Cliente){
            ((Cliente) logado).criarPagamentoCartao(numeroCartao,titular,validade,cvv);
            return;
        }
        throw new IllegalStateException("Faca login como Cliente");

    }
    @Override
    public void adicionarPagamentoPaypal(String email, String senha)
            throws PagamentoNaoCadastrouException {
        if (logado!= null && logado instanceof Cliente){
            ((Cliente) logado).criarPagamentoPaypal(email,senha);
            return;
        }
        throw new IllegalStateException("Faca login como Cliente");

    }
    @Override
    public void adicionarPagamentoTransferenciaBancaria(String banco, String agencia, String conta)
            throws PagamentoNaoCadastrouException {
        if (logado!= null && logado instanceof Cliente){
            ((Cliente) logado).criarPagamentoTransferenciaBancaria(banco,agencia,conta);
            return;
        }
        throw new IllegalStateException("Faca login como Cliente");
    }
    @Override
    public void removerFormaDePagamento(PagamentoStrategy pagamentoStrategy) {
        if (logado!= null && logado instanceof Cliente){
            ((Cliente) logado).removerFormaDePagamento(pagamentoStrategy);
            return;
        }
            throw new IllegalStateException("Faca login como Cliente");
    }
    @Override
    public Iterator<PagamentoStrategy> listarTodasFormasDePagamentoCliente() {
        if (logado!= null && logado instanceof Cliente){
            return ((Cliente) logado).listarTodasFormasDePagamento();
        }
        throw new IllegalStateException("Faca login como Cliente");
    }

    @Override
    public Iterator<String> listarTodosEnderecosCliente()  {
        if (logado!= null && logado instanceof Cliente){
            return ((Cliente) logado).listarTodosMeusEnderecos();
        }
        throw new IllegalStateException("Faca login como Cliente");
    }
    @Override
    public void adicionarAoCarrinho(EstoqueInterface estoque, Produto produto, Integer quantidade)
            throws ExcedeuEstoqueException, ProdutoNaoCadastrouException {
        if (logado!= null && logado instanceof Cliente){
            ((Cliente) logado).adicionarAoCarrinho(estoque,produto,quantidade);
            return;
        }
        throw new IllegalStateException("Faca login como Cliente");
    }
    @Override
    public void removerDoCarrinho(Produto produto) {
        if (logado!= null && logado instanceof Cliente){
            ((Cliente) logado).removerDoCarrinho(produto);
            return;
        }
        throw new IllegalStateException("Faca login como Cliente");
    }
    @Override
    public void efetuarCompra(PagamentoStrategy formaDePagamento, String endereco)
            throws EstoqueNegativoException {
        if (logado!= null && logado instanceof Cliente){
            ((Cliente) logado).efetuarCompra(formaDePagamento,estoque,endereco);
            return;
        }
        throw new IllegalStateException("Faca login como Cliente");
    }
    @Override
    public void adicionarEndereco(String endereco)  {
        if (logado!= null && logado instanceof Cliente){
            ((Cliente) logado).adicionarEndereco(endereco);
            return;
        }
        throw new IllegalStateException("Faca login como Cliente");
    }

    @Override
    public void removerEndereco(String endereco) {
        if (logado!= null && logado instanceof Cliente){
            ((Cliente) logado).removerEndereco(endereco);
            return;
        }
        throw new IllegalStateException("Faca login como Cliente");
    }


    //Metodos do Dono


    @Override
    public void adicionarProduto(String tipo, String nome, double valor, int quantidadeDeProdutos)
            throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        if (logado!= null && logado instanceof Dono) {
            ((Dono) logado).adicionarProduto(tipo,nome,valor,quantidadeDeProdutos,estoque);
            return;
        }
        throw new IllegalStateException("Faca login como o dono");
    }
    @Override
    public void removerProduto(Produto produto) {
        if (logado!= null && logado instanceof Dono) {
            ((Dono) logado).removerProduto(produto,estoque);
            return;
        }
        throw new IllegalStateException("Faca login como o dono");
    }
    @Override
    public void gerenciarEstoque(Produto produto, int quantidadeDeProdutos)
            throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        if (logado!= null && logado instanceof Dono) {
            ((Dono) logado).gerenciarEstoque(produto,quantidadeDeProdutos,estoque);
            return;
        }
        throw new IllegalStateException("Faca login como o dono");
    }
    @Override
    public void atualizarPedido(PedidoInterface pedido) throws PedidoEncerradoException {
        if (logado!= null && logado instanceof Dono) {
            ((Dono) logado).atualizarPedido(pedido);
            return;
        }
        throw new IllegalStateException("Faca login como o dono");
    }

    @Override
    public Iterator<ClienteInterface> listarTodosClientes() {
        if (logado!= null && logado instanceof Dono) {
            List<ClienteInterface> clientes = new ArrayList<>();
            Iterator<Usuario> iterator = usuarios.values().iterator();

            while (iterator.hasNext()) {
                Usuario usuario = iterator.next();
                if (usuario instanceof ClienteInterface) {
                    clientes.add((ClienteInterface) usuario);
                }
            }

            return clientes.iterator();
        }
        throw new IllegalStateException("Faca login como o dono");
    }
    @Override
    public ClienteInterface buscarCliente(String cliente)  {
        if (logado!= null && logado instanceof Dono && !cliente.equals("admin")) {
            return (ClienteInterface) usuarios.get(cliente);
        } else if (logado instanceof Cliente){
            throw new IllegalStateException("Faca login como o dono");
        }
        throw new IllegalArgumentException("Cliente inexistente, insira um login valido");
    }


    // Método para salvar os dados
    @Override
    public void salvarDados(String diretorio, String arquivo) {
        File dir = new File(diretorio);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String caminho = diretorio + File.separator + arquivo;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(instance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar os dados
    @Override
    public void carregarDados(String diretorio, String arquivo) {
        String caminho = diretorio + File.separator + arquivo;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            ControllerLoja loadedData = (ControllerLoja) ois.readObject();
            instance = loadedData;
            this.usuarios = loadedData.getUsuarios();
            this.estoque = loadedData.getEstoque();
            idGenerator = loadedData.getIDGen();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
