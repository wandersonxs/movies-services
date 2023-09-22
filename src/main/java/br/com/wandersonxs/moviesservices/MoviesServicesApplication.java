package br.com.wandersonxs.moviesservices;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
@Slf4j
public class MoviesServicesApplication  {

    public static void main(String[] args) {
        SpringApplication.run(MoviesServicesApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        log.info("RUNNING..........");
////        InputStream inputStream = getClass().getResourceAsStream("/movielist.csv");
//       // readFile();
//    }

    private List<String[]> readAllLines(Path filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }

    private void readFile() {


        try {
            Reader reader = new InputStreamReader(getClass().getResourceAsStream("/movielist.csv"));
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();

            while (line != null) {
                System.out.println(line);
                // read next line
                line = bufferedReader.readLine();
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
