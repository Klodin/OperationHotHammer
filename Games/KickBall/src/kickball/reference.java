/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kickball;

/**
 *
 * @author Klodin
 */
public class reference {
    public static void main(String[] args){
        int intergerNumber = 1;
        double decimalNumber = 1.5;
        boolean truthValue = true;
        
        int size = 5;
        int[] oneDimensionalListofInts = new int[size];
        
        int i = 0;
        while (i < 5){
            i++;
            System.out.println("Heya");
        }
        
        for (int j = 0; j < 5; j++){
            System.out.println("counting Down!: " + j);
        }
        
        System.out.println(5);
        System.out.println(addFive(5));
        
    }
    
    public static int addFive(int inputValue){
        return inputValue + 5;
    }
}
