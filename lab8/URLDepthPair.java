import java.util.LinkedList;
import java.net.MalformedURLException;
import java.net.URL;

//Хранит URL-адреса и глубину, на которой они были исследованы
public class URLDepthPair {
	// Регулярное выражение для URL-адреса
	public static final String URL_PREFIX = "http:";

	public String URL;

	private int depth;

	URL host_path;

	// Предполагает ввод URL-адрес абсолютный URL-адрес
	public URLDepthPair (String URL, int depth){
		this.URL=URL;
		this.depth=depth;
		try {
			this.host_path= new URL(URL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	//Возвращает имя хоста сервера, указанное в URL-адресе
	public String getHost(){
		return host_path.getHost();
	}

	//Возвращает ресурс на сервере
	public String getPath(){
		return host_path.getPath();
	}

	// Возвращает глубину поиска URL-адреса 
	public int getDepth() {
		return depth;
	}

	//Возвращает URL-адрес пары 
	public String getURL() {
		return URL;
	}

	//Проверяет, является ли URL-адрес абсолютным URL-адресом, а не относительным
	public static boolean check(LinkedList<URLDepthPair> resultLink, URLDepthPair pair) {
		boolean isAlready = true;
		for (URLDepthPair c : resultLink)
			if (c.getURL().equals(pair.getURL()))
				isAlready=false;
		return isAlready;
	}
}