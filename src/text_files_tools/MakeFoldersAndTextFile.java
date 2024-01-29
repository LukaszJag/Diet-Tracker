package text_files_tools;

import Ganeral.Config;
import products_tools.Product;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MakeFoldersAndTextFile {
    public static void makeEmptyFile(String newFileName, String destination) {
        try {
            File newFile = new File(destination + newFileName + ".txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred. - Class MakeTextFile -> makeEmptyFile");
            e.printStackTrace();
        }
    }

    public static void makeTextFileForProduct(Product product, float macroFor) {
        String fileName = product.getName();;
        MakeFoldersAndTextFile.makeEmptyFile(fileName, Config.DESTINATION_FOR_TEXT_FILE_PRODUCTS);


        try {
            MakeFoldersAndTextFile.writeProductToFile("Name: " + product.getName(), fileName);
            MakeFoldersAndTextFile.writeProductToFile("Brand: " + product.getBrand(), fileName);
            MakeFoldersAndTextFile.writeProductToFile("Package has: " + product.getWeight_of_pack(), fileName);

            MakeFoldersAndTextFile.writeProductToFile("Macro for: " + macroFor, fileName);

            MakeFoldersAndTextFile.writeProductToFile("KCal: " + product.getProduct_macro().getKcal(), fileName);
            MakeFoldersAndTextFile.writeProductToFile("Protein: " + product.getProduct_macro().getProtein(), fileName);
            MakeFoldersAndTextFile.writeProductToFile("Fat: " + product.getProduct_macro().getFat(), fileName);

            MakeFoldersAndTextFile.writeProductToFile("Carbs: " + product.getProduct_macro().getCarbs(), fileName);


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void makeDirectory(String newDirectoryName, String path) {
        if (path.equals("")) {
            path = Config.DESTINATION_FOR_TEXT_FILE_DAYS;
        }
    }

    public static void writeProductToFile(String lineToWriteToFile, String fileName) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Config.DESTINATION_FOR_TEXT_FILE_PRODUCTS + fileName + ".txt", true));
        bufferedWriter.append(lineToWriteToFile.toString());
        bufferedWriter.append("\n");
        bufferedWriter.close();
    }
}
