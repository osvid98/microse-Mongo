package com.dailycodebuffer.springbootmongodb.controller;

import com.dailycodebuffer.springbootmongodb.classes.InsertionResult;
import com.dailycodebuffer.springbootmongodb.collection.Person;
import com.dailycodebuffer.springbootmongodb.collection.Address;
import com.dailycodebuffer.springbootmongodb.service.PersonService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.dailycodebuffer.springbootmongodb.DbDriverMongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private DbDriverMongo dbDriverMongo;

    @PostMapping
    public String save(@RequestBody Person person) {
        return personService.save(person);
    }

    @GetMapping
    public List<Person> getPersonStartWith(@RequestParam("name") String name) {
        return personService.getPersonStartWith(name);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        personService.delete(id);
    }

    @GetMapping("/age")
    public List<Person> getByPersonAge(@RequestParam Integer minAge,
                                       @RequestParam Integer maxAge) {
        return personService.getByPersonAge(minAge,maxAge);
    }

    @GetMapping("/search")
    public Page<Person> searchPerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {
        Pageable pageable
                = PageRequest.of(page,size);
        return personService.search(name,minAge,maxAge,city,pageable);
    }

    @GetMapping("/oldestPerson")
    public List<Document> getOldestPerson() {
        return personService.getOldestPersonByCity();
    }

    @GetMapping("/populationByCity")
    public List<Document> getPopulationByCity() {
        return personService.getPopulationByCity();
    }

    @GetMapping("/firstColumnName")
    public String getFirstColumnName() {
        String firstColumnName = dbDriverMongo.getFirstColumnName("person");
        if (firstColumnName != null) {
            return "El ID del primer documento en la colección es: " + firstColumnName;
        } else {
            return "No se pudo encontrar ningún documento en la colección.";
        }
    }

    @PostMapping("/insert")
    public String insert() {
        Map<String, Object> dataPersona = new HashMap<>();
        dataPersona.put("nombre", "DAVID_Embebido");
        dataPersona.put("edad", 12);

        Map<String, Object> direccion = new HashMap<>();
        direccion.put("calle", "123 Main St");
        direccion.put("ciudad", "Ciudad Ejemplo");
        direccion.put("estado", "Estado Ejemplo");

        dataPersona.put("direccion", direccion);

        // Insertar la persona en la base de datos
        boolean insercionExitosa = dbDriverMongo.insert("person", dataPersona);

        return "Resultado: " + insercionExitosa;

    }

    @PostMapping("/insertWithId")
    public String insertWithId() {
        Map<String, Object> dataPersona = new HashMap<>();
        dataPersona.put("_id", 120);
        dataPersona.put("nombre", "DAVID_Retorno2");
        dataPersona.put("edad", 12);

        Map<String, Object> direccion = new HashMap<>();
        direccion.put("calle", "123 Main St");
        direccion.put("ciudad", "Ciudad Ejemplo");
        direccion.put("estado", "Estado Ejemplo");

        dataPersona.put("direccion", direccion);

        // Insertar la persona en la base de datos
        InsertionResult insercionExitosa = dbDriverMongo.insertWithId("person", dataPersona);

        return "Resultado: " + insercionExitosa;
    }
}
