import java.util.Scanner;

public class Palindrome{
	public static void main(String[] args){
		Palindrome a = new Palindrome();
		Scanner in = new Scanner(System.in);
		System.out.println("Введите строку: ");
		String s = in.nextLine();
		String w[] = s.split(" ");
		for (String r:w){
			a.isPalindrome(r);
			System.out.println(s.equals(reversingString(s)));
		}
	}
	public static String reversingString(String s){
		String w = "";
		for (int i = 0; i < s.length(); i++){
			w += s.charAt(s.length()-1-i);
		}
		return w;
	}
	public static boolean isPalindrome(String s){
		if(s.equals(reversingString(s))){
			System.out.println("Полиндром");
		}else{
			System.out.println("Не полиндром");
		}
		return s.equals(reversingString(s));
	}
}