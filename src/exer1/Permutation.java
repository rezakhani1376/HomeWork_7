package exer1;

import java.util.ArrayList;
import java.util.Scanner;

public class Permutation {
    private static final ArrayList<String> ArrayList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("WRITE ONE STRING : ");
        inputOne(scanner.next());
        System.out.println(ArrayList);
        System.out.print("WRITE TWO STRING SPLIT : ");
        inputTwo(scanner.next(),scanner.next());

    }

    public static void inputOne(String string) {
        int n=string.length();
        permute(string,0,n-1);
    }
    public static void inputTwo(String one,String two){
        permute(one,0,one.length()-1);
        for (String string : ArrayList) {
            if(string.equals(two)){
                System.out.println("PASS");
                return;
            }
        }
        System.out.println("Fail");
    }

    public static void permute(String str, int l, int r) {
        if (l == r) {
            ArrayList.add(str);
        } else {
            for (int i = l; i <= r; i++) {
                str = swap(str, l, i);
                permute(str, l + 1, r);
                str = swap(str, l, i);
            }
        }


    }
    public static String swap(String a,int i,int j){
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }


}
