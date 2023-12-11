import spock.lang.Specification
import java.sql.ResultSet
import inz23.stolmel.dataTypeClasses.*
import inz23.stolmel.postgreSQL.*
import inz23.stolmel.user.*

class UserSpec extends Specification {

    def user = new User()
    def postgreSQL = Mock(PostgreSQL)
    def resultSet = Mock(ResultSet)

    def "getClientByEmail test"() {
        given:
        def id = 1
        def name = "lukasz"
        def surname = "konieczny"
        def number = "+48123456789"
        def email = "lukaszkonieczny@gmail.com"
        def hash = "b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86"
        def isAdmin = false

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> id
        1 * resultSet.getString("name") >> name
        1 * resultSet.getString("surname") >> surname
        1 * resultSet.getString("number") >> number
        1 * resultSet.getString("email") >> email
        1 * resultSet.getString("hash") >> hash
        1 * resultSet.getBoolean("is_admin") >> isAdmin
        1 * resultSet.next() >> false

        postgreSQL.resultSet = resultSet

        expect:
        user.getClientByEmail("email", postgreSQL) == new Client(id, name, surname, number, email, hash, isAdmin)
    }

    def "login test"() {
        given:
        def id = 1
        def name = "lukasz"
        def surname = "konieczny"
        def number = "+48123456789"
        def email = "lukaszkonieczny@gmail.com"
        def hash = "b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86"
        def isAdmin = false

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> id
        1 * resultSet.getString("name") >> name
        1 * resultSet.getString("surname") >> surname
        1 * resultSet.getString("number") >> number
        1 * resultSet.getString("email") >> email
        1 * resultSet.getString("hash") >> hash
        1 * resultSet.getBoolean("is_admin") >> isAdmin
        1 * resultSet.next() >> false

        postgreSQL.resultSet = resultSet

        expect:
        user.login(email, "password", postgreSQL) == 1
    }

    def "login test2"() {
        given:
        def id = 1
        def name = "lukasz"
        def surname = "konieczny"
        def number = "+48123456789"
        def email = "lukaszkonieczny@gmail.com"
        def hash = "b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86"
        def isAdmin = true

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> id
        1 * resultSet.getString("name") >> name
        1 * resultSet.getString("surname") >> surname
        1 * resultSet.getString("number") >> number
        1 * resultSet.getString("email") >> email
        1 * resultSet.getString("hash") >> hash
        1 * resultSet.getBoolean("is_admin") >> isAdmin
        1 * resultSet.next() >> false

        postgreSQL.resultSet = resultSet

        expect:
        user.login(email, "password", postgreSQL) == 2
    }

    def "login test 3"() {
        given:
        def email = "lukaszkonieczny@gmail.com"
        1 * resultSet.next() >> false
        postgreSQL.resultSet = resultSet

        expect:
        user.login(email, "password", postgreSQL) == 0
    }

    def "getFreeClientId test"() {
        given:
        def id = 18

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> id
        1 * resultSet.next() >> false

        postgreSQL.resultSet = resultSet

        expect:
        user.getFreeClientId(postgreSQL) == id + 1
    }

    def "register test"() {
        given:
        def id = 1
        def name = "lukasz"
        def surname = "konieczny"
        def number = "+48123456789"
        def email = "lukaszkonieczny@gmail.com"
        def hash = "b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86"
        def isAdmin = true
        def Client client = new Client(id, name, surname, number, email, hash, isAdmin)

        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> id
        1 * resultSet.getString("name") >> name
        1 * resultSet.getString("surname") >> surname
        1 * resultSet.getString("number") >> number
        1 * resultSet.getString("email") >> email
        1 * resultSet.getString("hash") >> hash
        1 * resultSet.getBoolean("is_admin") >> isAdmin
        1 * resultSet.next() >> false
        
        postgreSQL.resultSet = resultSet

        expect:
        user.register(client, postgreSQL) == false
    }

    def "register test 2"() {
        given:
        def id = 1
        def name = "lukasz"
        def surname = "konieczny"
        def number = "+48123456789"
        def email = "lukaszkonieczny@gmail.com"
        def hash = "b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86"
        def isAdmin = true
        def Client client = new Client(id, name, surname, number, email, hash, isAdmin)

        1 * resultSet.next() >> false
        1 * resultSet.next() >> true
        1 * resultSet.getInt("id") >> id
        1 * resultSet.getString("name") >> name
        1 * resultSet.getString("surname") >> surname
        1 * resultSet.getString("number") >> number
        1 * resultSet.getString("email") >> email
        1 * resultSet.getString("hash") >> hash
        1 * resultSet.getBoolean("is_admin") >> isAdmin
        1 * resultSet.next() >> false
        
        postgreSQL.resultSet = resultSet

        expect:
        user.register(client, postgreSQL) == true
    }
}
