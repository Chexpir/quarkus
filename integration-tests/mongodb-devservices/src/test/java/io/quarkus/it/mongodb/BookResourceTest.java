package io.quarkus.it.mongodb;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

import javax.json.bind.Jsonb;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.mongodb.health.MongoHealthCheck;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class BookResourceTest {
    private static Jsonb jsonb;

    @BeforeAll
    public static void giveMeAMapper() {
        jsonb = Utils.initialiseJsonb();
    }

    @AfterAll
    public static void releaseMapper() throws Exception {
        jsonb.close();
    }

    @Test
    public void testBlockingClient() {
        Utils.callTheEndpoint("/books");
    }

    @Test
    public void testReactiveClients() {
        Utils.callTheEndpoint("/reactive-books");
    }

    @Test
    public void health() throws Exception {
        // trigger (lazy) creation of the client, otherwise the health check would fail
        get("/books");
        RestAssured.when().get("/q/health/ready").then()
                .body("status", is("UP"),
                        "checks.data", containsInAnyOrder(hasKey(MongoHealthCheck.CLIENT_DEFAULT)),
                        "checks.data", containsInAnyOrder(hasKey(MongoHealthCheck.CLIENT_DEFAULT_REACTIVE)),
                        "checks.status", containsInAnyOrder("UP"),
                        "checks.name", containsInAnyOrder("MongoDB connection health check"));
    }

}
