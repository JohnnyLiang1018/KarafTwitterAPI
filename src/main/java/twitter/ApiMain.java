package twitter;
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
/**
 * 
 * @author Zhonglin Liang
 *
 */

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
		apiTrendClosest("37.781157","-122.400612831116");
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
	public static String apiSearch(String keywords, String result_type) { // there are more parameters available, for now just two
		try {
			makeConnection("https://api.twitter.com/1.1/search/tweets.json?q=" + keywords + "&" + "result_type=" + result_type); 
			System.out.println(connection.getURL().toString());
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization","Bearer" + " " + bearer_key);
			connection.connect();
			if(connection.getResponseMessage().equals("OK")) {
				
				JSONObject json = inputStreamToJSON();
				
				// a demonstration of processing json data, should be inside a feature function
				JSONArray array = json.getJSONArray("statuses"); // there are multiple tweets under statuses tab
				String output = "";
				for(int i=0;i<array.length();i++) { // loop through all the tweets, print the text string if available
					output = output + "Tweet_id:" + array.getJSONObject(i).optString("id_str") + "<br>";
					output = output + "User:" + array.getJSONObject(i).getJSONObject("user").optString("name") + "<br>";
					output = output + "Text:" + array.getJSONObject(i).optString("text")+"<br>";
					
					System.out.println(array.getJSONObject(i).optString("id_str")+"\n");
				}
				return output;
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

	public static String apiGetFollower(String name) {
		try {
			makeConnection("https://api.twitter.com/1.1/followers/ids.json?screen_name=" + name);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Bearer" + " " + bearer_key);
			connection.connect();
			if(connection.getResponseMessage().equals("OK")) {
				JSONObject json = inputStreamToJSON();
				JSONArray array = json.getJSONArray("ids");
				String output = "";
				for(int i = 0; i<50; i++) {
					output = output + array.optString(i) + ",";
					System.out.println(array.optString(i) + "\n");
				}
				return output;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return null;
	}
	
	public static String apiGetTrends(String id) {
		try {
			makeConnection("https://api.twitter.com/1.1/trends/place.json?id=" + id);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Bearer" + " " + bearer_key);
			connection.connect();
			if(connection.getResponseMessage().equals("OK")) {
				JSONObject json = inputStreamToJSONV2();
				JSONArray array = json.getJSONArray("trends");
				String output = "";
				for(int i = 0; i<array.length(); i++) {
					output = output + array.getJSONObject(i).optString("name") + "<br>";
					System.out.println(array.getJSONObject(i).optString("name"));
				}
				return output;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String apiTrendClosest(String lat, String lon) {
		try {
			makeConnection("https://api.twitter.com/1.1/trends/closest.json?lat=" + lat + "&" + "long=" + lon);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Bearer" + " " + bearer_key);
			connection.connect();
			if(connection.getResponseMessage().equals("OK")) {
				JSONArray json = inputStreamToJSONArray();
				String output = "";
				for(int i = 0; i<json.length(); i++) {
					output = output + "Country:" + json.getJSONObject(i).optString("country") + "<br>";
					output = output + "Name:" + json.getJSONObject(i).optString("name") + "<br>"; 
					output = output + "WOEID:" + json.getJSONObject(i).optString("woeid") + "<br>";
					
				}
				return output;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String apiGetUsers(String idList) {
		try {
			makeConnection("https://api.twitter.com/1.1/users/lookup.json?user_id=" + idList);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer" + " " + bearer_key);
			connection.connect();
			if(connection.getResponseMessage().equals("OK")) {
				JSONArray json = inputStreamToJSONArray();
				String output = "";
				for(int i = 0; i<json.length(); i++) {
					output = output + json.getJSONObject(i).optString("name") + "<br>";
				}
				return output;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String apiGetFollowerName(String name) {
		String input = apiGetFollower(name);
		return apiGetUsers(input);
	
	}


	/**
	 * author Aye 
	 * @param name
	 * @return
	 */
	public static String apiGetUserTimeline(String name, String count) {
		String output = "";
		try {
			makeConnection("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=" + name+ "&count=" + count);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Bearer" + " " + bearer_key);
			connection.connect();
			if(connection.getResponseMessage().equals("OK")) {
				JSONArray json = inputStreamToJSONArray();
				for(int i = 0; i<json.length(); i++) {
					output = output + "Text:" + json.getJSONObject(i).optString("text") + "<br>";
				}
			}
			else {
				output = output + "Cannot connect to api";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return output;
	}
	
	public static String apiStatusesRetweets(String tweetId) {
		try {
			makeConnection("https://api.twitter.com/1.1/statuses/retweets/" + tweetId + ".json");
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Bearer" + " " + bearer_key);
			connection.connect();
			if(connection.getResponseMessage().equals("OK")) {
				JSONArray json = inputStreamToJSONArray();
				String output = "";
				for(int i = 0; i<json.length(); i++) {
					output = output + "created_at:" + json.getJSONObject(i).optString("created_at") + "<br>";
				}
				return output;
			}
			else {
				System.out.println(connection.getResponseMessage());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	public static JSONObject inputStreamToJSON() throws IOException {
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer jsonLines = new StringBuffer();
		while ((line = br.readLine()) != null) {
			jsonLines.append(line);
			System.out.println(line);
		}
		br.close();
		JSONObject output = new JSONObject(jsonLines.toString());
		
		return output;
	}
	
	public static JSONObject inputStreamToJSONV2() throws IOException{
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer jsonLines = new StringBuffer();
		br.skip(1);
		while ((line = br.readLine()) != null) {
			jsonLines.append(line);
			System.out.println(line);
		}
		br.close();
		JSONObject output = new JSONObject(jsonLines.toString());
		
		return output;
	}
	
	public static JSONArray inputStreamToJSONArray() throws IOException{
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer jsonLines = new StringBuffer();
		while ((line = br.readLine()) != null) {
			jsonLines.append(line);
			System.out.println(line);
		}
		br.close();
		JSONArray output = new JSONArray(jsonLines.toString());
		
		return output;
	}
	
}
