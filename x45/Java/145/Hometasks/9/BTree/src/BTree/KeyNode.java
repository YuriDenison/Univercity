package BTree;


class KeyNode<Type> {
    private int key;
    private Object data;

    public KeyNode(int key, Type data) {
        this.key = key;
        this.data = data;
    }

    public int getKey() {
        return key;
    }

    public Object getData() {
        return data;
    }
}
