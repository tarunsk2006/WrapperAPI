package wrapper.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

@Path("/alchemy")
public class AlchemyService {
	
	  @GET
	  @Produces("application/xml")
	  public String convertToXmlDefault(@Context UriInfo uriInfo) throws JSONException {
		  System.out.println("this is application/xml");
		  String finalUrl = getTargetURL(uriInfo.getQueryParameters(true));
		  HttpURLConnection con = getConnection(finalUrl);
		  BufferedReader in;
		  StringBuffer response = new StringBuffer();
		  try {
		   in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		  String inputLine;
		  while ((inputLine = in.readLine()) != null) {
			  response.append(inputLine);
		  }
		  in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return response.toString();
	  }
	  
	  @Path("/json")
	  @GET
	  @Produces("application/json")
	  public Response convertToJson(@Context UriInfo uriInfo) throws JSONException {
		  System.out.println("this is application/json");
		  String finalUrl = getTargetURL(uriInfo.getQueryParameters(true));
		  HttpURLConnection con = getConnection(finalUrl);
		  JSONObject xmlJSONObj = null;
		  BufferedReader in;
		  String jsonPrettyPrintString ="";
		try {
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		  String inputLine;
		  StringBuffer response = new StringBuffer();

		  while ((inputLine = in.readLine()) != null) {
			  response.append(inputLine);
		  }
		  	  xmlJSONObj = XML.toJSONObject(response.toString());
			  jsonPrettyPrintString = xmlJSONObj.toString(4);
			  System.out.println("json " + jsonPrettyPrintString);

		  in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		 // String result = xmlJSONObj.toString();
		 return Response.status(200).entity(jsonPrettyPrintString).build();
	  }
	  
	  @Path("/xml")
	  @GET
	  @Produces("application/xml")
	  public String convertToXml(@Context UriInfo uriInfo) throws JSONException {
		  System.out.println("this is application/xml");
		  String finalUrl = getTargetURL(uriInfo.getQueryParameters(true));
		  HttpURLConnection con = getConnection(finalUrl);
		  BufferedReader in;
		  StringBuffer response = new StringBuffer();
		  try {
		   in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		  String inputLine;
		  while ((inputLine = in.readLine()) != null) {
			  response.append(inputLine);
		  }
		  in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return response.toString();
	  }
	 
	 public String getTargetURL(
				MultivaluedMap<String, String> queryParameters){
		 
		 StringBuffer sb = new StringBuffer();
			sb.append("http" + "://");
			sb.append("184.170.233.3:9080");
			sb.append("/vivisimo/cgi-bin/velocity.exe");
			sb.append( "?");
			
			Map<String, String> qparameters = new LinkedHashMap<String, String>();
			Iterator<String> it = queryParameters.keySet().iterator();

			while (it.hasNext()) {
				String theKey = (String) it.next();
				qparameters.put(theKey, queryParameters.getFirst(theKey));
				sb.append(theKey + "=");
				sb.append(queryParameters.getFirst(theKey) +"&");
			}

//			for (Map.Entry<String, String> entry : qparameters.entrySet()) {
//			    String key = entry.getKey();
//			    sb.append(key + "=");
//			    Object value = entry.getValue();
//			    sb.append(value +"&");
//			}
			String targetURL = sb.toString();
			System.out.println("target url >> " +targetURL);
			
			return targetURL;
			
	 }
	 public HttpURLConnection getConnection(String targetURL){
		 String response = "";
		 HttpURLConnection con=null;
		    try {
				URL url = new URL(targetURL);
				con= (HttpURLConnection)url.openConnection();
				con.setRequestMethod("GET");
				System.out.println(con.getResponseCode());
				System.out.println(con.getResponseMessage());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 return con;
		 
	 }
}
