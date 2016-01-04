/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.project.perl;

import org.devassistant.project.AbstractProjectExecuteStep;
import org.jboss.forge.addon.ui.context.UIContext;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class PerlBasicClassExecuteStep extends AbstractProjectExecuteStep
{
   @Override
   protected String[] getCommand(UIContext context, String projectName)
   {
      return new String[] { "da", "create", "perl", "class", "-n", projectName };
   }

}
