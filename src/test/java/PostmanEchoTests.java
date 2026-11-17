import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;


public class PostmanEchoTests {

    // Базовый адрес сервиса. Вынесен в константу, чтобы не дублировать в каждом тесте.
    private static final String BASE_URL = "https://postman-echo.com";

    // ==========================================
    // ТЕСТ 1: GET /get
    // ==========================================
    @Test
    public void testGetMethod() {
        given()
                .baseUri(BASE_URL)     // Подготовка: задаём базовый URL
                .when()
                .get("/get")           // Действие: отправляем GET-запрос на /get
                .then()
                .statusCode(200)       // Проверка: сервер ответил кодом 200 (OK)
                .body(containsString("https://postman-echo.com/get"));
        // Проверка тела: ответ должен содержать URL запроса (эхо-ответ)
    }

    // ==========================================
    // ТЕСТ 2: POST /post
    // ==========================================
    @Test
    public void testPostMethod() {
        // Тело запроса в формате JSON (экранируем кавычки для Java-строки)
        String requestBody = "{\"name\": \"John\", \"role\": \"QA\"}";

        given()
                .baseUri(BASE_URL)
                .contentType("application/json") // Указываем, что отправляем JSON
                .body(requestBody)               // Прикрепляем тело к запросу
                .when()
                .post("/post")                   // Отправляем POST-запрос
                .then()
                .statusCode(200)                 // Проверка кода ответа
                // Проверка тела: сервер должен вернуть наши данные обратно
                .body(containsString("John"))
                .body(containsString("QA"));
    }

    // ==========================================
    // ТЕСТ 3: PUT /put
    // ==========================================
    @Test
    public void testPutMethod() {
        // Тело запроса для PUT (полное обновление ресурса)
        String requestBody = "{\"id\": 1, \"status\": \"updated\"}";

        given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/put")                     // Отправляем PUT-запрос
                .then()
                .statusCode(200)
                // Проверяем, что сервер вернул наши поля обратно
                .body(containsString("\"id\":1"))
                .body(containsString("\"status\":\"updated\""));
    }

    // ==========================================
    // ТЕСТ 4: PATCH /patch
    // ==========================================
    @Test
    public void testPatchMethod() {
        // Тело запроса для PATCH (частичное обновление)
        String requestBody = "{\"email\": \"test@example.com\"}";

        given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch("/patch")                 // Отправляем PATCH-запрос
                .then()
                .statusCode(200)
                // Проверяем, что сервер вернул наш email
                .body(containsString("test@example.com"));
    }

    // ==========================================
    // ТЕСТ 5: DELETE /delete
    // ==========================================
    @Test
    public void testDeleteMethod() {
        given()
                .baseUri(BASE_URL)
                .when()
                .delete("/delete")               // Отправляем DELETE-запрос
                .then()
                .statusCode(200)
                // Проверяем, что в ответе есть URL удалённого ресурса
                .body(containsString("https://postman-echo.com/delete"));
    }
}