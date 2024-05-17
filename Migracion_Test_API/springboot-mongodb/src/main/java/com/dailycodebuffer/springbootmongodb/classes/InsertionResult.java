package com.dailycodebuffer.springbootmongodb.classes;

/**
 * Esta clase representa el resultado de una operación de inserción.
 * Se utiliza mayormente cuando es invocado el método insertWithId de la clase DbDriver.
 * Su propósito es proporcionar información sobre el éxito de la operación de inserción y, en caso de éxito, el ID faltante asociado a esa inserción.
 *
 * El motivo de existencia de esta clase se debe a que, al insertar datos en tablas relacionadas que utilizan secuencias de ID,
 * es necesario recuperar el ID o valor que establece la relación entre las tablas. El proceso de inserción automática verifica si
 * hay ID faltantes en la secuencia, y si los encuentra, inserta el siguiente número o valor disponible.
 */
public class InsertionResult {
    /**
     * Indica si la operación de inserción fue exitosa.
     */
    private boolean success;

    /**
     * El ID faltante en caso de que la operación sea exitosa.
     */
    private int missingId;

    /**
     * Crea una instancia de la clase InsertionResult.
     *
     * @param success   Indica si la operación de inserción fue exitosa.
     * @param missingId El ID faltante en caso de que la operación sea exitosa.
     */
    public InsertionResult(boolean success, int missingId) {
        this.success = success;
        this.missingId = missingId;
    }

    /**
     * Verifica si la operación de inserción fue exitosa.
     *
     * @return `true` si la operación fue exitosa, de lo contrario `false`.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Establece el estado del resultado de la inserción.
     *
     * @param success El estado de la inserción a establecer.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Obtiene el ID faltante en caso de que la operación de inserción sea exitosa.
     *
     * @return El ID faltante.
     */
    public int getMissingId() {
        return missingId;
    }

    /**
     * Establece el ID faltante en caso de que la operación de inserción sea exitosa.
     *
     * @param missingId El ID faltante a establecer.
     */
    public void setMissingId(int missingId) {
        this.missingId = missingId;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + success + '\'' +
                ", edad=" + missingId +
                '}';
    }
}
