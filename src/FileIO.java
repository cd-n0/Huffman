

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
        }
        catch (IOException e){
            System.out.println(e);
        }
        return stringBuilder.toString();
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
