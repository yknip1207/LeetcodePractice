import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		Graph.main([]);
//		ArrayList<Boolean> input = new ArrayList<Boolean>(16);
//		
//
//		try {
//			BufferedReader br = new BufferedReader(new FileReader("testcase" ));
//			String doc = "", line = "";
//			while((line = br .readLine()) != null)
//				doc += line + "\n";
//			
//			
//		}
//		
//		
//		
//		
//		
//		
//		
//
//		catch (IOException e ) {
//			e. printStackTrace();
//		}
		Boolean[][] truthTable = truthTable(2);
		for(Boolean[] x: truthTable){
			for(Boolean y: x){
				System.out.print((y==false?"0":"1") + " ");
			}
			Graph.main(x);
			System.out.println();
		}
	}
	
	private static Boolean[][] truthTable(int n) {
		int rows = (int) Math.pow(2,n);
		Boolean[][] result = new Boolean[rows][n];
        

        for (int i=0; i<rows; i++) {
        	result[i] = new Boolean[n];
            for (int j=n-1; j>=0; j--) {
//                System.out.print((i/(int) Math.pow(2, j))%2 + " ");
                result[i][(n-1)-j] = (i/(int) Math.pow(2, j))%2==1 ? true : false ;
            }
//            System.out.println();
        }
        
        return result;
    }

	

	
}
