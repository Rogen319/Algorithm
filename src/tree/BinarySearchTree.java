package tree;

import javax.security.auth.x500.X500Principal;

/**
 * �����������ʵ�֣�����search��delete��insert��maximum��minimum��predecessor�Լ�successor�ȶ�̬���ϲ�����
 * @author �ĺ�
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
	 * �ڲ��ڵ���
	 * @author �ĺ�
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
	 * ����Ԫ��
	 * @param t ������Ԫ�ص�key value
	 * @return ����ɹ�����true�����򷵻�false
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
	 * ����
	 * @param t �����ҵ�key value
	 * @return Ԫ�ض�Ӧ�ڵ�
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
	 * ����ָ���������������Ԫ��
	 * @return �������Ԫ�ؽڵ�
	 */
	public Node<T> maximum(Node<T> node){
		Node<T> cur = node;
		while(cur.getRight() != null){
			cur = cur.getRight();
		}
		return cur;
	}
	
	/**
	 * ����ָ��������������СԪ��
	 * @return ������СԪ�ؽڵ�
	 */
	public Node<T> minimun(Node<T> node){
		Node<T> cur = node;
		while(cur.getLeft() != null){
			cur = cur.getLeft();
		}
		return cur;	
	}
	
	/**
	 * ǰ��
	 * @param node ��������ǰ���Ľڵ�
	 * @return �ڵ�ǰ��
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
	 * ���
	 * @param node ���������̵Ľڵ�
	 * @return �ڵ���
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
	 * ɾ���ڵ�
	 * @param t Ҫ��ɾ���� key value
	 * @return ��ɾ���Ľڵ�
	 */
	public Node<T> delete(T t){
		Node<T> z = search(t);
		Node<T> y = null;
		//ȷ��Ҫ��ɾ���Ľڵ�
		if(z.getLeft() == null || z.getRight() == null){
			y  = z;
		}
		else{
			y = successor(z);
		}
		//���Ҫ��ɾ���ڵ���ӽڵ�
		Node<T> x = null;
		if(y.getLeft() != null){
			x = y.getLeft();
		}
		else{
			x = y.getRight();
		}
		//�ڱ�ɾ���ĸ��ڵ���ӽڵ�֮�佨����ϵ���ﵽɾ���ڵ��Ч��
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
	 * �������
	 * @param node�ڵ�
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
