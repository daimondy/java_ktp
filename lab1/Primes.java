public class Primes{
	public static void main(String[] args){
		for (int x = 2;x<100;x++){
			if (isPrime(x) == true){ //если число простое, то оно выводится
				System.out.println(x);
			}
		}
	}
	public static boolean isPrime(int n){
			for (int i = 2; i<n;i++){
				if (n%i==0) //проверка числа на делимость без остатка
					return false; //если число делится без остатка, то оно не попадает в список
			}
			return true; //возвращает простые числа
	}
}


