import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Base64;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.*;


public class ApiMain {
	private URL url;
	HttpURLConnection connection;
	private String consumer_key = "NMqaca1bzXsOcZhP2XlwA";
	private String consumer_secret = "VxNQiRLwwKVD0K9mmfxlTTbVdgRpriORypnUbHhxeQw";
	
	public ApiMain() {
		try {
			// initialize HttpURLConnection
			url = new URL("https://api.twitter.com/oauth2/token");
			connection = (HttpURLConnection) url.openConnection();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	// Main method
	public static void main(String []args) {
		ApiMain am = new ApiMain();
		am.application_only_auth();
	}
	
	// 
	public void application_only_auth() {
		try {
			String consumer_key_encode = URLEncoder.encode(consumer_key, "UTF-8");
			String consumer_secret_encode = URLEncoder.encode(consumer_secret, "UTF-8");
			String encode_key = consumer_key_encode + ":" + consumer_secret_encode;
			String Base64_encode = "Basic" +" "+ Base64.getEncoder().encodeToString(encode_key.getBytes());
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", Base64_encode);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write("grant_type=client_credentials");
			osw.flush();
			osw.close();
			connection.connect();
			System.out.println(connection.getResponseMessage());
			System.out.println(connection.getResponseCode());
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void HttpGET() {
		try {
			connection.setRequestMethod("GET");
			//Add some properties//
			connection.connect();
			if(connection.getResponseMessage().equals("OK")) {
				InputStream is = connection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line;
				StringBuffer jsonLines = new StringBuffer();
				while ((line = br.readLine()) != null) {
					jsonLines.append(line);
				}
				br.close();
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(jsonLines.toString());
				JSONObject json = new JSONObject(jsonLines.toString());
				
			}
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
