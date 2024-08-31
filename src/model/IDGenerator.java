package model;

import java.io.*;

public class IDGenerator implements Serializable {

    @Serial
    private static final long serialVersionUID = 16L;

    private static IDGenerator instancia;
    private int ClienteID;
    private int ProdutoID;
    private int PedidoID;

    private static final int CLIENTE_SUFIXO = 1;
    private static final int PRODUTO_SUFIXO = 2;
    private static final int PEDIDO_SUFIXO = 3;

    private IDGenerator(){
            ClienteID = 0;
            ProdutoID = 0;
            PedidoID = 0;
    }

    public static IDGenerator getInstancia() {
        if (instancia == null){
            instancia = new IDGenerator();
        }
        return instancia;
    }

    public int getNovoClienteID() {
        return (++ClienteID * 10) + CLIENTE_SUFIXO;
    }

    public int getNovoPedidoID() {
        return (++PedidoID * 10) + PEDIDO_SUFIXO;
    }

    public int getNovoProdutoID() {
        return (++ProdutoID * 10) + PRODUTO_SUFIXO;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        instancia = this;
    }
}
