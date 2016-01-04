/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.resource;

import java.io.File;

import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.resource.ResourceGenerator;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class DevAssistantResourceGenerator implements ResourceGenerator<DevAssistantYamlResource, File>
{
   @Override
   public boolean handles(Class<?> type, Object resource)
   {
      if (!(resource instanceof File))
      {
         // Do not handle non-file resources (yet)
         return false;
      }
      String fileName = ((File) resource).getName().toLowerCase();
      return fileName.equals(".devassistant");
   }

   @SuppressWarnings("unchecked")
   @Override
   public <T extends Resource<File>> T getResource(ResourceFactory factory, Class<DevAssistantYamlResource> type,
            File resource)
   {
      return (T) new DevAssistantYamlResource(factory, resource);
   }

   @Override
   public <T extends Resource<File>> Class<?> getResourceType(ResourceFactory factory,
            Class<DevAssistantYamlResource> type,
            File resource)
   {
      return DevAssistantYamlResource.class;
   }

}
