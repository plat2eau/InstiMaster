package com.instimaster;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class InstiMasterApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl="http://localhost";

	private static TestRestTemplate template;


	@BeforeAll
	public static void init() {
		template = new TestRestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port+"");
	}

	@Bean
	public RestTemplate restTemplate() {
		HttpClient httpClient = null;
		try {
			httpClient = HttpClients.custom()
					.setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
							.setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
									.setSslContext(SSLContextBuilder.create()
											.loadTrustMaterial(TrustAllStrategy.INSTANCE)
											.build())
									.setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
									.build())
							.build())
					.build();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		}

		HttpComponentsClientHttpRequestFactory requestFactory =
				new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);

		return new RestTemplate(requestFactory);
	}
}
