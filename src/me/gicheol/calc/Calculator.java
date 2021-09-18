package me.gicheol.calc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filePath) throws IOException {
        BufferedReaderCallback sumCallback = new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                Integer sum = 0;
                String line = null;

                while ((line = br.readLine()) != null) {
                    sum += Integer.parseInt(line);
                }

                return sum;
            }
        };

        return fileReadTemplate(filePath, sumCallback);
    }


    public Integer calcMultiply(String numFilePath) throws IOException {
        BufferedReaderCallback multiplyCallback = new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                Integer multiply = 1;
                String line = null;

                while((line = br.readLine()) != null) {
                    multiply *= Integer.parseInt(line);
                }

                return multiply;
            }
        };

        return fileReadTemplate(numFilePath, multiplyCallback);
    }

    public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));
            int ret = callback.doSomethingWithReader(br);

            return ret;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        }

    }

}
