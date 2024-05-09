import java.io.*;

public class FileIO {
    public static String readFile(String fname) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        File filename = new File(fname);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line + "\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static String writeFile(String fname, String content) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        File filename = new File(fname);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
        bufferedWriter.write(content);
        bufferedWriter.close();
        return stringBuilder.toString();
    }
    public static byte[] readBinaryFile(String fname) throws FileNotFoundException, IOException{
        File filename = new File(fname);
        byte[] byteArray = new byte[(int) filename.length()];
        FileInputStream fileInputStream = new FileInputStream(filename);
        fileInputStream.read(byteArray);
        fileInputStream.close();
        return byteArray;
    }

    public static void writeBinaryFile(String fname, byte[] content)throws IOException{
        File filename = new File(fname);
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        fileOutputStream.write(content);
        fileOutputStream.close();
    }

    public static void Serialize(String outputFile, Huffman huffman) throws FileNotFoundException, IOException{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputFile + ".ser"));
        out.writeObject(huffman);
        out.close();
    }

    public static Object Deserialize(String inputFile) throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(inputFile));
        Object readObject  = in.readObject();
        in.close();
        return readObject;
    }
}
