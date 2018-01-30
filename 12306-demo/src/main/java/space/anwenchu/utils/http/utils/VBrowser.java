package space.anwenchu.utils.http.utils;


import space.anwenchu.utils.http.client.VHttpClient;
import space.anwenchu.utils.http.client.VHttpResponse;
import space.anwenchu.utils.http.client.methods.VHttpMethods;
import space.anwenchu.utils.http.impl.VHttpClientImpl;

/**
 * 浏览器类
 * @author Administrator
 *
 */
public class VBrowser {
	
	private static VHttpClient client = new VHttpClientImpl();
	
	private static VBrowser browser = null;
	
	private VBrowser(){}
	
	public static VBrowser getInstance(){
		if (browser==null) {
			return new VBrowser();
		}
		return browser;
	}

	public static VHttpResponse execute(VHttpMethods methods){
		return client.execute(methods);
	}
}
