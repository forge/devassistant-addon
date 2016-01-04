/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.project.cpp;

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
public class CPPProjectExecuteStep implements UIWizardStep
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
      int status = Resources.execute(parentFile, output.out(),
               output.err(), "da", "create", "cpp", "-n", root.getName());
      if (status == 0)
      {
         return Results.success();
      }
      else
      {
         return Results.fail("Error while executing da. Exit code: " + status);
      }
   }
}
