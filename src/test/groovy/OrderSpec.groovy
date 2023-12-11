import spock.lang.Specification
import groovy.json.JsonOutput
import java.sql.ResultSet
import inz23.stolmel.dataTypeClasses.*
import inz23.stolmel.postgreSQL.*
import inz23.stolmel.order.*

class OrderSpec extends Specification {

    def order = new Order()
    def postgreSQL = Mock(PostgreSQL)
    def resultSet = Mock(ResultSet)

    def "getOrdersOfClient test"() {
        given:
        def clientId = 1

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> 1
        1 * resultSet.getString("state") >> "Aktywny Koszyk"
        1 * resultSet.getString("timestamp") >> "2023-09-01 23:59"
        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> 2
        1 * resultSet.getString("state") >> "W trakcie realizacji"
        1 * resultSet.getString("timestamp") >> "2023-08-11 16:10"
        1 * resultSet.next() >> false

        postgreSQL.resultSet = resultSet

        def jsonArray = [
            ["id": 1, "state": "Aktywny Koszyk", "timestamp": "2023-09-01 23:59"],
            ["id": 2, "state": "W trakcie realizacji", "timestamp": "2023-08-11 16:10"]
        ]
        def jsonString = JsonOutput.toJson(jsonArray)

        expect:
        order.getOrdersOfClient(clientId, postgreSQL).toString() == jsonString
    }

    def "createOrderForClient test"() {
        given:
        def clientId = 5
        def orderId = 12

        def jsonObject = ["id": orderId, "state": "Aktywny Koszyk", "client_id": clientId, "timestamp":order.strDate]
        def jsonString = JsonOutput.toJson(jsonObject)

        expect:
        order.createOrderForClient(clientId, orderId, postgreSQL).toString() == jsonString
    }

    def "getFreeOrderId test"() {
        given:
        def id = 12

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> id

        postgreSQL.resultSet = resultSet

        expect:
        order.getFreeOrderId(postgreSQL) == id + 1
    }

    def "getProductIdsFromOrder test"() {
        given:
        def orderId = 12

        1 * resultSet.next() >> true
        1 * resultSet.getInt("product_id") >> 1
        1 * resultSet.getInt("amount") >> 1
        1 * resultSet.next() >> true
        1 * resultSet.getInt("product_id") >> 5
        1 * resultSet.getInt("amount") >> 3
        1 * resultSet.next() >> false

        postgreSQL.resultSet = resultSet
        
        def jsonArray = [
            ["amount": 1, "product_id": 1],
            ["amount": 3, "product_id": 5]
        ]
        def jsonString = JsonOutput.toJson(jsonArray)

        expect:
        order.getProductIdsFromOrder(orderId, postgreSQL).toString() == jsonString
    }

    def "getBasketOfClient test"() {
        given:
        def clientId = 12

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> 1
        1 * resultSet.getString("state") >> "Aktywny Koszyk"
        1 * resultSet.getString("timestamp") >> "2023-09-01 23:59"

        postgreSQL.resultSet = resultSet
        
        def jsonObject = ["id": 1, "state": "Aktywny Koszyk", "timestamp": "2023-09-01 23:59"]
        def jsonString = JsonOutput.toJson(jsonObject)

        expect:
        order.getBasketOfClient(clientId, postgreSQL).toString() == jsonString
    }

    def "getProductFromId test"() {
        given:
        def productId = 1

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> 1
        1 * resultSet.getString("name") >> "mebel"
        1 * resultSet.getString("price") >> "1233"

        postgreSQL.resultSet = resultSet
        
        def product = new Product(1, "mebel", "1233")

        expect:
        order.getProductFromId(productId, postgreSQL) == product
    }

    def "basketFinish test"() {
        given:
        def orderId = 1

        1 * resultSet.next() >> true
        1 * resultSet.getString("datetime") >> "2023-09-01 23:59"

        postgreSQL.resultSet = resultSet

        expect:
        order.basketFinish(orderId, postgreSQL) == "2023-09-01 23:59"
    }
}
