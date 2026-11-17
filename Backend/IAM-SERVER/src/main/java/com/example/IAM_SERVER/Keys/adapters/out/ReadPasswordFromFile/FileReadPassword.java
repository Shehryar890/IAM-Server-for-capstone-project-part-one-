
package com.example.IAM_SERVER.Keys.adapters.out.ReadPasswordFromFile;

import com.example.IAM_SERVER.Keys.domain.ports.out.GenericInterfaces.ReadPassword;
import com.example.IAM_SERVER.Utility.FileValidationUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;

public final class FileReadPassword implements ReadPassword<char[], Path> {

    private static final int EXPECTED_PASSWORD_BYTES = 32;
    private static final int EXPECTED_BASE64_LENGTH = 44;
    private static final int MAX_FILE_SIZE = 128;

    @Override
    public char[] readPassword(Path passwordFile) {

        Path validatedPath = FileValidationUtils.validateAbsolutePath(
                passwordFile, "Password file"
        );

        FileValidationUtils.validateReadableRegularFile(
                validatedPath, "Password file"
        );

        byte[] fileBytes = null;
        char[] password = null;

        try {
            long fileSize = Files.size(validatedPath);

            if (fileSize <= 0 || fileSize > MAX_FILE_SIZE) {
                throw new InvalidPasswordFileException(
                        "Password file has an invalid size."
                );
            }

            fileBytes = Files.readAllBytes(validatedPath);

            // Decode file bytes as UTF-8, rejecting malformed input.
            var decoder = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT);

            var decoded = decoder.decode(ByteBuffer.wrap(fileBytes));

            password = new char[decoded.remaining()];
            decoded.get(password);

            // Your PowerShell writer adds no newline or BOM.
            // Reject unexpected characters rather than trimming them.
            if (password.length != EXPECTED_BASE64_LENGTH) {
                throw new InvalidPasswordFileException(
                        "Password file does not contain the expected password format."
                );
            }

            // This validates the Base64 format and expected 32-byte payload.
            // The decoded bytes are NOT used as the keystore password.
            byte[] decodedBytes = null;

            try {
                String encodedPassword = new String(password);

                decodedBytes = Base64.getDecoder().decode(encodedPassword);

                if (decodedBytes.length != EXPECTED_PASSWORD_BYTES) {
                    throw new InvalidPasswordFileException(
                            "Password file does not encode 32 random bytes."
                    );
                }
            } catch (IllegalArgumentException exception) {
                throw new InvalidPasswordFileException(
                        "Password file does not contain valid Base64.",
                        exception
                );
            } finally {
                if (decodedBytes != null) {
                    Arrays.fill(decodedBytes, (byte) 0);
                }
            }

            return password;

        } catch (CharacterCodingException exception) {
            if (password != null) {
                Arrays.fill(password, '\0');
            }

            throw new InvalidPasswordFileException(
                    "Password file is not valid UTF-8.", exception
            );

        } catch (IOException exception) {
            if (password != null) {
                Arrays.fill(password, '\0');
            }

            throw new PasswordFileReadException(
                    "Unable to read the password file.", exception
            );

        } catch (RuntimeException exception) {
            // Do not erase the array after successfully returning it.
            // On failure, clear the local password buffer.
            if (password != null) {
                Arrays.fill(password, '\0');
            }

            throw exception;

        } finally {
            if (fileBytes != null) {
                Arrays.fill(fileBytes, (byte) 0);
            }
        }
    }

    public static class PasswordFileReadException
            extends RuntimeException {

        public PasswordFileReadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InvalidPasswordFileException
            extends PasswordFileReadException {

        public InvalidPasswordFileException(String message) {
            super(message, null);
        }

        public InvalidPasswordFileException(
                String message, Throwable cause) {
            super(message, cause);
        }
    }
}
