package com.example.Teacher;

import com.example.Teacher.Repository.TeacherRepo;
import com.example.Teacher.entity.Teacher;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Teacher Service Integration Tests (H2 Database)")
class TeacherIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TeacherRepo teacherRepo;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        // Clean database before each test
        teacherRepo.deleteAll();
    }

    @Test
    @DisplayName("POST /teacher - Create new teacher successfully")
    void shouldCreateTeacher() {
        String createTeacherJson = """
                {
                    "name": "Ali Haider",
                    "subject": "Mathematics",
                    "email": "ali.haider@example.com"
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createTeacherJson)
                .when()
                .post("/teacher")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Ali Haider"))
                .body("subject", equalTo("Mathematics"))
                .body("email", equalTo("ali.haider@example.com"));
    }

    @Test
    @DisplayName("POST /teacher - Create teacher with null fields")
    void shouldCreateTeacherWithNullFields() {
        String createTeacherJson = """
                {
                    "name": null,
                    "subject": null,
                    "email": null
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createTeacherJson)
                .when()
                .post("/teacher")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", nullValue())
                .body("subject", nullValue())
                .body("email", nullValue());
    }

    @Test
    @DisplayName("GET /teacher - Get all teachers")
    void shouldGetAllTeachers() {
        teacherRepo.save(new Teacher(null, "Ali", "Math", "ali@test.com"));
        teacherRepo.save(new Teacher(null, "Sara", "Physics", "sara@test.com"));
        teacherRepo.save(new Teacher(null, "John", "Chemistry", "john@test.com"));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/teacher")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", equalTo(3))
                .body("name", hasItems("Ali", "Sara", "John"))
                .body("subject", hasItems("Math", "Physics", "Chemistry"));
    }

    @Test
    @DisplayName("GET /teacher/{id} - Get teacher by ID when exists")
    void shouldGetTeacherById_WhenExists() {
        Teacher savedTeacher = teacherRepo.save(
                new Teacher(null, "Ali Haider", "Mathematics", "ali@example.com")
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/teacher/{id}", savedTeacher.getId())
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(savedTeacher.getId().intValue()))
                .body("name", equalTo("Ali Haider"))
                .body("subject", equalTo("Mathematics"))
                .body("email", equalTo("ali@example.com"));
    }

    @Test
    @DisplayName("GET /teacher/{id} - Get teacher by ID when not exists")
    void shouldGetTeacherById_WhenNotExists() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/teacher/{id}", 99999)
                .then()
                .log().all()
                .statusCode(200)
                .body(equalTo(""));
    }

    @Test
    @DisplayName("PUT /teacher/{id} - Update teacher when exists")
    void shouldUpdateTeacher_WhenExists() {
        Teacher savedTeacher = teacherRepo.save(
                new Teacher(null, "Ali", "Math", "ali@old.com")
        );

        String updateTeacherJson = """
                {
                    "name": "Ali Haider Updated",
                    "subject": "Physics",
                    "email": "ali.updated@example.com"
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateTeacherJson)
                .when()
                .put("/teacher/{id}", savedTeacher.getId())
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(savedTeacher.getId().intValue()))
                .body("name", equalTo("Ali Haider Updated"))
                .body("subject", equalTo("Physics"))
                .body("email", equalTo("ali.updated@example.com"));
    }

    @Test
    @DisplayName("PUT /teacher/{id} - Update teacher when not exists")
    void shouldUpdateTeacher_WhenNotExists() {
        String updateTeacherJson = """
                {
                    "name": "Non Existent",
                    "subject": "Test",
                    "email": "test@example.com"
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateTeacherJson)
                .when()
                .put("/teacher/{id}", 99999)
                .then()
                .log().all()
                .statusCode(200)
                .body(equalTo(""));
    }

    @Test
    @DisplayName("DELETE /teacher/{id} - Delete teacher when exists")
    void shouldDeleteTeacher_WhenExists() {
        Teacher savedTeacher = teacherRepo.save(
                new Teacher(null, "Ali", "Math", "ali@test.com")
        );

        assertThat(teacherRepo.findById(savedTeacher.getId()).isPresent(), is(true));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/teacher/{id}", savedTeacher.getId())
                .then()
                .log().all()
                .statusCode(200);

        assertThat(teacherRepo.findById(savedTeacher.getId()).isPresent(), is(false));
    }

    @Test
    @DisplayName("DELETE /teacher/{id} - Delete teacher when not exists")
    void shouldDeleteTeacher_WhenNotExists() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/teacher/{id}", 99999)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("GET /teacher - Get empty list when no teachers")
    void shouldGetAllTeachers_WhenEmpty() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/teacher")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", equalTo(0))
                .body("", emptyArray());
    }

    @Test
    @DisplayName("Complete CRUD Flow")
    void shouldPerformCompleteCrudFlow() {
        // 1. CREATE
        String createJson = """
                {
                    "name": "CRUD Teacher",
                    "subject": "Integration Test",
                    "email": "crud@test.com"
                }
                """;

        Integer createdId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createJson)
                .when()
                .post("/teacher")
                .then()
                .statusCode(200)
                .extract()
                .path("id");

        // 2. READ
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/teacher/{id}", createdId)
                .then()
                .statusCode(200)
                .body("name", equalTo("CRUD Teacher"))
                .body("subject", equalTo("Integration Test"))
                .body("email", equalTo("crud@test.com"));

        // 3. UPDATE
        String updateJson = """
                {
                    "name": "Updated CRUD Teacher",
                    "subject": "Updated Integration",
                    "email": "updated.crud@test.com"
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateJson)
                .when()
                .put("/teacher/{id}", createdId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated CRUD Teacher"))
                .body("subject", equalTo("Updated Integration"))
                .body("email", equalTo("updated.crud@test.com"));

        // 4. DELETE
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/teacher/{id}", createdId)
                .then()
                .statusCode(200);

        // 5. VERIFY DELETED
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/teacher/{id}", createdId)
                .then()
                .statusCode(200)
                .body(equalTo(""));
    }

    @Test
    @DisplayName("Should handle multiple teachers with same name")
    void shouldHandleMultipleTeachersWithSameName() {
        teacherRepo.save(new Teacher(null, "John Doe", "Math", "john1@test.com"));
        teacherRepo.save(new Teacher(null, "John Doe", "Physics", "john2@test.com"));
        teacherRepo.save(new Teacher(null, "John Doe", "Chemistry", "john3@test.com"));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/teacher")
                .then()
                .statusCode(200)
                .body("size()", equalTo(3))
                .body("findAll { it.name == 'John Doe' }.size()", equalTo(3));
    }

    @Test
    @DisplayName("Should update only specific fields")
    void shouldUpdateOnlySpecificFields() {
        Teacher savedTeacher = teacherRepo.save(
                new Teacher(null, "Original Name", "Original Subject", "original@email.com")
        );

        String updateJson = """
                {
                    "name": "Updated Name Only",
                    "subject": "Original Subject",
                    "email": "updated@email.com"
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateJson)
                .when()
                .put("/teacher/{id}", savedTeacher.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Name Only"))
                .body("subject", equalTo("Original Subject"))
                .body("email", equalTo("updated@email.com"));
    }
}