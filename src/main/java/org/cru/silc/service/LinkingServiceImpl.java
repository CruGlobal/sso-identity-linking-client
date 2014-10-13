package org.cru.silc.service;

/**
 * Created by lee.braddock on 10/13/14.
 */
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.cru.silc.domain.Identities;
import org.cru.silc.domain.Identity;
import org.cru.silc.exception.LinkingException;
import org.cru.silc.util.Marshaler;
import org.cru.silc.util.WebResourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.util.Map;

/**
 * lee.braddock
 */
public class LinkingServiceImpl implements LinkingService
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String resource;

	public void setResource(String resource)
	{
		this.resource = resource;
	}

	private String identitiesAccessToken;

	public void setIdentitiesAccessToken(String identitiesAccessToken)
	{
		this.identitiesAccessToken = identitiesAccessToken;
	}

	public Identity getLinkedIdentityByProviderType(Identity identity, Identity.ProviderType providerType) throws LinkingException
	{
		WebResource.Builder webResource = getWebResource(identity);

		// issue GET request and get a response
		ClientResponse response = webResource.get(ClientResponse.class);

		if (response.getStatus() != 200)
			throwException("get identity by provider type failed for " + identity.toString() + " and provider type " + providerType.toString() + " with http error code " + response.getStatus());

		String xmlResponse = response.getEntity(String.class);

		logger.info("get identity by provider type xml response for " + identity.getId() + " is " + xmlResponse);

		return getIdentityByProviderType(xmlResponse, providerType);
	}

	public void linkIdentities(Identity first, Identity second) throws LinkingException
	{
		WebResource.Builder webResource = getWebResource(first, ContentIndicator.HAS_CONTENT);

		String xml = marshalIdentity(second);

		logger.info("link identities for " + first.getId() + "," + first.getType() + " is " + xml);

		// issue POST request and get a response
		ClientResponse response = webResource.post(ClientResponse.class, xml);

		if (response.getStatus() != 201)
			throwException("link identities failed for " + first.toString() + " and " + second.toString() + " with http error code " + response.getStatus());
	}

	public void deleteLink(Identity identity) throws LinkingException
	{
		WebResource.Builder webResource = getWebResource(identity);

		// issue DELETE request and get a response
		ClientResponse response = webResource.delete(ClientResponse.class);

		if (response.getStatus() != 200) // TODO should return 204 since there is no content returned
			throwException("delete link failed for " + identity.toString() + " with http error code " + response.getStatus());
	}

	private Identity getIdentityByProviderType(String xml, Identity.ProviderType providerType) throws LinkingException
	{
		Identities identities = unmarshalIdentities(xml);

		for (Identity identity : identities.getIdentities())
			if (providerType.equals(identity.getType()))
				if (!Strings.isNullOrEmpty(identity.getId()))
					return identity;

		return null;
	}

	private String marshalIdentity(Identity identity) throws LinkingException
	{
		try
		{
			return Marshaler.marshal(identity, javax.xml.bind.Marshaller.JAXB_FRAGMENT);
		}
		catch(JAXBException e)
		{
			logger.error("marshalling error for identity " + identity.getId() + "," + identity.getType(), e);
			throw new LinkingException(e);
		}
	}

	private Identities unmarshalIdentities(String xml) throws LinkingException
	{
		try
		{
			return Marshaler.unmarshal(xml, Identities.class);
		}
		catch(JAXBException e)
		{
			logger.error("unmarshalling error for xml " + xml, e);
			throw new LinkingException(e);
		}
	}

	private enum ContentIndicator
	{
		HAS_CONTENT, NONE
	}

	private WebResource.Builder getWebResource(Identity identity)
	{
		return getWebResource(identity, ContentIndicator.NONE);
	}

	private WebResource.Builder getWebResource(Identity identity, ContentIndicator contentIndicator)
	{
		String resourceUri = resourceUri(identity.getId(), identity.getType());

		Map<String, String> headers = ImmutableMap.of("Authorization", "Bearer " + identitiesAccessToken);

		MediaType acceptType = MediaType.APPLICATION_XML_TYPE;

		MediaType contentType = MediaType.APPLICATION_XML_TYPE;

		return WebResourceBuilder.build(resourceUri, headers, acceptType, contentIndicator.equals(ContentIndicator
				.HAS_CONTENT) ? contentType : null);
	}

	private String resourceUri(String id, String type)
	{
		return resource + "/" + type + "/" + id;
	}

	private void throwException(String message) throws LinkingException
	{
		logger.error(message);

		throw new LinkingException(message);
	}
}
