package org.fhirappcloud.httpclient.service;

import javax.net.ssl.SSLContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.fhirappcloud.domain.PatientFHIRAccess;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Service
public class OauthHTTPService {
	
	 
	public PatientFHIRAccess getAuthCode(String URL, String client_id , String redirect_uri, String launch, String state) {
		CloseableHttpClient client = null;
		HttpPost post = null;
		HttpResponse response = null;
		String responseData = null;
		String tokenData = null;
		PatientFHIRAccess patientFHIRAccess = null;
		try {
		    
		    
			client = HttpClients.custom().setSSLSocketFactory(getNoAuthSSLContext()).build();
 			//client = HttpClientBuilder.create().build();
			if (!URL.isEmpty()) {
 				post = new HttpPost(URL);
				List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
				nameValueList.add(new BasicNameValuePair("response_type", "code"));
				nameValueList.add(new BasicNameValuePair("client_id", client_id));
				nameValueList.add(new BasicNameValuePair("redirect_uri", redirect_uri));
				nameValueList.add(new BasicNameValuePair("launch", launch ));
				nameValueList.add(new BasicNameValuePair("state", state ));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValueList);
				post.setEntity(entity);
				response = client.execute(post);
				Map<String,String>headerMap = new<String,String>HashMap();
				Header[] headers = response.getAllHeaders();
				for (Header header : headers) {
					headerMap.put(header.getName(), URLDecoder.decode(header.getValue(), StandardCharsets.UTF_8.name()));

				}
				
				if (headerMap.containsKey("Location")) {
					responseData = headerMap.get("Location")  ;
					 
				}
				
				try {
					MultiValueMap<String, String> URLparameters =
					        UriComponentsBuilder.fromUri(new URI(responseData)).build().getQueryParams();
					
					String authCode = URLparameters.getFirst("code");
					String authState = URLparameters.getFirst("state");
					
					post = new HttpPost("https://open-ic.epic.com/Argonaut/oauth2/token");
					nameValueList = new ArrayList<NameValuePair>();
					nameValueList.add(new BasicNameValuePair("grant_type", "authorization_code"));
					nameValueList.add(new BasicNameValuePair("code", authCode));
					nameValueList.add(new BasicNameValuePair("redirect_uri", redirect_uri));
					nameValueList.add(new BasicNameValuePair("client_id", client_id));
					entity = new UrlEncodedFormEntity(nameValueList);
					post.setEntity(entity);
					response = client.execute(post);
					if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
						ObjectMapper mapper = new ObjectMapper();
						tokenData = EntityUtils.toString(response.getEntity());
						System.out.println(tokenData);
						JsonNode node = mapper.readValue(tokenData, JsonNode.class);
						JsonNode patientNode = node.get("patient");
					    String patient = patientNode.asText();
					    System.out.println(patient);
						patientFHIRAccess = mapper.readValue(tokenData ,  PatientFHIRAccess.class);
						
						
						
						
					}
					 
					HttpGet request = new HttpGet("https://open-ic.epic.com/FHIR/api/FHIR/DSTU2/Patient/" +patientFHIRAccess.getPatient() ); 
					request.addHeader("Bearer", patientFHIRAccess.getAccessToken());
					response = client.execute(request);
					if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
						System.out.println(EntityUtils.toString(response.getEntity()));
					}
					 
					return patientFHIRAccess;
					
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//log.info(lmsStudentData);
				if (!tokenData.isEmpty() ) {
					//System.out.println(aiccResponse.toString());
					 
				}
				
				
				
				
			}
		} catch (IOException e) {
			//log.error(e, e);
		} finally {
			try {
				if (response != null && response.getEntity().getContent() != null) {
					response.getEntity().getContent().close();
				}
			} catch (IOException e) {
				//log.error(e, e);
			}

			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					//log.error(e, e);
				}
			}

			if (post != null) {
				try {
					post.releaseConnection();
				} catch (Exception e) {
					//log.error(e, e);
				}
			}

		}
		return null;
	}
	
	private SSLConnectionSocketFactory getNoAuthSSLContext(){
		SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.custom()
			        .loadTrustMaterial(null, new TrustStrategy() {
 						@Override
						public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
								throws CertificateException {
							 
							return true;
						}
			        })
			        .build();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	            sslContext,
	            NoopHostnameVerifier.INSTANCE);
	    return sslsf;
	}
}
