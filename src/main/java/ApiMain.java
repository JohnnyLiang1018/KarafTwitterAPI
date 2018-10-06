import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Base64;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServlet;
import org.json.*;


public class ApiMain extends HttpServlet{
	private static URL url;
	private static HttpsURLConnection connection;
	private String consumer_key = "NMqaca1bzXsOcZhP2XlwA";
	private String consumer_secret = "VxNQiRLwwKVD0K9mmfxlTTbVdgRpriORypnUbHhxeQw";
	private static String bearer_key;
	
	public ApiMain() {
		
	}
	// Main method
	public static void main(String []args) {
		ApiMain api = new ApiMain();
		api.application_only_auth();
		apiSearch("league%20of%20legends","popular");
	}
	
	public static void makeConnection(String urlString) {
		try {
			url = new URL(urlString);
			connection = (HttpsURLConnection) url.openConnection();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// 
	public void application_only_auth() {
		try {
			makeConnection("https://api.twitter.com/oauth2/token");
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
			if(connection.getResponseMessage().equals("OK")) {
				JSONObject json = inputStreamToJSON();
				bearer_key = json.getString("access_token");
				System.out.println(json.getString("token_type"));
				System.out.println(json.getString("access_token"));
				
				
			}
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
	public static JSONObject apiSearch(String keywords, String result_type) { // there are more parameters available, for now just two
		try {
			makeConnection("https://api.twitter.com/1.1/search/tweets.json?q=" + keywords + "&" + "result_type=" + result_type); 
			System.out.println(connection.getURL().toString());
			connection.setRequestMethod("GET");
			String bearer = "Bearer" + " " + bearer_key;
			System.out.println(bearer);
			connection.setRequestProperty("Authorization",bearer);
			connection.connect();
			if(connection.getResponseMessage().equals("OK")) {
				
				JSONObject json = inputStreamToJSON();
				
				// a demonstration of processing json data, should be inside a feature function
				JSONArray array = json.getJSONArray("statuses"); // there are multiple tweets under statuses tab
				for(int i=0;i<array.length();i++) { // loop through all the tweets, print the text string if available
					System.out.println(array.getJSONObject(i).optString("text")+"\n");
					
				}
				return json;
			}
			
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 

	}
	
	public static String apiGetFollower() {
		
		
		return null;
	}
	
	public static String apiGetTrends() {
		
		
		return null;
	}
	
	public static String apiGetGeo() {
		
		return null;
	}
	
	public static String apiGetUsers() {
		
		return null;
	}
	
	public static String apiGetUserTimeline() {
		
		return null;
	}
	
	public static String apiStatusesRetweets() {
		
		return null;
	}
	
	
	
	
	public static JSONObject inputStreamToJSON() throws IOException {
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer jsonLines = new StringBuffer();
		while ((line = br.readLine()) != null) {
			jsonLines.append(line);
		}
		br.close();
		JSONObject output = new JSONObject(jsonLines.toString());
		
		return output;
	}
	
	
	
}
