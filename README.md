# Consumir API de Películas de StarWars

## ¿Qué vamos a hacer?

- Crear una aplicación para consultar las películas de Star Wars mediante SWAPI
- Menú para que el usuario elija la película que quiere buscar.
- Generar un archivo .JSON con los datos de la película.

## Paso a paso

1. Crear un nuevo proyecto, llamado StarWarsApi
2. Crear la clase Principal
3. Crear una clase Record llamada Pelicula, pasar como parámetros los atributos clave del archivo json de la API.
4. Crear la clase encargada de realizar la búsqueda en la API, la cual se llamara ConsultaPelicula. Esta clase va a tener un método que va a devolver una Pelicula llamado buscarPelicula(), el cual recibe como parámetro un entero con el numeroDePelicual. Importar la dependencia para usar Gson.

```java
    public Pelicula buscarPelicula(int numeroDePelicula) {
        URI direccion = URI.create("https://swapi.py4e.com/api/films/"
                + numeroDePelicula + "/");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), Pelicula.class);
        } catch (Exception e) {
            throw new RuntimeException("No encontré esa película");
        }
    }
```

1. Guardar los resultados dentro de un archivo.
2. Crear una nueva clase llamada GeneradorDeArchivo, la cual tendrá un método de tipo void llamado guardarJson(), que recibe como parámetro un objeto Pelicula.
3. Usar un GsonBuilder() que se encargara de trasformar nuestro objeto en un json.
4. Un objeto FileWriter se encargara de guardar el archivo.
5. Manejar las excepciones para el método una vez terminado el código del método.

```java
public class GeneradorDeArchivo {
    public void guardarJson(Pelicula pelicula) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter escritura = new FileWriter(pelicula.title() + ".json");
        escritura.write(gson.toJson(pelicula));
        escritura.close();
    }
}
```

1. Se crea la instancia de la clase GeneradorDeArchivo en la clase Principal. Y se ejecuta la app.

```java
public class Principal {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        ConsultaPelicula consulta = new ConsultaPelicula();
        System.out.println("Escriba que número de película de Star Wars quiere consultar");
        try {
            var numeroDePelicula = Integer.valueOf(lectura.nextLine());
            Pelicula pelicula = consulta.buscarPelicula(numeroDePelicula);
            System.out.println(pelicula);
            GeneradorDeArchivo generador = new GeneradorDeArchivo();
            generador.guardarJson(pelicula);
        } catch (NumberFormatException e) {
            System.out.println("Número no encontrado " + e.getMessage());
        } catch (RuntimeException | IOException e){
            System.out.println(e.getMessage());
            System.out.println("Finalizando la aplicación");
        }
    }
}
```

## Ejemplo de entrada y salida

![ejemplo-entrada-salida](https://imgur.com/lmnmqSj.png)