/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.project;

import java.util.Arrays;

import org.devassistant.project.facet.DevAssistantProjectFacet;
import org.jboss.forge.addon.projects.AbstractProjectType;
import org.jboss.forge.addon.projects.ProjectFacet;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public abstract class AbstractDevAssistantProjectType extends AbstractProjectType
{
   @Override
   public Iterable<Class<? extends ProjectFacet>> getRequiredFacets()
   {
      return Arrays.<Class<? extends ProjectFacet>> asList(DevAssistantProjectFacet.class,
               DevAssistantProjectFacet.class);
   }

   @Override
   public int priority()
   {
      return Integer.MAX_VALUE;
   }

}
