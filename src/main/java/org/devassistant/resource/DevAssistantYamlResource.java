/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.resource;

import java.io.File;

import org.jboss.forge.addon.parser.yaml.resource.AbstractYamlResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class DevAssistantYamlResource extends AbstractYamlResource
{
   public DevAssistantYamlResource(final ResourceFactory factory, final File file)
   {
      super(factory, file);
   }

   @Override
   public Resource<File> createFrom(File file)
   {
      return new DevAssistantYamlResource(getResourceFactory(), file);
   }

}
