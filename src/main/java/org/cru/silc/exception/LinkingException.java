package org.cru.silc.exception;

/**
 * Created by lee.braddock on 10/13/14.
 */
public class LinkingException extends Exception
{
    private static final long serialVersionUID = -4324411778119490111L;

    public LinkingException(Throwable cause)
    {
        super(cause);
    }

    public LinkingException(String message)
    {
        super(message);
    }
}
