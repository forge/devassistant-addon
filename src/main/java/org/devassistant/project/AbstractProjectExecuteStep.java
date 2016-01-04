/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.project;

import java.io.File;

import org.devassistant.utils.Resources;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.output.UIOutput;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public abstract class AbstractProjectExecuteStep implements UIWizardStep
{
   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      UIContext uiContext = context.getUIContext();
      UIOutput output = uiContext.getProvider().getOutput();
      Project project = (Project) uiContext.getAttributeMap().get(Project.class);
      DirectoryResource root = project.getRoot().reify(DirectoryResource.class);
      // Remove previously created dir
      root.delete(true);
      File parentFile = root.getParent().getUnderlyingResourceObject();
      String[] command = getCommand(uiContext, root.getName());
      int status = Resources.execute(parentFile, output.out(), output.err(), command);
      if (status == 0)
      {
         return Results.success();
      }
      else
      {
         return Results.fail("Error while executing da. Exit code: " + status);
      }
   }

   /**
    * @param context TODO
    * @param project
    * @return the commands to be executed
    */
   protected abstract String[] getCommand(UIContext context, String projectName);

}
