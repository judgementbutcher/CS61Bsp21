package bstmap;

import java.util.*;
import java.lang.UnsupportedOperationException;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private BSTNode<K, V> root;

    private class BSTNode<K, V> {
        private K key;
        private V value;
        private BSTNode<K, V> left;
        private BSTNode<K, V> right;
        private int size;

        public BSTNode(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }
    @Override
    public void clear() {

    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return get(root,key);
    }

    private V get(BSTNode<K, V> node, K key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if(cmp > 0) {
            return get(node.right,key);
        } else {
            return node.value;
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(BSTNode<K, V> node) {
        if(node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        // 如果value为null，那么就要删除这个键
        if (value == null) {
            remove(key);
            return;
        }
        root = put(root,key,value);
    }

    private BSTNode<K, V> put(BSTNode<K, V> node, K key, V value) {
        if(node == null) {
            return new BSTNode<K,V>(key,value,1);
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0) {
            node.left = put(node.left,key,value);
        } else if(cmp > 0) {
            node.right = put(node.right,key,value);
        } else {
            node.value = value;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    //想要顺序输出二叉搜索树，这里可以使用树的中序遍历
    public void printInOrder() {
        System.out.println("{Keys, Values}");
        inOrder(root);
    }

    private void inOrder(BSTNode<K, V> node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.println("{" + node.key + ", " + node.value + "}");
        inOrder(node.right);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("ketSet method is not supported");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("move method is not supported");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("remove method is not supported");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("iterator method is not supported");
    }
}
