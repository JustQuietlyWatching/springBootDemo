package channel.anwenchu.utils.http.client.methods;


import channel.anwenchu.utils.http.Header.VHeader;
import channel.anwenchu.utils.http.client.parames.VParames;
import channel.anwenchu.utils.http.utils.VConstant;

/**
 * 
 * @author 默非默
 *
 */
public class VHttpPost implements VHttpMethods{

	private final String type = VConstant.HTTPPOST;

	private String url;
	
	private VHeader header;
	
	private VParames parames;
	
	/**
	 * 
	 */
	public VHttpPost(){};
	
	/**
	 * 
	 * @param url
	 */
	public VHttpPost(String url){
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public VHeader getHeader() {
		return header;
	}

	public void setHeader(VHeader header) {
		this.header = header;
	}

	public VParames getParames() {
		return parames;
	}

	public void setParames(VParames parames) {
		this.parames = parames;
	}
	
}
