package com.example.IAM_SERVER.Utility.FileSystemUtility;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Objects;

public final class PathUtils {

    private PathUtils() {
        // Utility class: prevent object creation.
    }


    public static Path validateAbsolutePath(
            Path path,
            String description) {

        Objects.requireNonNull(
                description,
                "Path description cannot be null"
        );

        if (description.isBlank()) {
            throw new IllegalArgumentException(
                    "Path description cannot be blank"
            );
        }

        if (path == null) {
            throw new IllegalArgumentException(
                    description + " cannot be null"
            );
        }

        if (!path.isAbsolute()) {
            throw new IllegalArgumentException(
                    description + " must be an absolute path"
            );
        }

        Path normalizedPath = path.normalize();

        if (normalizedPath.getParent() == null) {
            throw new IllegalArgumentException(
                    description + " must have a parent directory"
            );
        }

        return normalizedPath;
    }




    public static boolean exists(Path path) {

        if (path == null) {
            throw new IllegalArgumentException(
                    "Path cannot be null"
            );
        }

        return Files.exists(
                path,
                LinkOption.NOFOLLOW_LINKS
        );
    }
}
