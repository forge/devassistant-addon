/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.project.c;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.devassistant.utils.Resources;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.output.UIOutput;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;
import org.jboss.forge.furnace.util.Streams;

/**
 * Creates a project configured for the C language
 * 
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class CProjectExecuteStep extends AbstractUICommand implements UIWizardStep
{
   private static final String FILES_PATH = "/files/crt/c";

   FileResource<?> postCreate;

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      UIContext uiContext = context.getUIContext();
      UIOutput output = uiContext.getProvider().getOutput();
      Project project = (Project) uiContext.getAttributeMap().get(Project.class);
      final DirectoryResource root = project.getRoot().reify(DirectoryResource.class);
      final String basename = root.getName();
      // copy(FILES_PATH, root);
      Resources.process(FILES_PATH, (path, contents) -> {
         String name = path.getName(path.getNameCount() - 1).toString();
         byte[] newContents = contents;
         switch (name)
         {
         case "configure.ac":
         {
            newContents = new String(newContents).replaceAll("CDevelopmentTool", basename).getBytes();
            Resources.setContents(root, name, newContents);
            break;
         }
         case "cdevelopmenttool.spec":
         {
            name = basename + ".spec";
            String tmp = new String(newContents);
            tmp = tmp.replaceAll("cdevelopmenttool", basename);
            String user = System.getProperty("user.name");
            String email = System.getProperty("user.email", "");
            String replaced = String.format("%1$ta %1$tb %1$te %1$tY %2$s <%3$s>", Calendar.getInstance(), user, email);
            tmp = tmp.replaceAll("Fri Mar 15 2013 UserName <user@host>", replaced);
            newContents = tmp.getBytes();
            Resources.setContents(root, name, newContents);
            break;
         }
         case "post_create.sh":
         {
            postCreate = Resources.setContents(root, name, newContents);
            postCreate.setExecutable(true);
            break;
         }
         default:
         {
            Resources.setContents(root, name, newContents);
         }
         }
      });
      // Execute post_create.sh
      Process process = new ProcessBuilder("./post_create.sh")
               .directory(root.getUnderlyingResourceObject()).start();
      ExecutorService executor = Executors.newFixedThreadPool(2);
      // Read std out
      executor.submit(() -> Streams.write(process.getInputStream(), output.out()));
      // Read std err
      executor.submit(() -> Streams.write(process.getErrorStream(), output.err()));
      executor.shutdown();
      Result result = null;

      try
      {
         int returnCode = process.waitFor();
         if (returnCode == 0)
         {
            result = Results.success();
         }
         else
         {
            result = Results.fail("Error while executing post_create.sh. Result code=" + returnCode
                     + ". See output for more details");
         }
      }
      catch (InterruptedException ie)
      {
         result = Results.success("Command execution interrupted");
      }
      finally
      {
         process.destroy();
      }
      // Delete post_create.sh
      postCreate.delete();
      return result;
   }
}
