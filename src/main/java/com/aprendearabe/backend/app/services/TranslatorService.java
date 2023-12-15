package com.aprendearabe.backend.app.services;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aprendearabe.backend.app.models.entities.Parameter;

@Service
public class TranslatorService {
	@Autowired
	private ParameterService parameterService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private String endpoint;
	private String keyEndpoint;
	private String host;
	
	private String from;
	private String to;
	
	public String translateEsToAr(String text) {
		boolean ok = init();
		if (ok) {
			from = "es";
			to = "ar";
			String body = "{\"from\": \"" + from + "\", \"to\": \"" + to + "\", \"q\": \"" + text + "\" }";
			log.info(body);
			try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
	            HttpPost httpPost = new HttpPost(endpoint);

	            // Configuración del encabezado de la solicitud
	            httpPost.setHeader("content-type", "application/json");
	            httpPost.setHeader("X-RapidAPI-Key", keyEndpoint);
	            httpPost.setHeader("X-RapidAPI-Host", host);
	            
	            // Establecer el cuerpo de la solicitud
	            StringEntity entity = new StringEntity(body);
	            httpPost.setEntity(entity);

	            // Realizar la solicitud POST
	            HttpResponse response = httpClient.execute(httpPost);

	            // Obtener la respuesta del servidor
	            HttpEntity responseEntity = response.getEntity();
	            String jsonResponse = EntityUtils.toString(responseEntity);
	            return jsonResponse;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Error en la solicitud POST: " + e.getMessage();
	        }
		}
		else {
			return "PARAMETROS INVALIDOS";
		}
    }
	
	public boolean init() {
		Parameter parameter = parameterService.getByName("Rapid Translate Multi Traduction");
		if (parameter!=null) {
			endpoint = parameter.getEndpoint();
			keyEndpoint = parameter.getKeyEndpoint();
			host = parameter.getHost();
			return true;
		}
		else {
			return false;
		}
	}
}
