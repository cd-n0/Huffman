import java.io.*;

public class FileIO {
    public static String readFile(String fname){
        StringBuilder stringBuilder = new StringBuilder();
        File filename = new File(fname);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))){
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line + "\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
        return stringBuilder.toString();
    }
    public static String writeFile(String fname, String content){
        StringBuilder stringBuilder = new StringBuilder();
        File filename = new File(fname);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))){
            bufferedWriter.write(content);
            bufferedWriter.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
        return stringBuilder.toString();
    }
    public static byte[] readBinaryFile(String fname){
        File filename = new File(fname);
        byte[] byteArray = new byte[(int) filename.length()];
        try (FileInputStream fileInputStream = new FileInputStream(filename)){
                fileInputStream.read(byteArray);
                fileInputStream.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
        return byteArray;
    }

    public static void writeBinaryFile(String fname, byte[] content){
        File filename = new File(fname);
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)){
                fileOutputStream.write(content);
                fileOutputStream.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    public static void Serialize(String outputFile, Huffman huffman){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputFile + ".ser"));
            out.writeObject(huffman);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Object Deserialize(String inputFile){
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(inputFile));
            Object readObject  = in.readObject();
            in.close();
            return readObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
