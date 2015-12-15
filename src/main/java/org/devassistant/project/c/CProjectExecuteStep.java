/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.project.c;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.context.UINavigationContext;
import org.jboss.forge.addon.ui.result.NavigationResult;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class CProjectExecuteStep extends AbstractUICommand implements UIWizardStep
{
   private static final String FILES_PATH = "/files/crt/c";

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      Project project = (Project) context.getUIContext().getAttributeMap().get(Project.class);
      final Resource<?> root = project.getRoot();
      copy(FILES_PATH, root);
      return Results.success();
   }

   private void copy(String source, Resource<?> target)
            throws IOException, URISyntaxException
   {
      URI resource = getClass().getClassLoader().getResource(source).toURI();
      try (FileSystem fileSystem = FileSystems.newFileSystem(resource, Collections.emptyMap()))
      {
         Path start = fileSystem.getPath(source);
         try (Stream<Path> walk = Files.list(start))
         {
            for (Iterator<Path> it = walk.iterator(); it.hasNext();)
            {
               Path filePath = it.next();
               String name = filePath.getName(filePath.getNameCount() - 1).toString();
               FileResource<?> newResource = target.getChild(name).reify(FileResource.class);
               try (OutputStream os = newResource.getResourceOutputStream())
               {
                  Files.copy(filePath, os);
               }
            }
         }
      }
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
   }

   @Override
   public NavigationResult next(UINavigationContext context) throws Exception
   {
      return null;
   }

}
