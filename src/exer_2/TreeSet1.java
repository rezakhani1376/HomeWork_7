package exer_2;

import java.util.Random;
import java.util.TreeSet;

public class TreeSet1 {
    public static void main(String[] args) {
        TreeSet<Character> input1=new TreeSet<>();
        TreeSet<String> input2=new TreeSet<>();
        fullTreeSet(input1);
        fullTreeSet(input2);
        System.out.println(input1);
        System.out.println(input2);
        total(input1,input2);


    }
    static void fullTreeSet(TreeSet input){
        Random rnd = new Random();
        while (input.size()!=10){
            char c = (char) (rnd.nextInt(26) + 'a');
            //System.out.println(c);
            input.add(c);
        }
    }
    static void total(TreeSet input1, TreeSet input2){
        TreeSet <Character> newTreeSet=new TreeSet<>();
        newTreeSet.addAll(input1);
        newTreeSet.addAll(input2);
        System.out.println("subscribe :"+newTreeSet);
    }

}
