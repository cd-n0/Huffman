

import java.io.*;

class BinaryIO{
    public static byte[] stringToBytes(String binaryString) {
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

    public static void writeByteArrayToFile(byte[] dataForWriting, String filePath) {
        try (FileOutputStream outputStream = new FileOutputStream(new File(filePath))) {
            outputStream.write(dataForWriting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readFileToByteArray(String filePath) {
        try (FileInputStream inputStream = new FileInputStream(new File(filePath))) {
            byte[] buffer = new byte[(int) new File(filePath).length()];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) {
                return null; // File is empty
            }
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
