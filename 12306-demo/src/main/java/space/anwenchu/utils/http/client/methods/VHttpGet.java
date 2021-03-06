package space.anwenchu.utils.http.client.methods;


import space.anwenchu.utils.http.Header.VHeader;
import space.anwenchu.utils.http.client.parames.VParames;
import space.anwenchu.utils.http.utils.VConstant;

/**
 * 
 * @author 默非默
 *
 */
public class VHttpGet implements VHttpMethods {
	
	private final String type = VConstant.HTTPGET;

	private String url;
	
	private VHeader header;
	
	private VParames parames;

	/**
	 * 
	 * @param url
	 */
	public VHttpGet(String url){
		this.url = url;
	}
	
	/**
	 * 
	 */
	public VHttpGet(){}
	
	public VHeader getHeader() {
		return header;
	}

	public void setHeader(VHeader header) {
		this.header = header;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public VParames getParames() {
		return parames;
	}

	public void setParames(VParames parames) {
		this.parames = parames;
	}
	
	public String getType() {
		return type;
	}

}
