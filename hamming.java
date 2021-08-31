import java.util.*;

class hamming {

  public static int XOR(StringBuilder arr) {
    int count = 0;
    char one = '1';
    for(int i = 0; i < arr.length(); i++){
        if(arr.charAt(i) == one){
            count++;
        }
    }
    if(count % 2 == 0){
        return 0;
    }
    return 1;
  }

  public static int XOR(int arr[]) {
    int count = 0;
    for(int i = 0; i < arr.length; i++) {
        if(arr[i] == 1) {
            count++;
        }
    }
    if(count % 2 == 0){
        return 0;
    }
    return 1;
  }
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
        
    StringBuilder sb = new StringBuilder("");
    System.out.print("Enter the data bits : ");
    
    sb.append(sc.nextLine());        

    int noOfRedundantBits = 0;
    int len = sb.length();
    while(Math.pow(2, noOfRedundantBits) <= len + noOfRedundantBits + 1){
        noOfRedundantBits++;
    }
    
    System.out.println(sb);
    System.out.println("Number of Redundant Bits required will be : " + noOfRedundantBits);
    
    int[] parityBits = new int[noOfRedundantBits];
    int[][] checkBitsSender = { {6,5,3,2,0} , {6,4,3,1,0} , {5,4,3}, {2,1,0} };
    
    for(int i = 0; i < parityBits.length; i++){
        StringBuilder sb1 = new StringBuilder("");
        for(int j = 0; j < checkBitsSender[i].length; j++){
            int num = checkBitsSender[i][j];
            char ch = sb.charAt(num);
            sb1.append(ch);
        }
        System.out.println("P[" + (int)(Math.pow(2,i)) +"] = XOR(" + sb1 + ")");
        parityBits[i] = XOR(sb1);
    }
    
    for(int i = 0; i < parityBits.length; i++){
        System.out.println("P[" + (int)(Math.pow(2,i)) +"] = " + parityBits[i]);
    }

    sb.insert(3, parityBits[3]);
    sb.insert(7, parityBits[2]);
    sb.insert(9, parityBits[1]);
    sb.insert(10, parityBits[0]);

    System.out.print("How many error bits you want to insert (1/2) : ");
    int choice = sc.nextInt();

    if(choice == 1){
        System.out.print("Data bits that will be sent is : ");        

        System.out.println(sb + "\n");

        System.out.print("At what position you want to add error bit : ");
        int orgPos = sc.nextInt();

        int pos = Math.abs(sb.length() - orgPos);

        if(sb.charAt(pos) == '1') {
            sb.setCharAt(pos, '0');
        } else {
            sb.setCharAt(pos, '1');
        }

        System.out.println("Data bits now have become : " + sb);

        int receiver[] = new int[parityBits.length];
        int[][] checkBitsReceiver = { {10,8,6,4,2,0} ,{9,8,5,4,1,0} , {7,6,5,4}, {3,2,1,0} };

        for(int i = 0; i < receiver.length; i++) {
            StringBuilder sb2 = new StringBuilder("");
            for(int j = 0; j < checkBitsReceiver[i].length; j++){
                int num = checkBitsReceiver[i][j];
                char a = sb.charAt(num);
                sb2.append(a);
            }
            System.out.println("C[" + (int)(Math.pow(2,i)) +"] = XOR(" + sb2 + ")");
            receiver[i] = XOR(sb2);
        }

        for(int i = 0; i < receiver.length; i++){
            System.out.println("C[" + (int)(Math.pow(2,i)) +"] = " + receiver[i]);
        } 

        System.out.print("Error has occured at Bit : ");
        for(int i = receiver.length - 1;i >= 0; i--) {
            System.out.print(receiver[i]);
        }
        System.out.println();
        System.out.println("Error can be resolved by changing the bit : " + orgPos);

        System.out.print("After making changes we have data bits as : ");

        if(sb.charAt(pos) == '1') {
            sb.setCharAt(pos, '0');
        } else {
            sb.setCharAt(pos, '1');
        }
        System.out.print(sb);

    } else {

        int addParity = XOR(parityBits);
        char c = Character.forDigit(addParity, 10);
        sb.insert(0, c);

        System.out.println("Data bits that will be sent is : " + sb + "\n");

        System.out.print("At what position you want to add error bits : ");
        int[] errorLoc = new int[2];
        errorLoc[0] = Integer.parseInt(sc.next());
        errorLoc[1] = Integer.parseInt(sc.next());

        int pos = Math.abs(sb.length() - errorLoc[0]);
        if(sb.charAt(pos) == '1') {
            sb.setCharAt(pos, '0');
        } else {
            sb.setCharAt(pos, '1');
        }

        int pos2 = Math.abs(sb.length() - errorLoc[1]);
        if(sb.charAt(pos2) == '1') {
            sb.setCharAt(pos2, '0');
        } else {
            sb.setCharAt(pos2, '1');
        }

        System.out.println("After adding error bits : " + sb + "\n");

        int finalSender = XOR(sb);

        int receiver2[] = new int[parityBits.length];
        int[][] checkBitsReceiver2 = { {11,9,7,5,3,1} ,{10,9,6,7,2,1} , {8,7,6,5,0}, {4,3,2,1,0} };

        for(int i = 0; i < receiver2.length; i++) {
            StringBuilder sb2 = new StringBuilder("");
            for(int j = 0; j < checkBitsReceiver2[i].length; j++){
                int num = checkBitsReceiver2[i][j];
                char a = sb.charAt(num);
                sb2.append(a);
            }
            System.out.println("C[" + (int)(Math.pow(2,i)) +"] = XOR(" + sb2 + ")");
            receiver2[i] = XOR(sb2);
        }

        for(int i = 0; i < receiver2.length; i++){
            System.out.println("C[" + (int)(Math.pow(2,i)) +"] = " + receiver2[i]);
        } 

        int finalReceiver = XOR(receiver2);

        System.out.println("P = " + finalSender);
        System.out.println("C = " + finalReceiver);

        if(finalReceiver == 0 && finalSender == 0) {
            System.out.println("No Error");
        } else if(finalReceiver != 0 && finalSender == 1) {
            System.out.println("Single Error can be corrected!!!");
        } else if(finalReceiver != 0 && finalSender == 0) {
            System.out.println("Double Error cannot be corrected");
        } else if(finalReceiver == 0 && finalSender == 1) {
            System.out.println("Error in P[12] bit");
        }
    }    
    sc.close();
  }
}