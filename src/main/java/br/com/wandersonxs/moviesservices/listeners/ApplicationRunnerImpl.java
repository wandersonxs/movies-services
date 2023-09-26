package br.com.wandersonxs.moviesservices.listeners;

import br.com.wandersonxs.moviesservices.helper.FileHelper;
import br.com.wandersonxs.moviesservices.service.MovieService;
import br.com.wandersonxs.moviesservices.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final ProducerService producerService;
    private final MovieService movieService;
    private final FileHelper fileHelper;

    public void configEnvironment(String fileNamePath, boolean hasHeader) {

        // Validar estrutura CSV

        List<String> linhasCsv = fileHelper.readLinesCsv(fileNamePath);

        producerService.saveAll(linhasCsv);
        movieService.saveAll(linhasCsv);
    }

    @Override
    public void run(ApplicationArguments args) {

        final String fileNamePath = args.getSourceArgs().length > 0 ? args.getSourceArgs()[0] : "/movielist.csv";
        final String hasHeader = args.getSourceArgs().length > 1 ? args.getSourceArgs()[1] : "yes";

        InputStream inputStream = getClass().getResourceAsStream(fileNamePath);

        if (inputStream == null) {
            log.error("{} ::: File not found!", fileNamePath);
            System.exit(0);
        }

        configEnvironment(fileNamePath, hasHeader.equalsIgnoreCase("yes"));
    }

}
