import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class MD5Checksum {

    /**
     * проверить правильность компирования файлов файлов через хешсуммы
     */
    public static boolean sameHash(File copy, File original) throws Exception {
        return getMD5Checksum(copy.getAbsolutePath())
                .equals(getMD5Checksum(original.getAbsolutePath()));
    }

    private static byte[] createChecksum(String filename) throws Exception {
        try (InputStream fis = new FileInputStream(filename)) {

            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            int numRead;

            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);

            return complete.digest();
        }
    }

    // see this How-to for a faster way to convert
    // a byte array to a HEX string
    private static String getMD5Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";

        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}