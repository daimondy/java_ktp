import java.util.Scanner;

public class Palindrome{
	public static void main(String[] args){
		Palindrome a = new Palindrome(); //ссылка на обьект класса
		Scanner in = new Scanner(System.in); //ввод
		System.out.println("Введите строку: ");
		String s = in.nextLine(); //ввод строки с консоли
		String w[] = s.split(" "); //исключение пробелов
		for (String r:w){ 
			a.isPalindrome(r);
			System.out.println(s.equals(reversingString(s))); 
		} //вывод ответа
	}
	public static String reversingString(String s){
		String w = "";
		for (int i = 0; i < s.length(); i++){
			w += s.charAt(s.length()-1-i);
		} // берем символы исходной строки с начала строки до конца
    	// и добавляем ее в конец строки-результата (переменная w)
		return w;
	}
	public static boolean isPalindrome(String s){
		if(s.equals(reversingString(s))){ //если перевернутая строка равна исходной
			System.out.println("Полиндром"); 
		}else{
			System.out.println("Не полиндром");
		}
		return s.equals(reversingString(s)); //возвращение результата
	}
}