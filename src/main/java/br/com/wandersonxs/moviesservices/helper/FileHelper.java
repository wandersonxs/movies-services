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

    public List<String> getRawProducers(List<String> lines) {

        List<String> rawProducers = new ArrayList<>();

        for (String line : lines) {
            String[] raw = line.split(";");
            String[] rawProducer = raw[3].split(",");
            for (int i = 0; i < rawProducer.length; i++) {

                String producer = rawProducer[i].trim();
                producer = producer.replace("and ", "");
                rawProducers.add(producer);
            }
        }
        return rawProducers.stream().distinct().toList();
    }

    public List<String> getRawProducers(String line) {

        List<String> rawProducers = new ArrayList<>();

        String[] raw = line.split(";");
        String[] rawProducer = raw[3].split(",");
        for (int i = 0; i < rawProducer.length; i++) {

            String producer = rawProducer[i].trim();
            producer = producer.replace("and ", "");
            rawProducers.add(producer);
        }

        return rawProducers.stream().distinct().toList();
    }


}
