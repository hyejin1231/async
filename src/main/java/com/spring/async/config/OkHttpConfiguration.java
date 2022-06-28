package com.spring.async.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpConfiguration
{
	//https://programmer.help/blogs/springboot-uses-resttemplate-okhttp-connection-pool-to-send-http-messages.html
	//https://namjackson.tistory.com/26
	
	@Bean
	public OkHttpClient okHttpClient()
	{
		return new OkHttpClient.Builder()
				.sslSocketFactory(sslSocketFactory(), x509TrustManager())
				.hostnameVerifier(hostnameVerifier())
				.retryOnConnectionFailure(true)//false :  unexpected end of stream on ... java.io.EOFException: \n not found: limit=0 content=…
				.connectionPool(pool())
				.connectTimeout(30, TimeUnit.SECONDS)
				.readTimeout(60, TimeUnit.SECONDS)
				.writeTimeout(60, TimeUnit.SECONDS)
				.build();
	}
	
	@Bean
	public HostnameVerifier hostnameVerifier()
	{
		return new HostnameVerifier()
		{
			@Override
			public boolean verify(String hostname, SSLSession session)
			{
				return true;
			}
		};
	}
	
	@Bean
	public X509TrustManager x509TrustManager()
	{
		return new X509TrustManager()
		{
			@Override
			public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
			{
			}
			
			@Override
			public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
			{
			}
			
			@Override
			public X509Certificate[] getAcceptedIssuers()
			{
				return new X509Certificate[0];
			}
		};
	}
	
	@Bean
	public SSLSocketFactory sslSocketFactory()
	{
		try
		{
			// Trust any link
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { x509TrustManager() }, new SecureRandom());
			return sslContext.getSocketFactory();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (KeyManagementException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Create a new connection pool with tuning parameters appropriate for a
	 * single-user application. The tuning parameters in this pool are subject
	 * to change in future OkHttp releases. Currently
	 */
	@Bean
	public ConnectionPool pool()
	{
		return new ConnectionPool(200, 5, TimeUnit.MINUTES);
	}
}