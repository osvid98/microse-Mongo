package com.dailycodebuffer.springbootmongodb;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbDriverMongo {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * - Devuelve el nombre del primer campo de una coleccion -
     * El objetivo de este método es identificar la el campo que contiene el ID de
     * cualquier coleccion, permitiendo así la inserción de un ID faltante o el siguiente ID disponible.
     *
     * @param collectionName El nombre de la tabla.
     * @return El nombre de la primera columna de la tabla.
     */
    public String getFirstColumnName(String collectionName) {
        String firstColumnName = null;
        Document firstDocument = mongoTemplate.getCollection(collectionName).find().first();
        if (firstDocument != null && !firstDocument.keySet().isEmpty()) {
            firstColumnName = firstDocument.keySet().iterator().next();
        }
        return firstColumnName;
    }
}
