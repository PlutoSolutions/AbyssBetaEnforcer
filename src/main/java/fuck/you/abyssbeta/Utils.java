package fuck.you.abyssbeta;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

// i coded all of this myself
public class Utils
{
    public static String encrypt( String in )
    {
        try
        {
            IvParameterSpec iv = new IvParameterSpec( parseHexBinary( "320184ae2fe5a740722a420d94bb1e21" ) );
            SecretKeySpec key = new SecretKeySpec( parseHexBinary( "6b61499284acab1ef47fca087eff654e" ), "AES" );

            Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5PADDING" );
            cipher.init( Cipher.ENCRYPT_MODE, key, iv );

            byte[ ] bytes = cipher.doFinal( in.getBytes( ) );

            StringBuilder builder = new StringBuilder( );
            for( int i = 0; i < bytes.length; i++ )
                builder.append( String.format( "%02x", bytes[ i ] ) );

            return builder.toString( );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }

        return null;
    }

    public static String decrypt(String string) {
        if (string == null) {
            return null;
        }
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(parseHexBinary("320184ae2fe5a740722a420d94bb1e21"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(parseHexBinary("6b61499284acab1ef47fca087eff654e"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            byte[] byArray = cipher.doFinal(parseHexBinary(string));
            return new String(byArray);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static byte[] parseHexBinary(String s) {
        final int len = s.length();
        // "111" is not a valid hex encoding.
        if (len % 2 != 0) {
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);
        }
        byte[] out = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            int h = hexToBin(s.charAt(i));
            int l = hexToBin(s.charAt(i + 1));
            if (h == -1 || l == -1) {
                throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);
            }
            out[i / 2] = (byte) (h * 16 + l);
        }
        return out;
    }

    private static int hexToBin(char c) {
        if ('0' <= c && c <= '9') {
            return c - 48;
        }
        if ('A' <= c && c <= 'F') {
            return c - 65 + 10;
        }
        if ('a' <= c && c <= 'f') {
            return c - 97 + 10;
        }
        return -1;
    }
}
