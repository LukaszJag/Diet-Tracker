package runners.run_update;

import tools.sql_tools.products.ChangeProductTextFiles;
import tools.text_files_tools.FilesTools;

import java.io.IOException;

public class RunFixMacroForAllTextFiles {
    public static void main(String[] args) throws IOException {
        String[] allProductFiles = FilesTools.getStringArrayForAllFilesInDirectory("src/data_store_and_backup/text_files/products");
        for (int i = 0; i < allProductFiles.length; i++) {
            ChangeProductTextFiles.changeMacroForLineLineNumberInFile(allProductFiles[i]);
        }

    }
}
