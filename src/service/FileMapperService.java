package service;

import Format.Category;
import Format.Index;
import dto.FIleContent;
import exeption.WrongFileFormat;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileMapperService {

    public FIleContent mapToFileContent(File file) throws WrongFileFormat {

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            FIleContent fIleContent = new FIleContent();

            String line;
            while ((line = br.readLine())!=null){
                String[] temp = line.split(" ");
                List<String> contentOfLine = Arrays.asList(temp);
                if(contentOfLine.size() < 9 ){
                    throw  new WrongFileFormat("File: " + file.getName() + " has wrong format, line: " + line);
                }

                for (String s : contentOfLine) {


                    if(s.equalsIgnoreCase("category")) {
                        String categoryName = contentOfLine.get(contentOfLine.indexOf(s) + 2);
                        Category category = Category.valueOf(categoryName.toUpperCase());
                        fIleContent.setCategory(category);
                    }

                    switch (s.toLowerCase()) {
                        case "name" -> fIleContent.setName(contentOfLine.get(contentOfLine.indexOf(s) + 2));
                        case "password" -> fIleContent.setPassword(contentOfLine.get(contentOfLine.indexOf(s) + 2));
                        case "login" -> fIleContent.setLogin(contentOfLine.get(contentOfLine.indexOf(s) + 2));
                        case "website" -> fIleContent.setWebsite(contentOfLine.get(contentOfLine.indexOf(s) + 2));
                        case "location" -> fIleContent.setLocation(contentOfLine.get(contentOfLine.indexOf(s) + 2));
                        case "index" -> fIleContent.setIndex(new Index(contentOfLine.get(contentOfLine.indexOf(s) + 2)));
                    }

                }



            }

            if(fIleContent.getIndex().getIndexName().isEmpty()){
                fIleContent.setIndex(new Index(IndexGenerator.generateIndex()));

                //ask for master password to encrypt the file
            }else {
                //ask to enter master to decrypt the file
            }

        }catch (IOException e){
            System.out.println("smth went wrong while reading the content of file: " + file.getName());
            e.printStackTrace();
        }
    }
}
