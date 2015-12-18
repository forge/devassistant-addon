/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.project.perl;

import org.devassistant.utils.Paths;
import org.devassistant.utils.Resources;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class PerlBasicClassExecuteStep implements UIWizardStep
{
   private static final String FILES_PATH = "/files/crt/perl/class";

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      UIContext uiContext = context.getUIContext();
      Project project = (Project) uiContext.getAttributeMap().get(Project.class);
      final DirectoryResource root = project.getRoot().reify(DirectoryResource.class);

      Paths.process(FILES_PATH, (start, path, contents) -> {
         String name = Paths.getFileName(path, start);
         Resources.setContents(root, name, contents);
      });
      return null;
   }

}
