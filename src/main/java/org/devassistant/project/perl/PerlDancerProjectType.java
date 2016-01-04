/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.project.perl;

import org.devassistant.project.AbstractProjectExecuteStep;
import org.jboss.forge.addon.projects.generic.AbstractGenericProjectType;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UINavigationContext;
import org.jboss.forge.addon.ui.result.NavigationResult;
import org.jboss.forge.addon.ui.result.navigation.NavigationResultBuilder;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class PerlDancerProjectType extends AbstractGenericProjectType
{
   @Override
   public String getType()
   {
      return "Perl (Dancer)";
   }

   @Override
   public NavigationResult next(UINavigationContext context)
   {
      NavigationResultBuilder builder = NavigationResultBuilder.create();
      builder.add(new AbstractProjectExecuteStep()
      {
         @Override
         protected String[] getCommand(UIContext context, String projectName)
         {
            return new String[] { "da", "create", "perl", "dancer", "-n", projectName };
         }
      });
      return builder.build();
   }

   @Override
   public String toString()
   {
      return "perl-dancer";
   }

}
