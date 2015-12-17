/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devassistant.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class Paths
{

   /**
    * Returns the file name for a given {@link Path}
    * 
    * @param path
    * @return
    */
   public static String getFileName(Path path)
   {
      return path.getName(path.getNameCount() - 1).toString();
   }

   /**
    * Traverses a path in this JAR
    * 
    * @param source
    * @param consumer
    * @throws IOException
    * @throws URISyntaxException
    */
   public static void process(String source, BiConsumer<Path, byte[]> consumer)
            throws IOException, URISyntaxException
   {
      URI resource = Resources.class.getClassLoader().getResource(source).toURI();
      try (FileSystem fileSystem = FileSystems.newFileSystem(resource, Collections.emptyMap()))
      {
         Path start = fileSystem.getPath(source);
         try (Stream<Path> walk = Files.list(start))
         {
            for (Iterator<Path> it = walk.iterator(); it.hasNext();)
            {
               Path filePath = it.next();
               byte[] contents = Files.readAllBytes(filePath);
               consumer.accept(filePath, contents);
            }
         }
      }
   }

}
