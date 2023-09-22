package br.com.wandersonxs.moviesservices.helper;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class FileHelper {

    public List<String> readLinesCsv(String filename) {

        List<String> lines = new ArrayList<>();

        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filename)))) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();

            while (line != null) {
                line = bufferedReader.readLine();
                if (line != null) {
                    lines.add(line);
                }
            }

        } catch (IOException e) {
            log.error("Erro ao ler arquivo.", e);
        }
        return lines;
    }

}
