/*
 * Copyright (C) 2011 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.portal.config.serialize;

import org.exoplatform.portal.config.model.LocalizedValue;
import org.gatein.common.i18n.LocaleFormat;
import org.gatein.common.util.ConversionException;
import org.jibx.runtime.IAliasable;
import org.jibx.runtime.IMarshaller;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshaller;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.JiBXParseException;
import org.jibx.runtime.impl.UnmarshallingContext;

import java.util.Locale;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 */
public class LocalizedValueMapper implements IUnmarshaller, IAliasable, IMarshaller
{

   /** . */
   private String marshalURI;

   /** . */
   private String marshallName;

   /** . */
   private int marshallIndex;

   public LocalizedValueMapper(String uri, int index, String name)
   {
      this.marshalURI = uri;
      this.marshallName = name;
      this.marshallIndex = index;
   }

   public LocalizedValueMapper()
   {
      this.marshalURI = null;
      this.marshallName = "label";
      this.marshallIndex = 0;
   }

   public boolean isPresent(IUnmarshallingContext ctx) throws JiBXException
   {
      return ctx.isAt(marshalURI, marshallName);
   }

   public Object unmarshal(Object o, IUnmarshallingContext ictx) throws JiBXException
   {
      UnmarshallingContext ctx = (UnmarshallingContext)ictx;
      if (!ctx.isAt(marshalURI, marshallName))
      {
         ctx.throwStartTagNameError(marshalURI, marshallName);
      }
      int count = ctx.getAttributeCount();
      Locale lang = null;
      for (int i = 0;i < count;i++)
      {
         String attrName = ctx.getAttributeName(i);
         if (attrName.equals("lang"))
         {
            String attrValue= ctx.getAttributeValue(i).trim();
            try
            {
               lang = LocaleFormat.RFC3066_LANGUAGE_TAG_NO_CACHE.getLocale(attrValue);
            }
            catch (ConversionException e)
            {
               throw new JiBXParseException("The attribute lang does not represent a valid locale", attrValue, e);
            }
            break;
         }
      }
      ctx.parsePastStartTag(marshalURI, marshallName);
      String value = ctx.getText();
      ctx.parsePastEndTag(marshalURI, marshallName);
      return new LocalizedValue(value, lang);
   }

   //


   public boolean isExtension(String s)
   {
      throw new UnsupportedOperationException();
   }

   public void marshal(Object o, IMarshallingContext iMarshallingContext) throws JiBXException
   {
      throw new UnsupportedOperationException();
   }
}
