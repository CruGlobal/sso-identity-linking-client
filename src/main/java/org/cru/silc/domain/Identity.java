package org.cru.silc.domain;

/**
 * Created by lee.braddock on 10/13/14.
 */

import com.google.common.base.Strings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "identity")
@XmlAccessorType(XmlAccessType.FIELD)
public class Identity
{
    @XmlAttribute
    private String id;
    @XmlAttribute
    private String type;
    @XmlAttribute
    private String source;
    @XmlAttribute
    private String strength;
    @XmlAttribute
    private String createdBy;
    @XmlAttribute
    private String updatedBy;

    public enum ProviderType
    {
        RELAY("RELAY"), THE_KEY("THEKEY");

        private String providerType;

        ProviderType(String providerType)
        {
            this.providerType = providerType;
        }

        @Override
        public String toString()
        {
            return providerType;
        }

        public boolean equals(String providerType)
        {
            return !Strings.isNullOrEmpty(providerType) && this.providerType.equalsIgnoreCase(providerType);
        }
    }

    public enum Source
    {
        USER("User"), NONE("");

        private String source;

        Source(String source)
        {
            this.source = source;
        }

        @Override
        public String toString()
        {
            return source;
        }
    }

    public enum Strength
    {
        FULL("1.0"), NONE("");

        private String strength;

        Strength(String strength)
        {
            this.strength = strength;
        }

        @Override
        public String toString()
        {
            return strength;
        }
    }

    public Identity()
    {
    }

    public Identity(String id, String type, String source, String strength, String createdBy, String updatedBy)
    {
        this.id = id;
        this.type = type;
        this.source = source;
        this.strength = strength;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Identity(String id, ProviderType providerType)
    {
        this(id, providerType, Source.NONE, Strength.NONE);
    }

    public Identity(String id, ProviderType providerType, Source source, Strength strength)
    {
        this(id, providerType, source, strength, null, null);
    }

    public Identity(String id, ProviderType providerType, Source source, Strength strength, String createdBy,
                    String updatedBy)
    {
        this(id, providerType.toString(), source.toString(), strength.toString(), createdBy, updatedBy);
    }

    public Identity(Identity identity)
    {
        this(identity.getId(), identity.getType(), identity.getSource(), identity.getStrength(),
                identity.getCreatedBy(), identity.getUpdatedBy());
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getStrength()
    {
        return strength;
    }

    public void setStrength(String strength)
    {
        this.strength = strength;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy()
    {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identity identity = (Identity) o;

        if (createdBy != null ? !createdBy.equals(identity.createdBy) : identity.createdBy != null) return false;
        if (id != null ? !id.equals(identity.id) : identity.id != null) return false;
        if (source != null ? !source.equals(identity.source) : identity.source != null) return false;
        if (strength != null ? !strength.equals(identity.strength) : identity.strength != null) return false;
        if (type != null ? !type.equals(identity.type) : identity.type != null) return false;
        if (updatedBy != null ? !updatedBy.equals(identity.updatedBy) : identity.updatedBy != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (strength != null ? strength.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Identity{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                ", strength='" + strength + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
