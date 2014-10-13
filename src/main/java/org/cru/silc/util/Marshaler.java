package org.cru.silc.util;

/**
 * Created by lee.braddock on 10/13/14.
 */

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class Marshaler
{
    public static <T> String marshal(T t, String... properties) throws JAXBException
    {
        StringWriter stringWriter = null;

        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(t.getClass());

            Marshaller marshaller = jaxbContext.createMarshaller();

            for (String property : properties)
                marshaller.setProperty(property, Boolean.TRUE);

            stringWriter = new StringWriter();

            marshaller.marshal(t, stringWriter);

            return stringWriter.toString();
        } finally
        {
            try
            {
                if (stringWriter != null)
                    stringWriter.close();
            }
            catch (Exception e)
            {
            }
        }
    }

    public static <T> T unmarshal(String xml, Class<T> clazz, String... properties) throws JAXBException
    {
        StringReader stringReader = null;

        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            for (String property : properties)
                unmarshaller.setProperty(property, Boolean.TRUE);

            stringReader = new StringReader(xml);

            return (T) unmarshaller.unmarshal(stringReader);
        } finally
        {
            try
            {
                if (stringReader != null)
                    stringReader.close();
            }
            catch (Exception e)
            {
            }
        }
    }
}