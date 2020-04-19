public class Palindrome{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);    
		System.out.println("Введите строку: ");
		String s = in.nextLine();
		in.close();
		isPalindrome(s);
	}
	public static String reversingString(String s){
		String w = "";
		for (int i = s.lenght()-1;i>=0;--i)
			w +=s.charAt(i);
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
