package channel.anwenchu.utils.http.client.entity;


import channel.anwenchu.utils.http.Header.VHeader;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;

/**
 * http协议实体
 * @author 默非默
 *
 */
public class VHttpEntity {
	
	private VHeader headers;			//http头信息
	
	private InputStream input;			//http体
	
	private int staus;					//返回状态码
	
	private HttpsURLConnection conn;	//连接

	public void setConn(HttpsURLConnection conn) {
		this.conn = conn;
	}
	
	/**
	 * 关闭连接
	 */
	public void disconnect(){
		conn.disconnect();
	}

	public int getStaus() {
		return staus;
	}

	public void setStaus(int staus) {
		this.staus = staus;
	}

	public VHeader getHeaders() {
		return headers;
	}

	public void setHeaders(VHeader header) {
		this.headers = header;
	}

	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}

}
