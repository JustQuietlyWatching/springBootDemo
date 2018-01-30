package space.anwenchu.utils.http.client;


import space.anwenchu.utils.http.client.methods.VHttpMethods;

/**
 * 
 * @author 默非默
 *
 */
public interface VHttpClient {

	public abstract VHttpResponse execute(VHttpMethods methods);
}
