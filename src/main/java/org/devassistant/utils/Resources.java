/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.furnace.util.Streams;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class Resources
{

   public static int execute(File root, PrintStream out, PrintStream err, String... commands)
            throws InterruptedException, IOException
   {
      // Execute post_create.sh
      Process process = new ProcessBuilder(commands).directory(root).start();
      ExecutorService executor = Executors.newFixedThreadPool(2);
      // Read std out
      executor.submit(() -> Streams.write(process.getInputStream(), out));
      // Read std err
      executor.submit(() -> Streams.write(process.getErrorStream(), err));
      executor.shutdown();
      try
      {
         return process.waitFor();
      }
      finally
      {
         process.destroyForcibly();
      }
   }

   public static FileResource<?> setContents(final Resource<?> root, String name, byte[] newContents)
   {
      FileResource<?> newResource = root.getChild(name).reify(FileResource.class);
      newResource.setContents(new ByteArrayInputStream(newContents));
      return newResource;
   }
}
