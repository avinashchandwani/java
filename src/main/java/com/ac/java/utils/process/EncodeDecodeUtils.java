
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EncodeDecodeUtils {

    public static String getBase64EncodedValue() throws IOException {
        byte[] inFileBytes = Files.readAllBytes(Paths.get("C:\\Avinash\\personal\\akshay\\1MB.pdf"));
        byte[] encoded = org.apache.commons.codec.binary.Base64.encodeBase64(inFileBytes);
        String fileContent = new String(encoded);
        System.out.println(fileContent);
        return fileContent;
    }

    public static String getBase64EncodedValue(InputStream inputStream) {
        byte[] bData;
        String fileContent;
        try {
            bData = FileCopyUtils.copyToByteArray(inputStream);
            byte[] encoded = Base64.encodeBase64(bData);
            fileContent = new String(encoded);
            inputStream.close();
        } catch (IOException e) {
            try {
                inputStream.close();
            } catch (IOException ioException) {
                throw new SMSServiceException(SMSExceptionCode.BAD_REQUEST, "Issues with the file uploaded");
            }
            throw new SMSServiceException(SMSExceptionCode.BAD_REQUEST, "Issues with the file uploaded");
        }
        return fileContent;
    }

    public static InputStream getFileFromBase64EncodedValue(String encodedString) {
        byte[] decodedBytes = Base64.decodeBase64(encodedString);
        InputStream targetInputStream = null;
        targetInputStream = new ByteArrayInputStream(decodedBytes);
        return targetInputStream;
    }
}
