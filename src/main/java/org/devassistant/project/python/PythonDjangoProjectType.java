/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.project.python;

import org.jboss.forge.addon.projects.generic.AbstractGenericProjectType;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class PythonDjangoProjectType extends AbstractGenericProjectType
{
   @Override
   public String getType()
   {
      return "Python (Django)";
   }

   @Override
   public Class<? extends UIWizardStep> getSetupFlow()
   {
      return PythonDjangoProjectExecuteStep.class;
   }

   @Override
   public String toString()
   {
      return "python-django";
   }

}