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
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class Paths
{
   /**
    * Returns the file name for a given {@link Path} relative to another {@link Path}
    * 
    * @param path the path which the filename will be resolved
    * @param start the start path
    * @return
    */
   public static String getFileName(Path path, Path start)
   {
      return start.relativize(path).normalize().toString();
   }

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
   public static void process(String source, TriConsumer<Path, Path, byte[]> consumer)
            throws IOException, URISyntaxException
   {
      URI resource = Resources.class.getClassLoader().getResource(source).toURI();
      try (FileSystem fileSystem = FileSystems.newFileSystem(resource, Collections.emptyMap()))
      {
         Path start = fileSystem.getPath(source);
         Files.walkFileTree(start, new SimpleFileVisitor<Path>()
         {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
               byte[] contents = Files.readAllBytes(file);
               consumer.accept(start, file, contents);
               return FileVisitResult.CONTINUE;
            }
         });
      }
   }

}
