import spock.lang.Specification
import groovy.json.JsonOutput
import org.json.JSONObject
import java.sql.ResultSet
import inz23.stolmel.dataTypeClasses.*
import inz23.stolmel.postgreSQL.*
import inz23.stolmel.schedule.*

class SchedulesSpec extends Specification {

    def scheduler = new Schedules()
    def postgreSQL = Mock(PostgreSQL)
    def resultSet = Mock(ResultSet)

    def "checkMaterialsAvailability test"() {
        given:
        def productId = 1

        1 * resultSet.next() >> true

        postgreSQL.resultSet = resultSet

        expect:
        scheduler.checkMaterialsAvailability(productId, postgreSQL) == true
    }

    def "checkMaterialsAvailability test 2"() {
        given:
        def productId = 1

        1 * resultSet.next() >> false

        postgreSQL.resultSet = resultSet

        expect:
        scheduler.checkMaterialsAvailability(productId, postgreSQL) == false
    }

    def "getLastHourOfTasks test"() {
        given:
        def productId = 1
        def jsonArray = [
            ["Time_needed": 3, "Profession": "krawiec"],
            ["Time_needed": 2, "Profession": "stolarz"],
            ["Time_needed": 2, "Profession": "kierowca"]
        ]
        def jsonList = jsonArray.collect { new JSONObject(it) }


        1 * resultSet.next() >> true
        1 * resultSet.getString("datetime") >> "2023-08-11 16:00"
        1 * resultSet.getInt("id") >> 2
        1 * resultSet.next() >> true
        1 * resultSet.getString("datetime") >> "2023-08-11 17:00"
        1 * resultSet.getInt("id") >> 2
        1 * resultSet.next() >> true
        1 * resultSet.getString("datetime") >> "2023-08-11 18:00"
        1 * resultSet.getInt("id") >> 2
        1 * resultSet.next() >> false

        1 * resultSet.next() >> true
        1 * resultSet.getString("datetime") >> "2023-08-11 16:00"
        1 * resultSet.getInt("id") >> 5
        1 * resultSet.next() >> true
        1 * resultSet.getString("datetime") >> "2023-08-11 17:00"
        1 * resultSet.getInt("id") >> 5
        1 * resultSet.next() >> false

        1 * resultSet.next() >> true
        1 * resultSet.getString("date") >> "2023-08-12"
        1 * resultSet.getInt("id") >> 7

        1 * resultSet.next() >> true
        1 * resultSet.getString("datetime") >> "2023-08-12 08:00"
        1 * resultSet.getInt("id") >> 7
        1 * resultSet.next() >> true
        1 * resultSet.getString("datetime") >> "2023-08-12 09:00"
        1 * resultSet.getInt("id") >> 7
        1 * resultSet.next() >> false

        postgreSQL.resultSet = resultSet

        def outputArray = [
            ["employeeId" : 2, "timestamp" : "2023-08-11 16:00"],
            ["employeeId" : 2, "timestamp" : "2023-08-11 17:00"],
            ["employeeId" : 2, "timestamp" : "2023-08-11 18:00"],
            ["employeeId" : 5, "timestamp" : "2023-08-11 16:00"],
            ["employeeId" : 5, "timestamp" : "2023-08-11 17:00"],
            ["employeeId" : 7, "timestamp" : "2023-08-12 08:00"],
            ["employeeId" : 7, "timestamp" : "2023-08-12 09:00"]
        ]
        def output = outputArray.collect { new JSONObject(it) }

        expect:
        scheduler.getLastHourOfTasks(jsonList, postgreSQL).toString() == output.toString()
    }
}
