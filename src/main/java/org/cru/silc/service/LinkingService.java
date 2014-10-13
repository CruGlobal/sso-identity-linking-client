package org.cru.silc.service;

/**
 * Created by lee.braddock on 10/13/14.
 */
import org.cru.silc.exception.LinkingException;
import org.cru.silc.domain.Identity;

public interface LinkingService
{
	public Identity getLinkedIdentityByProviderType(Identity identity, Identity.ProviderType providerType) throws
			LinkingException;

	public void linkIdentities(Identity first, Identity second) throws LinkingException;

	public void deleteLink(Identity identity) throws LinkingException;

}
