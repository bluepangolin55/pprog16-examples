package session1;

class BinarySearchTree<T extends Comparable<T> > {
	
	class Node {
		T element;
		Node leftChild;
		Node rightChild;
		
		Node(T element){
			this.element = element;
			leftChild = null;
			rightChild = null;
		}
	}
	
	Node root;
	
	BinarySearchTree(){
		root = null;
	}

	boolean insert(T element){
		Node new_node = new Node(element);
		if(root == null){
			root = new_node;
			return true; 
		}

		Node current = root; 
		while(true){
			if(element.compareTo(current.element) < 0){
				if(current.leftChild == null){
					current.leftChild = new_node;
					return true;
				}
				else{
					current = current.leftChild;
				}
			}
			else if(element.compareTo(current.element) > 0){
				if(current.rightChild == null){
					current.rightChild = new_node;
					return true;
				}
				else{
					current = current.rightChild;
				}
			}
			else{
				return false; // the element already exists
			}
		}

	}
	
	boolean contains(T element){
		Node current = root; 
		while(true){
			if(element.compareTo(current.element) < 0){
				if(current.leftChild != null){
					current = current.leftChild;
				}
				else{
					return false;
				}
			}
			else if(element.compareTo(current.element) > 0){
				if(current.rightChild != null){
					current = current.rightChild;
				}
				else{
					return false;
				}
			}
			else{
				return true; // found it
			}
		}
	}

}
