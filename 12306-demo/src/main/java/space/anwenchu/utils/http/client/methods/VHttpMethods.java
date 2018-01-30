package space.anwenchu.utils.http.client.methods;


import space.anwenchu.utils.http.Header.VHeader;
import space.anwenchu.utils.http.client.parames.VParames;

/**
 * 
 * @author 默非默
 *
 */
public interface VHttpMethods {
	
	public abstract String getUrl();
	
	public abstract VHeader getHeader();
	
	public abstract VParames getParames();
	
	public abstract String getType();

}
