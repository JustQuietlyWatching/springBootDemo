package channel.anwenchu.utils.http.client;


import channel.anwenchu.utils.http.client.methods.VHttpMethods;

/**
 * 
 * @author 默非默
 *
 */
public interface VHttpClient {

	public abstract VHttpResponse execute(VHttpMethods methods);
}
