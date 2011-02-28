/*
 * Copyright (C) 2010 eXo Platform SAS.
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

package org.exoplatform.portal.mop.navigation;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface Scope
{

   Scope SINGLE = new Scope()
   {
      private Visitor instance = new Visitor()
      {
         public VisitMode visit(int depth, String nodeId, String nodeName)
         {
            return VisitMode.NODE;
         }
      };
      public Visitor get()
      {
         return instance;
      }
   };

   Scope CHILDREN = new Scope()
   {
      public Visitor get()
      {
         return new Visitor()
         {
            boolean done = false;
            public VisitMode visit(int depth, String nodeId, String nodeName)
            {
               if (done)
               {
                  return VisitMode.NODE;
               }
               else
               {
                  done = true;
                  return VisitMode.CHILDREN;
               }
            }
         };
      }
   };

   Scope ALL = new Scope()
   {
      private Visitor instance = new Visitor()
      {
         public VisitMode visit(int depth, String nodeId, String nodeName)
         {
            return VisitMode.CHILDREN;
         }
      };
      public Visitor get()
      {
         return instance;
      }
   };

   Visitor get();

   public interface Visitor
   {
      VisitMode visit(
         int depth,
         String nodeId,
         String nodeName);
   }
}