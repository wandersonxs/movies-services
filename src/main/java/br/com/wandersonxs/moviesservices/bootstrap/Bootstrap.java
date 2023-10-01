package br.com.wandersonxs.moviesservices.bootstrap;

import br.com.wandersonxs.moviesservices.helper.FileHelper;
import br.com.wandersonxs.moviesservices.service.MovieService;
import br.com.wandersonxs.moviesservices.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class Bootstrap implements ApplicationRunner {

    private final ProducerService producerService;
    private final MovieService movieService;
    private final FileHelper fileHelper;

    private static final String CSV_FILENAME_DEFAULT = "/movielist.csv";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean isFilenamePathOutResource = args.getSourceArgs() != null && args.getSourceArgs().length > 0;
        loadInitialDatabase(args.getSourceArgs().length > 0 ? args.getSourceArgs()[0] : CSV_FILENAME_DEFAULT, isFilenamePathOutResource);
    }

    public void loadInitialDatabase(String fileNamePath, boolean isFilenamePathOutResource) throws Exception {
        List<String> linhasCsv = fileHelper.readLinesCsv(fileNamePath, isFilenamePathOutResource);
        producerService.saveAll(linhasCsv);
        movieService.saveAll(linhasCsv);
    }

}
