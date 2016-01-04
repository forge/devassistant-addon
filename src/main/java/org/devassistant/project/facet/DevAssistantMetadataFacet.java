/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.project.facet;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.devassistant.project.DevAssistantProjectProvider;
import org.jboss.forge.addon.dependencies.Dependency;
import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.parser.yaml.resource.YamlResource;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectProvider;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.furnace.container.simple.lifecycle.SimpleContainer;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class DevAssistantMetadataFacet extends AbstractFacet<Project> implements MetadataFacet
{
   @Override
   public boolean install()
   {
      FileResource<?> configFile = getConfigFile();
      if (!configFile.exists())
         configFile.createNewFile();
      return true;
   }

   @SuppressWarnings("unchecked")
   @Override
   public String getProjectName()
   {
      Map<String, Object> model = getConfig().getModel().orElseGet(LinkedHashMap::new);
      Map<String, String> kwargs = (Map<String, String>) model.getOrDefault("original_kwargs", new HashMap<>());
      return kwargs.get("name");
   }

   @SuppressWarnings("unchecked")
   @Override
   public MetadataFacet setProjectName(String name)
   {
      YamlResource config = getConfig();
      Map<String, Object> model = config.getModel().orElseGet(LinkedHashMap::new);
      Map<String, String> kwargs = (Map<String, String>) model.getOrDefault("original_kwargs", new HashMap<>());
      kwargs.put("name", name);
      model.put("original_kwargs", kwargs);
      config.setContents(model);
      return this;
   }

   @Override
   public String getTopLevelPackage()
   {
      return null;
   }

   @Override
   public String getProjectGroupName()
   {
      return null;
   }

   @Override
   public MetadataFacet setTopLevelPackage(String groupId)
   {
      return this;
   }

   @Override
   public MetadataFacet setProjectGroupName(String groupId)
   {
      return this;
   }

   @Override
   public String getProjectVersion()
   {
      // FIXME: Change this
      return "1.0";
   }

   @Override
   public MetadataFacet setProjectVersion(String version)
   {
      return this;
   }

   @Override
   public Dependency getOutputDependency()
   {
      return null;
   }

   @Override
   public Map<String, String> getEffectiveProperties()
   {
      return Collections.emptyMap();
   }

   @Override
   public Map<String, String> getDirectProperties()
   {
      return Collections.emptyMap();
   }

   @Override
   public String getEffectiveProperty(String name)
   {
      return null;
   }

   @Override
   public String getDirectProperty(String name)
   {
      return null;
   }

   @Override
   public MetadataFacet setDirectProperty(String name, String value)
   {
      return this;
   }

   @Override
   public String removeDirectProperty(String name)
   {
      return null;
   }

   @Override
   public ProjectProvider getProjectProvider()
   {
      return SimpleContainer.getServices(getClass().getClassLoader(), DevAssistantProjectProvider.class).get();
   }

   @Override
   public boolean isValid()
   {
      return isInstalled();
   }

   @Override
   public boolean isInstalled()
   {
      YamlResource config = getConfig();
      return config instanceof YamlResource && config.exists();
   }

   private YamlResource getConfig()
   {
      Resource<?> child = getConfigFile();
      return child.reify(YamlResource.class);
   }

   private FileResource<?> getConfigFile()
   {
      FileResource<?> child = (FileResource<?>) getFaceted().getRoot().getChild(".devassistant");
      return child;
   }
}
