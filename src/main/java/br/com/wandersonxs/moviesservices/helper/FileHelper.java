package br.com.wandersonxs.moviesservices.helper;

import br.com.wandersonxs.moviesservices.handler.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class FileHelper {

    private static final Integer COLUMN_PRODUCER = 3;
    private static final String CSV_COLUMN_SPLITTER = ";";
    private static final String PRODUCERS_SPLITTER = ",";
    private static final String PRODUCERS_COMMON_CONJUNCTION = "and ";
    private static final String EMPTY = "";
    private static final String HEADER = "year;title;studios;producers;winner";

    public List<String> readLinesCsv(String filename) throws Exception {

        List<String> lines = new ArrayList<>();

        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filename)))) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();

            if (!HEADER.equalsIgnoreCase(line.trim())) {
                log.error("Csv structure not valid. Please, check it out and try again.");
                throw new BusinessException("Csv structure not valid. Please, check it out and try again.");
            }

            while (line != null) {
                line = bufferedReader.readLine();

                if (line != null) {
                    lines.add(line);
                }
            }

        } catch (IOException | NullPointerException ex) {
            log.error("File not found or error on file reading.", ex);
            throw new BusinessException("File not found or error on file reading.");
        }
        return lines;
    }

    public List<String> getRawProducers(List<String> lines) {

        List<String> rawProducers = new ArrayList<>();

        for (String line : lines) {
            rawProducers.addAll(getProducers(line));
        }
        return rawProducers.stream().distinct().toList();
    }

    public List<String> getRawProducers(String line) {
        return getProducers(line).stream().distinct().toList();
    }

    private List<String> getProducers(String line) {
        List<String> rawProducers = new ArrayList<>();

        String[] lineRaw = line.split(CSV_COLUMN_SPLITTER);

        String[] rawProducer = lineRaw[COLUMN_PRODUCER].split(PRODUCERS_SPLITTER);

        for (int i = 0; i < rawProducer.length; i++) {
            String producer = rawProducer[i].trim();
            producer = producer.replace(PRODUCERS_COMMON_CONJUNCTION, EMPTY);
            rawProducers.add(producer);
        }

        return rawProducers;
    }

}
