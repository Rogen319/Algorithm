package tree;

import javax.security.auth.x500.X500Principal;

/**
 * 二叉查找树的实现，包括search，delete，insert，maximum，minimum，predecessor以及successor等动态集合操作。
 * @author 文海
 *
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<T>> {
	private Node<T> root;
	
	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	/**
	 * 内部节点类
	 * @author 文海
	 *
	 * @param <T>
	 */
	private static class Node<T extends Comparable<T>>{
		private Node<T> parent;
		private Node<T> left;
		private Node<T> right;
		
		private T value;

		public Node<T> getParent() {
			return parent;
		}

		public void setParent(Node<T> parent) {
			this.parent = parent;
		}

		public Node<T> getLeft() {
			return left;
		}

		public void setLeft(Node<T> left) {
			this.left = left;
		}

		public Node<T> getRight() {
			return right;
		}

		public void setRight(Node<T> right) {
			this.right = right;
		}

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}
		
	}
	
	/**
	 * 插入元素
	 * @param t 待插入元素的key value
	 * @return 插入成功返回true，否则返回false
	 */
	public boolean insert(T t){
		Node<T> y = null;
		Node<T> x = root;
		Node<T> z = new Node<T>();
		z.setValue(t);
		
		while(x != null){
			y = x;
			if(t.compareTo(x.getValue()) < 0){
				x = x.getLeft();
			}
			else if(t.compareTo(x.getValue()) > 0){
				x = x.getRight();
			}
			else{
				return false;
			}
		}
		z.setParent(y);
		if(y == null)
			root = z;
		else if(t.compareTo(y.getValue())< 0) 
			y.setLeft(z);
		else 
			y.setRight(z);
		return true;
	}
	
	/**
	 * 查找
	 * @param t 待查找的key value
	 * @return 元素对应节点
	 */
	public Node<T> search(T t){
		Node<T> cur = root;
		while(cur != null){
			if(t.compareTo(cur.getValue()) < 0){
				cur = cur.getLeft();
			}
			else if(t.compareTo(cur.getValue()) > 0){
				cur = cur.getRight();
			}
			else {
				break;
			}
		}
		return cur;
	}
	
	/**
	 * 查找指定树（子树）最大元素
	 * @return 返回最大元素节点
	 */
	public Node<T> maximum(Node<T> node){
		Node<T> cur = node;
		while(cur.getRight() != null){
			cur = cur.getRight();
		}
		return cur;
	}
	
	/**
	 * 查找指定树（子树）最小元素
	 * @return 返回最小元素节点
	 */
	public Node<T> minimun(Node<T> node){
		Node<T> cur = node;
		while(cur.getLeft() != null){
			cur = cur.getLeft();
		}
		return cur;	
	}
	
	/**
	 * 前驱
	 * @param node 待查找其前驱的节点
	 * @return 节点前驱
	 */
	public Node<T> predecessor(Node<T> node){
		if(node.getLeft() != null){
			return maximum(node.getLeft());
		}
		Node<T> y = node.getParent();
		while(y != null && node == y.getLeft()){
			node = y;
			y = y.getParent();
		}
		return y;		
	}
	
	/**
	 * 后继
	 * @param node 待查找其后继的节点
	 * @return 节点后继
	 */
	public Node<T> successor(Node<T> node){
		if(node.getRight() != null){
			return minimun(node.getRight());
		}
		Node<T> y = node.getParent();
		while(y != null && node == y.getRight()){
			node = y;
			y = y.getParent();
		}
		return y;
	}
	
	/**
	 * 删除节点
	 * @param t 要被删除的 key value
	 * @return 被删除的节点
	 */
	public Node<T> delete(T t){
		Node<T> z = search(t);
		Node<T> y = null;
		//确定要被删除的节点
		if(z.getLeft() == null || z.getRight() == null){
			y  = z;
		}
		else{
			y = successor(z);
		}
		//获得要被删除节点的子节点
		Node<T> x = null;
		if(y.getLeft() != null){
			x = y.getLeft();
		}
		else{
			x = y.getRight();
		}
		//在被删除的父节点和子节点之间建立联系，达到删除节点的效果
		if(x != null){
			x.setParent(y.getParent());
		}
		if(y.getParent() == null){
			root = x;
		}
		else if(y == y.getParent().getLeft()){
			y.getParent().setLeft(x);
		}
		else{
			y.getParent().setRight(x);
		}
		if(y != z){
			z.setValue(y.getValue());
		}
		return z;
	}
	
	/**
	 * 中序遍历
	 * @param node节点
	 */
	public void inorder(Node<T> node){
		if(node != null){
			inorder(node.getLeft());
			System.out.print(node.getValue() + "	");
			inorder(node.getRight());
		}
	}
	
	public static void main(String[] args){
		BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
		binarySearchTree.insert(10);
		binarySearchTree.insert(20);
		binarySearchTree.insert(30);
		binarySearchTree.insert(5);
		binarySearchTree.insert(7);
		binarySearchTree.insert(3);
		binarySearchTree.insert(9);
		System.out.println("Tree:");
		binarySearchTree.inorder(binarySearchTree.getRoot());
		binarySearchTree.delete(5);
		System.out.println("\nThe max value is: " + 
		binarySearchTree.maximum(binarySearchTree.getRoot()).getValue());
		System.out.println("The min value is: " + 
		binarySearchTree.minimun(binarySearchTree.getRoot()).getValue());
		System.out.println("Tree:");
		binarySearchTree.inorder(binarySearchTree.getRoot());
	}
}
