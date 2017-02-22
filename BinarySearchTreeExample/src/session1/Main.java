package session1;

public class Main {

	public static void main(String[] args) {
		
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
		int data[] = {1,2,3,4,5};
		for(int i : data){
			tree.insert(i);
		}
		for(int i : data){
			if(!tree.contains(i)){
				System.out.println("does not contain " + i);
			}
		}
		

		System.out.println("done");
		
		// I want to have 100 unique random numbers in the range [0..1000]
		int n = 100; 
		int[] uniqueRandoms = new int[n];
		
		int counter = 0;
		while(counter < n){
			int number = (int) (Math.random() * 1000);  // why around Math.random*1000 ? 
			uniqueRandoms[counter] = number;
			System.out.print(number + " ");
			counter ++;
		}

	}


	
//		tree = new BinarySearchTree<Integer>();
	
//			if(tree.insert(number)){
//				uniqueRandoms[counter] = number;
//				System.out.print(number + " ");
//				counter ++;
//			}
	
	
}
