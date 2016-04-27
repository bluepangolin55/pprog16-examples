import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Random;

import org.junit.Test;

public class CorrectnessTest {
	
	final static int ARRAYSIZE = 20_000;

	@Test
	public void sortTest(){
		Random r = new Random(System.currentTimeMillis());
		
		Integer[] testArray = new Integer[ARRAYSIZE];

		// this is used for checking that no numbers are lost during sorting
		HashSet<Integer> elements = new HashSet<>();
		
		for(int i = 0; i<ARRAYSIZE; i++){
			int element = r.nextInt();
			while(elements.contains(element)){
				element = r.nextInt();
			}
			testArray[i] = element;
			elements.add(testArray[i]);
		}

		Sorter<Integer> sorter = new Sorter<Integer>(testArray);
		sorter.sort();
		
		assertTrue("New element appeared at " +  0 + ": " + testArray[0], elements.remove(testArray[0]));
		for(int i = 1; i<ARRAYSIZE; i++){
			assertTrue("Not sorted at: " + (i-1) + ": " + testArray[i-1] + " not <= " + testArray[i]
					, (testArray[i-1] <= testArray[i]));
			assertTrue("New element appeared at " +  i + ": " + testArray[i], elements.remove(testArray[i]));
		}
		assertTrue("Elements vanished",  elements.isEmpty());
		System.out.println("Test Successful");

	}
}
