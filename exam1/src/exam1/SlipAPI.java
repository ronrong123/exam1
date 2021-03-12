package exam1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class SlipAPI {

	public Map<String, Object> insertSlip(String param) {
        String reqURL = "http://192.168.0.83/exam/insertSlip";   
        Map<String, Object> map = new HashMap<String,Object>();
        try {
           
            URL url = new URL(reqURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST"); // HEADER + BODY(파라미터값)
			con.setRequestProperty("content-Type", "application/json; charset=utf8");
			con.setDoOutput(true);

			OutputStream os = con.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(param); // 파라미터와 &= 합체.
			writer.flush();
			writer.close();
			os.close();

			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				System.out.println("" + sb.toString());
			} else {
				System.out.println("token error" + con.getResponseMessage());
			}
            
            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성  "{}"
            Gson gson = new Gson();
            map = gson.fromJson(sb.toString(), Map.class);
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return map;
    }
}
