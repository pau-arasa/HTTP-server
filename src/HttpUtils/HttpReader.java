package HttpUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HttpReader {

    public static String ReadLine(InputStream in) throws IOException {
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        int prev = -1, curr;
        int maxLineLength = 1024;

        while ((curr = in.read()) != -1) {
            if (buff.size() > maxLineLength) throw new IOException("Line is too long");

            buff.write(curr);

            if (prev == '\r' && curr == '\n') break;    //CLRF = line end

            prev = curr;
        }

        byte[] lineBytes = buff.toByteArray();

        if (lineBytes.length < 2 ||
            lineBytes[lineBytes.length - 2] != '\r'  ||
            lineBytes[lineBytes.length - 1] != '\n' )
        {
            throw new IOException("Bad Request");
        }

        return new String(lineBytes, 0, lineBytes.length - 2, StandardCharsets.US_ASCII);
    }
}
