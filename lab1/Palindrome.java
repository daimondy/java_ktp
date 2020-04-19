import java.util.Scanner;

public class Palindrome{
	public static void main(String[] args){
		Palindrome a = new Palindrome(); //ссылка на обьект класса
		Scanner in = new Scanner(System.in);
		System.out.println("Slova ili slovosochetaniya?(1/2): "); //выбор палиндрома - отдельно слова или словосочетание
		int num = in.nextInt();
		in.nextLine();
		if (num == 1){ //выбор слов по отдельности
			System.out.println("Vvedite stroku: "); //ввод строки с консоли
			String s = in.nextLine();
        	String w[] = s.split(" "); //разделение слов
        	for (String r:w){ //пробег по каждому слову
            	a.isPalindrome(r);
            	System.out.println(s.equals(reversingString(s)));
        	}
		}else if (num ==2){ //палиндром как целое словосочетание
			System.out.println("Vvedite stroku: "); //ввод строки с консоли
			String s = in.nextLine().replaceAll(" ",""); //исключение пробелов
			a.isPalindrome(s);
			System.out.println(s.equals(reversingString(s))); 
		}else{
			System.out.println("Vvedit vernoe zhachenie");
		}
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
		if(s.equals(reversingString(s))){ 
			System.out.println("Palindrom"); //если перевернутая строка равна исходной
		}else{
			System.out.println("Ne palindrom");
		}
		return s.equals(reversingString(s)); //возвращение результата
	}
}