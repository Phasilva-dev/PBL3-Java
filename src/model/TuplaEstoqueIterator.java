package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public class TuplaEstoqueIterator implements Iterator<TuplaEstoque>, Serializable {

    @Serial
    private static final long serialVersionUID = 23L;
    private final Iterator<Map.Entry<Integer, TuplaEstoque>> iterator;
    public TuplaEstoqueIterator(Map<Integer, TuplaEstoque> produtos) {
        this.iterator = produtos.entrySet().iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public TuplaEstoque next() {
        return iterator.next().getValue();
    }
}
