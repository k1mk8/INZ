import spock.lang.Specification
import groovy.json.JsonOutput
import java.sql.ResultSet
import inz23.stolmel.dataTypeClasses.*
import inz23.stolmel.postgreSQL.*
import inz23.stolmel.product.*

class ProductManagerSpec extends Specification {

    def manager = new ProductManager()
    def postgreSQL = Mock(PostgreSQL)
    def resultSet = Mock(ResultSet)

    def "getProductId test"() {
        given:
        def productName = "Venus 3DL"

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> 1
        1 * resultSet.next() >> false

        postgreSQL.resultSet = resultSet

        expect:
        manager.getProductId(productName, postgreSQL) == 1
    }

    def "getNeededProfessions test"() {
        given:
        def productId = 1

        1 * resultSet.next() >> true
        1 * resultSet.getString("Profession") >> "krawiec"
        1 * resultSet.getInt("Time_needed") >> 5
        1 * resultSet.next() >> true
        1 * resultSet.getString("Profession") >> "stolarz"
        1 * resultSet.getInt("Time_needed") >> 8
        1 * resultSet.next() >> false

        postgreSQL.resultSet = resultSet

        def jsonArray = [
            ["Time_needed": 5, "Profession": "krawiec"],
            ["Time_needed": 8, "Profession": "stolarz"]
        ]
        def jsonString = JsonOutput.toJson(jsonArray)

        expect:
        manager.getNeededProfessions(productId, postgreSQL).toString().replaceAll("\\s", "") == jsonString
    }

    def "getProductDetails test"() {
        given:
        def productId = 1

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> productId
        1 * resultSet.getInt("price") >> 1200
        1 * resultSet.getString("type") >> "sofa"
        1 * resultSet.getString("name") >> "Venus 3DL"
        1 * resultSet.getString("description") >> "Lorem ipsum"
        1 * resultSet.getString("dimension") >> "300x240x60"
        1 * resultSet.getBytes("image") >> [97,98]

        postgreSQL.resultSet = resultSet

        def jsonObject = ["image": "aQ==", "price": 1200, "name": "Venus 3DL", "description": "Lorem ipsum", "id": productId, "type": "sofa", "dimension": "300x240x60"]
        def jsonString = JsonOutput.toJson(jsonObject)

        expect:
        manager.getProductDetails(productId, postgreSQL).toString() == jsonString
    }

    def "getProducts test"() {
        given:

        1 * resultSet.next() >> true
        1 * resultSet.getString("type") >> "sofa"
        1 * resultSet.getString("name") >> "Venus 3DL"
        1 * resultSet.getString("is_active") >> "true"
        1 * resultSet.next() >> true
        1 * resultSet.getString("type") >> "sofa"
        1 * resultSet.getString("name") >> "Oliwia III 3DL"
        1 * resultSet.getString("is_active") >> "true"
        1 * resultSet.next() >> false

        postgreSQL.resultSet = resultSet

        def jsonArray = [
            ["is_active": "true", "name": "Venus 3DL", "type": "sofa"],
            ["is_active": "true", "name": "Oliwia III 3DL", "type": "sofa"]
        ]
        def jsonString = JsonOutput.toJson(jsonArray)

        expect:
        manager.getProducts(postgreSQL).toString() == jsonString
    }

    def "getFreeProductId test"() {
        given:

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> 1

        postgreSQL.resultSet = resultSet

        expect:
        manager.getFreeProductId(postgreSQL) == 2
    }

    def "getFreeProductComponentsId test"() {
        given:

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> 1

        postgreSQL.resultSet = resultSet

        expect:
        manager.getFreeProductComponentsId(postgreSQL) == 2
    }
}
