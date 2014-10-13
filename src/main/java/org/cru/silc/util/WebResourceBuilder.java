package org.cru.silc.util;

/**
 * Created by lee.braddock on 10/13/14.
 */
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.Map;

public class WebResourceBuilder
{
	public static WebResource.Builder build(String resource, Map<String, String> headers, MediaType acceptType, MediaType contentType)
	{
		Client client = Client.create();

		// construct a web resource
		WebResource webResource = client.resource(resource);

		// get the web resource builder
		WebResource.Builder builder	= webResource.getRequestBuilder();

		// add headers
		for (Map.Entry<String, String> entry : headers.entrySet())
			builder.header(entry.getKey(), entry.getValue());

		// specify acceptable media type(s)
		if(acceptType != null)
			builder.accept(acceptType);

		// specify content media type(s)
		if(contentType != null)
			builder.type(contentType);

		return builder;
	}
}
