package space.anwenchu.utils.http.client;


import space.anwenchu.utils.http.Header.VHeader;
import space.anwenchu.utils.http.client.entity.VHttpEntity;

import java.io.InputStream;
import java.net.HttpCookie;
import java.util.List;

/**
 * 
 * @author 默非默
 *
 */
public interface VHttpResponse {
	
	public abstract List<HttpCookie> getCookies();
	
	public abstract String getHeader(String key);
	
	public abstract VHeader getAllHeader();
	
	public abstract InputStream getBody();
	
	public abstract String getLocation();
	
	public abstract VHttpEntity getEntity();
	
	public abstract void setEntity(VHttpEntity entity);
}
