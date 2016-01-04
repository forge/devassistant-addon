/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.project;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.devassistant.project.facet.DevAssistantMetadataFacet;
import org.devassistant.project.facet.DevAssistantProjectFacet;
import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.projects.AbstractProjectProvider;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectProvider;
import org.jboss.forge.addon.projects.ProvidedProjectFacet;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.furnace.container.simple.lifecycle.SimpleContainer;

/**
 * Abstract class for {@link ProjectProvider} implementations that create {@link GenericProject} projects
 * 
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class DevAssistantProjectProvider extends AbstractProjectProvider
{
   @Override
   public String getType()
   {
      return "DevAssistant";
   }

   @Override
   public Iterable<Class<? extends ProvidedProjectFacet>> getProvidedFacetTypes()
   {
      Set<Class<? extends ProvidedProjectFacet>> result = new HashSet<>();
      result.add(DevAssistantProjectFacet.class);
      result.add(DevAssistantMetadataFacet.class);
      return Collections.unmodifiableSet(result);
   }

   @Override
   public Project createProject(Resource<?> target)
   {
      Project project = new DevAssistantProject(target);
      FacetFactory factory = SimpleContainer.getServices(getClass().getClassLoader(), FacetFactory.class).get();
      factory.install(project, DevAssistantProjectFacet.class);
      factory.install(project, DevAssistantMetadataFacet.class);
      return project;
   }

   @Override
   public boolean containsProject(final Resource<?> target)
   {
      boolean result = false;
      if (target.exists())
      {
         Resource<?> child = target.getChild(".devassistant");
         if (child instanceof FileResource && child.exists())
         {
            result = !((FileResource<?>) child).isDirectory();
         }
      }
      return result;
   }

   @Override
   public int priority()
   {
      return Integer.MAX_VALUE;
   }
}
