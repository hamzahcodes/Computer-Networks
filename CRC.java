import java.util.Scanner;

public class CRC {
    
    public static int XOR(int x, int y) {
        if(x == y) {
            return 0;
        }
        return 1;
    }
    
    public static int flip(int x) {
        if(x == 0) {
            return 1;
        }
        return 0;
    }
    
    public static int[] moduloDivision(String data, int[] dividend, int[] divisor) {
        for(int i = 0; i < data.length() ; i++) {
            
            if(dividend[i] == 1){
                for(int j = 0; j < divisor.length; j++) {
                    dividend[i + j] = XOR(dividend[i + j], divisor[j]);
                }
            }
        }
        return dividend;
    }
    
    public static void displayCRC(String data, int[] dividend) {
        System.out.print("CRC is : ");
        for(int i = data.length(); i < dividend.length; i++) {
            System.out.print(dividend[i]);
        }
        System.out.println();
    }
    
    public static void displayCheckSum(String data, int[] dividend) {
        System.out.print("Checksum code is : ");
        for(int i = 0; i < data.length(); i++) {
            dividend[i] = data.charAt(i) - '0';
        }
        for(int i = 0; i < dividend.length; i++) {
            System.out.print(dividend[i]);
        }
        System.out.println();
        
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter data bits : ");
        String data = sc.next();
        
        System.out.print("Enter check bits : ");
        String check = sc.next();
        
        int dividend[] = new int[data.length() + (check.length() - 1)];
        int divisor[] = new int[check.length()];
        
        for(int i = 0; i < data.length(); i++) {
            dividend[i] = data.charAt(i) - '0';
        }
        
        for(int i = 0; i < check.length(); i++) {
            divisor[i] = check.charAt(i) - '0';
        }
        
        // calculating remainder(CRC)
        dividend = moduloDivision(data, dividend, divisor);
        
        //display remainder
        displayCRC(data, dividend); 
        
        // display checksum
        displayCheckSum(data, dividend);
        
        //asking for change in checksum
        System.out.print("Do you want to put error bit in checksum (0/1) : ");
        int choice = sc.nextInt();
        
        if(choice == 1) {
            System.out.print("How many error bits you want to change : ");
            int select = sc.nextInt();
            System.out.print("Enter the bit number you want to change : ");
            String change = sc.next();
            
            for(int i = 0; i < select; i++) {
                dividend[change.charAt(i) - '0'] = flip(dividend[change.charAt(i) - '0']);
            }
            
            dividend = moduloDivision(data, dividend, divisor);
            
            displayCRC(data, dividend);
            System.out.println("We see that remainder is not 0. Hence data is corrupted!!");
        } else {
            System.out.println("CRC obtained at receiver side is zero");
            System.out.println("Data sent without corruption");
        }
    }
}