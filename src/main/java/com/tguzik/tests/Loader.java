package com.tguzik.tests;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unit test utility that loads files. Primary use case is reading text files
 * and using the contents as expected values in unit tests. This is placed in
 * main sources (not test sources) to allow using this class in projects that
 * depend on this library.
 * <p/>
 * Loaded files have their newline characters normalized to single '\n' character
 * using {@link com.tguzik.tests.Normalize#newLines(String)}. Loaded files are
 * not trimmed - whitespace at the beginning and the end of the file will be
 * preserved.
 *
 * @author Tomasz Guzik <tomek@tguzik.com>
 * @since 0.1
 */
public enum Loader {
    ;

    @Nullable
    public static String loadFile( @Nonnull Path path ) throws IOException {
        byte[] fileBytes = java.nio.file.Files.readAllBytes( path );
        String contents = new String( fileBytes, StandardCharsets.UTF_8 );
        return Normalize.newLines( contents );
    }

    @Nullable
    public static String loadFile( @Nonnull String prefix,
                                   @Nonnull Class<?> classFromPackage,
                                   @Nonnull String subdirectory,
                                   @Nonnull String fileName ) throws IOException {
        String classPackage = classFromPackage.getPackage().getName().replace( ".", "/" );
        return loadFile( Paths.get( prefix, classPackage, subdirectory, fileName ) );
    }

    @Nullable
    public static String loadFile( @Nonnull String prefix,
                                   @Nonnull String directory,
                                   @Nonnull String subdirectory,
                                   @Nonnull String fileName ) throws IOException {
        return loadFile( Paths.get( prefix, directory, subdirectory, fileName ) );
    }

    @Nullable
    /** Assumes that the prefix will be 'src/test/java/' */
    public static String loadFile( @Nonnull Class<?> classFromPackage,
                                   @Nonnull String subdirectory,
                                   @Nonnull String fileName ) throws IOException {
        return loadFile( "src/test/java", classFromPackage, subdirectory, fileName );
    }
}
