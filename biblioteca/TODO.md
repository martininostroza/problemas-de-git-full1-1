# TODO - Arreglo de errores (biblioteca)

- [ ] Corregir los `package` incorrectos `com.biblioteca.biblioteca.*` a `com.example.biblioteca.*` en controller/service/repository/model.
- [ ] Actualizar imports en esos archivos para que apunten al package corregido.
- [ ] Blindar `comprarCarrito` contra `itemsCarrito == null` (evitar NPE).
- [ ] Ejecutar `mvn -q test` (o `mvn -q -DskipTests package`) para verificar que compila.

