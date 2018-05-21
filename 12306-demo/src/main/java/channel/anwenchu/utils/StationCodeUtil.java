package channel.anwenchu.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationCodeUtil {
	
	public static class StationCode{
		private String name;
		private String namePre;
		private String code;
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		public String getNamePre() {
			return namePre;
		}
		public void setNamePre(String namePre) {
			this.namePre = namePre;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
	}
	
	private static Map<String,StationCode> data = new HashMap<>();
	private static List<StationCode> dataList = new ArrayList<>();

	static {
		try {
			FileInputStream fis=new FileInputStream(new File(StationCodeUtil.class.getResource("/StationCode.txt").toURI()));
	        InputStreamReader isr=new InputStreamReader(fis, "UTF-8");
	        BufferedReader br = new BufferedReader(isr);
	        String line="";
	        String[] arrs=null;
	        while ((line=br.readLine())!=null) {
	            arrs=line.split("\\|"); 
	            StationCode s = new StationCode();
	            s.setCode(arrs[2]);
	            s.setName(arrs[1]);
	            s.setNamePre(arrs[0]);
				dataList.add(s);
	            data.put(arrs[1], s);
	        }
	        br.close();
	        isr.close();
	        fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String,StationCode> getData(){
		return data;
	}

	public static List<StationCode> getDataList(){
		return dataList;
	}

	public static List<StationCode> getDataListByLikeName(String name){
		List<StationCode> list = new ArrayList<>();
		for (StationCode stationCode : dataList) {
			if (stationCode.getName().contains(name)) {
				list.add(stationCode);
			}
		}
		return list;
	}
}
