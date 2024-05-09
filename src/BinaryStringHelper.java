import java.lang.NumberFormatException;

class BinaryStringHelper{
    public static byte[] stringToBytes(String binaryString) throws NumberFormatException{
        byte paddingLength = 0;
        while (binaryString.length() % 8 != 0) {
            binaryString += "0";
            paddingLength++;
        }

        int byteLength = binaryString.length() / 8;
        byte[] byteArray = new byte[byteLength + 1];

        int i;
        for (i = 0; i < byteLength; i++) {
            String byteString = binaryString.substring(8 * i, 8 * (i + 1));
            byte b = (byte) Integer.parseInt(byteString, 2);
            byteArray[i] = b;
        }
        byteArray[i] = paddingLength;

        return byteArray;
    }

    public static String bytesToString(byte[] byteArray) {
        String binaryString = "";

        for (byte currentByte : byteArray) {
            //Due to the bitwise operator & the result value of the expression * currentByte & 0xFF of the type int can be represented as a value of the type unsigned char
            String byteString = Integer.toBinaryString(currentByte & 0xFF);
            while (byteString.length() < 8) {
                byteString = "0" + byteString;
            }
            binaryString += byteString;
        }
        binaryString = binaryString.substring(0, binaryString.length()  - (8 + byteArray[byteArray.length - 1]));

        return binaryString;
    }
}
