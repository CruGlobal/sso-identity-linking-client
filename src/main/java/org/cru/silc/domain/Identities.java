package org.cru.silc.domain;

/**
 * Created by lee.braddock on 10/13/14.
 */
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "identities")
@XmlAccessorType(XmlAccessType.FIELD)
public class Identities
{
	@XmlElement(name = "identity")
	private List<Identity> identities = new ArrayList<Identity>();

	public List<Identity> getIdentities()
	{
		return identities;
	}

	public void setIdentities(List<Identity> identities)
	{
		this.identities = identities;
	}
}
