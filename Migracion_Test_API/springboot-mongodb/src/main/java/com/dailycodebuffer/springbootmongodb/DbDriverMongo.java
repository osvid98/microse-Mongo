package com.dailycodebuffer.springbootmongodb;

import com.dailycodebuffer.springbootmongodb.classes.InsertionResult;
import com.mongodb.MongoException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DbDriverMongo {

    @Autowired
    private MongoTemplate mongoTemplate;

    /** [getFirstFieldName]
     *
     * Devuelve el nombre del primer campo de una coleccion.
     *
     * El objetivo de este método es identificar el campo que contiene el ID de
     * cualquier coleccion,permitiendo así la inserción de un ID faltante
     * o el siguiente ID disponible.
     *
     * @param collectionName El nombre de la coleccion.
     * @return El nombre del primero campo de la coleccion.
     */
    public String getFirstColumnName(String collectionName) {
        try {
            Document firstDocument = mongoTemplate.getCollection(collectionName).find().first();
            return firstDocument != null ? firstDocument.keySet().iterator().next() : null;
        } catch (MongoException e) {
            System.err.println("Error al acceder a la colección: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Inserta un nuevo registro en una colección de la base de datos.
     *
     * @param collection El nombre de la colección.
     * @param data Los datos del registro a insertar.
     * @return true si se inserta el registro correctamente, false en caso contrario.
     */
    public boolean insert(String collection, Map<String, Object> data) {
        try {
            mongoTemplate.insert(data, collection);
            return true;
        } catch (MongoException e) {
            System.err.println("Error al insertar el registro en la colección '" + collection + "': " + e.getMessage());
            return false;
        }
    }

    /**
     * Inserta un nuevo registro en una colección de la base de datos.
     *
     * @param collection El nombre de la colección.
     * @param data Los datos del registro a insertar, en formato.
     * @return El resultado de la inserción junto con el ID asignado.
     */
    public InsertionResult insertWithId(String collection, Map<String, Object> data) {
        try {
            Document document = new Document(data);
            mongoTemplate.insert(document, collection);
            int id = (int) document.get("_id");
            return new InsertionResult(true, id);
        } catch (MongoException e) {
            System.err.println("Error al insertar el registro en la colección '" + collection + "': " + e.getMessage());
            return new InsertionResult(false, -1);
        }
    }
}
