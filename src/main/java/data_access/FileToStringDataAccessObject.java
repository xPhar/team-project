package data_access;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Represents a DAO to read a file on the computer and turn it into a string
 * or save from a string to a file on the computer.
 */
public class FileToStringDataAccessObject {

    /**
     * Read a file and turn it into a string of UTF-8 encoding.
     * @param file the file to be read from
     * @return a string (in UTF-8 encoding) of the file content
     * @throws IOException if something happens during file reading
     * @throws FileNotFoundException if the program can't find the file or the
     *                               user have no read permission for the file
     */
    public String readFileToString(File file) throws IOException, FileNotFoundException {

        final FileInputStream fis = new FileInputStream(file);
        final byte[] bytes = new byte[(int) file.length()];
        fis.read(bytes);
        fis.close();

        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Save a string representing the content to a file.
     * @param fileContent the string representing the content of the file to be saved
     * @param file the file to write the fileContent to
     * @throws IOException if something happens when writing to the file
     * @throws FileNotFoundException if the file is a directory or if the user has no write access
     *                               to the file
     */
    public static void saveFileFromString(String fileContent, File file) throws IOException, FileNotFoundException {

        final FileOutputStream fos = new FileOutputStream(file);
        fos.write(fileContent.getBytes());
        fos.close();
    }
}

