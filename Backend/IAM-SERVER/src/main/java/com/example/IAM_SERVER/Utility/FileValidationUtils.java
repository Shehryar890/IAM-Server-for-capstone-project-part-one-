package com.example.IAM_SERVER.Utility;




import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public final class FileValidationUtils {

    private FileValidationUtils() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated.");
    }

    /**
     * Validates that a path is non-null and absolute,
     * then returns its normalized representation.
     */
    public static Path validateAbsolutePath(
            Path path,
            String description) {

        requireDescription(description);

        if (path == null) {
            throw new InvalidFilePathException(
                    description + " path must not be null.");
        }

        if (!path.isAbsolute()) {
            throw new InvalidFilePathException(
                    description + " path must be absolute.");
        }

        try {
            return path.normalize();
        } catch (InvalidPathException exception) {
            throw new InvalidFilePathException(
                    description + " path is invalid.", exception);
        }
    }

    /**
     * Validates that a path identifies an existing,
     * readable regular file.
     *
     * Symbolic links are followed, so the target must
     * resolve to a readable regular file.
     */
    public static void validateReadableRegularFile(
            Path path,
            String description) {

        requireDescription(description);

        if (path == null) {
            throw new InvalidFilePathException(
                    description + " path must not be null.");
        }

        if (!path.isAbsolute()) {
            throw new InvalidFilePathException(
                    description + " path must be absolute.");
        }

        try {
            BasicFileAttributes attributes =
                    Files.readAttributes(
                            path,
                            BasicFileAttributes.class);

            if (!attributes.isRegularFile()) {
                throw new InvalidFileException(
                        description + " must be a regular file.");
            }

            if (!Files.isReadable(path)) {
                throw new FileAccessException(
                        description + " is not readable.");
            }

        } catch (InvalidFileException
                 | FileAccessException exception) {

            throw exception;

        } catch (IOException | SecurityException exception) {
            throw new FileValidationException(
                    "Unable to validate " + description + ".",
                    exception);
        }
    }

    /**
     * Rejects identical normalized paths.
     *
     * This is a lexical comparison; it does not prove
     * that two different paths cannot refer to the same
     * underlying filesystem file.
     */
    public static void validateDifferentPaths(
            Path first,
            Path second) {

        if (first == null || second == null) {
            throw new InvalidFilePathException(
                    "Paths must not be null.");
        }

        if (!first.isAbsolute() || !second.isAbsolute()) {
            throw new InvalidFilePathException(
                    "Both paths must be absolute.");
        }

        Path normalizedFirst = first.normalize();
        Path normalizedSecond = second.normalize();

        if (normalizedFirst.equals(normalizedSecond)) {
            throw new InvalidFilePathException(
                    "Keystore and password file must be different paths.");
        }
    }

    private static void requireDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "File description must not be blank.");
        }
    }

    public static class FileValidationException
            extends RuntimeException {

        public FileValidationException(String message) {
            super(message);
        }

        public FileValidationException(
                String message,
                Throwable cause) {
            super(message, cause);
        }
    }

    public static final class InvalidFilePathException
            extends FileValidationException {

        public InvalidFilePathException(String message) {
            super(message);
        }

        public InvalidFilePathException(
                String message,
                Throwable cause) {
            super(message, cause);
        }
    }

    public static final class InvalidFileException
            extends FileValidationException {

        public InvalidFileException(String message) {
            super(message);
        }
    }

    public static final class FileAccessException
            extends FileValidationException {

        public FileAccessException(String message) {
            super(message);
        }
    }
}
