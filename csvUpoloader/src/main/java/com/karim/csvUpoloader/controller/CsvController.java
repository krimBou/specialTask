package com.karim.csvUpoloader.controller;

import com.karim.csvUpoloader.entity.Person;
import com.karim.csvUpoloader.entity.PersonRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/csv-data")
public class CsvController {

    @Autowired
    PersonRepository personService;
    @PostMapping
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) throws Exception{
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        if(!extension.equals("csv")){
            return new ResponseEntity("File have to be csv", HttpStatus.BAD_REQUEST);
        }

        List<Person> personList = new ArrayList<>();

        InputStream inputStream = file.getInputStream();

        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> allRecords = parser.parseAllRecords(inputStream);

        allRecords.forEach(record -> {
                Person person = new Person();
                person.setName(record.getString("Name"));
                person.setEmail(record.getString("Email"));
                person.setPhoneNumber(record.getString("PhoneNumber"));
                personList.add(person);
            });

        personService.saveAll(personList);
        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping
    public List<Person> downloadData(){
        return personService.findAll();
    }
}

