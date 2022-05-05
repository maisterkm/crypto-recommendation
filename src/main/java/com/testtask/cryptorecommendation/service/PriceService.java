package com.testtask.cryptorecommendation.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.testtask.cryptorecommendation.exception.CryptoNotFoundException;
import com.testtask.cryptorecommendation.model.CryptoAsset;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PriceService {

    private final HashMap<String,String> mapSymbolPath = new HashMap<>(); //key->symbol of crypto(i.e. BTC), value->path_file in main/java/resource/data

    @PostConstruct
    private void postConstruct() {
        mapSymbolToFile();
    }

    public CryptoAsset getOldestPrice(String symbol) {
        return readCryptoBySymbol(symbol.toUpperCase())
                .stream()
                .min(Comparator.comparing(CryptoAsset::getDate))
                .orElseThrow(NoSuchElementException::new);
    }

    public CryptoAsset getNewestPrice(String symbol) {
        return readCryptoBySymbol(symbol.toUpperCase())
                .stream()
                .max(Comparator.comparing(CryptoAsset::getDate))
                .orElseThrow(NoSuchElementException::new);
    }

    public CryptoAsset getMinPrice(String symbol) {
        return readCryptoBySymbol(symbol.toUpperCase())
                .stream()
                .min(Comparator.comparing(CryptoAsset::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }

    public CryptoAsset getMaxPrice(String symbol) {
        return readCryptoBySymbol(symbol.toUpperCase())
                .stream()
                .max(Comparator.comparing(CryptoAsset::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }

    public List<CryptoAsset> getSortedCryptosByNormalizedRange() {

        Map<String,BigDecimal> mapSymbolRange = new HashMap<>();
        List<CryptoAsset> resultList = new ArrayList<>();
        List<String> pathFiles = getAllFilesInDirectory();

        for(String path : pathFiles) {
            Set<String> symbolSet = getSymbolByPath2(path);

            String symbol = getSymbolByPath2(path).stream().findFirst().get();
            BigDecimal range = calculateNormalizedRange(symbol);
            mapSymbolRange.put(symbol, range);
        }

        for(Map.Entry<String,BigDecimal> entry : mapSymbolRange.entrySet()) {
            CryptoAsset cryptoAsset = new CryptoAsset();
            cryptoAsset.setSymbol(entry.getKey());
            cryptoAsset.normalizedRange = entry.getValue();
            resultList.add(cryptoAsset);
        }

        return resultList.stream()
                .sorted(Comparator.comparing(CryptoAsset::getNormalizedRange).reversed()).collect(Collectors.toList());
    }

    public CryptoAsset getCryptoWithHighestRangeByDay(String date) {
        List<CryptoAsset> allCrypto = readAllCrypto();
        LocalDate day = LocalDate.parse(date);

        Map<String,List<CryptoAsset>> mapCryptoByDay = new HashMap<>(); //Ключ -> символ, значение -> список крипты в данный день

        return allCrypto.stream()
                .filter(crypto -> crypto.getDate().isEqual(day))
                .peek(c -> c.setNormalizedRange(calculateNormalizedRange(c.getSymbol())))
                .max(Comparator.comparing(CryptoAsset::getNormalizedRange))
                .orElseThrow(NoSuchElementException::new);
    }

    public BigDecimal calculateNormalizedRange(String symbol) {
        MathContext mc = new MathContext(3);
        return getMaxPrice(symbol).getPrice().subtract(getMinPrice(symbol).getPrice()).divide(getMinPrice(symbol).getPrice(), mc);
    }

    private Set<String> getSymbolByPath2(String valuePath) {
        return mapSymbolPath
                .entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), valuePath))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    //todo Читает при каждом обращении, сделать какой-то кеш
    private List<CryptoAsset> readCryptoBySymbol(String symbol) {

        String path = mapSymbolPath.get(symbol);
        if(path == null) {
            //todo : to fix CryptoNotFoundException
            throw new CryptoNotFoundException("The crypto token you entered (" + symbol + ") is not supported by our recommendation service");
        }

        String[] record;
        List<CryptoAsset> cryptoAssets = new ArrayList<>();

        try(CSVReader reader = new CSVReader(new FileReader(path))) {
            int counter = 1;

            while ((record = reader.readNext()) != null) {

                CryptoAsset cryptoAsset = new CryptoAsset();
                if (counter == 1) {
                    counter++;
                    continue;
                }

                LocalDate time = Instant.ofEpochMilli(Long.parseLong(record[0])).atZone(ZoneId.systemDefault()).toLocalDate();
                cryptoAsset.setDate(time);
                cryptoAsset.setSymbol(record[1]);
                cryptoAsset.setPrice(new BigDecimal(record[2]));
                cryptoAssets.add(cryptoAsset);
                counter++;
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return cryptoAssets;
    }

    private List<String> getAllFilesInDirectory() {
        List<String> listOfFiles = new ArrayList<>();

        try (Stream<Path> walk = Files.walk(Paths.get("src/main/resources/data/"))) {
            listOfFiles = walk.map(Path::toString)
                    .filter(f -> f.endsWith(".csv")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listOfFiles;
    }

    private void mapSymbolToFile() {
        List<String> listOfFiles = getAllFilesInDirectory();
        listOfFiles.forEach(x -> mapSymbolPath.put(getSymbolByPath(x), x));
        listOfFiles.forEach(System.out::println);
    }

    private String getSymbolByPath(String path) {
        String[] record;
        List<CryptoAsset> cryptoAssets = new ArrayList<>();

        try(CSVReader reader = new CSVReader(new FileReader(path))) {
            int counter = 1;
            while ((record = reader.readNext()) != null) {
                CryptoAsset cryptoAsset = new CryptoAsset();
                if (counter == 1) {
                    counter++;
                    continue;
                }

                LocalDate time = Instant.ofEpochMilli(Long.parseLong(record[0])).atZone(ZoneId.systemDefault()).toLocalDate();
                cryptoAsset.setDate(time);
                cryptoAsset.setSymbol(record[1]);
                cryptoAsset.setPrice(new BigDecimal(record[2]));
                cryptoAssets.add(cryptoAsset);
                counter++;
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return cryptoAssets.get(0).getSymbol();
    }

    private List<CryptoAsset> readAllCrypto() {
        List<CryptoAsset> allCryptos = new ArrayList<>();
        List<String> files = getAllFilesInDirectory();

        for(String file : files) {
            String symbol = getSymbolByPath2(file).stream().findFirst().get();
            allCryptos.addAll(readCryptoBySymbol(symbol));
        }
        return allCryptos;
    }
}
