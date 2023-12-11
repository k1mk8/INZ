import spock.lang.Specification
import inz23.stolmel.dataTypeClasses.*

class ClientSpec extends Specification {

    def "init test"() {
        given:
        Client client = new Client()

        when:
        def resultId = client.getId()
        def resultName = client.getName()
        def resultSurname = client.getSurname()
        def resultNumber = client.getNumber()
        def resultEmail = client.getEmail()
        def resultHash = client.getHash()
        def resultIsAdmin = client.isAdmin()

        then:
        resultId == -1
        resultName == ""
        resultSurname == ""
        resultNumber == ""
        resultEmail == ""
        resultHash == ""
        resultIsAdmin == false
    }

    def "init2 test"() {
        given:
        Client client = new Client(18, "Jan", "Kowalski", "+48 789 456 123", "jkowal@gmail.com", "somehash", true)

        when:
        def resultId = client.getId()
        def resultName = client.getName()
        def resultSurname = client.getSurname()
        def resultNumber = client.getNumber()
        def resultEmail = client.getEmail()
        def resultHash = client.getHash()
        def resultIsAdmin = client.isAdmin()

        then:
        resultId == 18
        resultName == "Jan"
        resultSurname == "Kowalski"
        resultNumber == "+48 789 456 123"
        resultEmail == "jkowal@gmail.com"
        resultHash == "somehash"
        resultIsAdmin == true
    }

    def "setters test"() {
        given:
        Client client = new Client()
        client.setId(11)
        client.setName("Magda")
        client.setSurname("Gezler")
        client.setNumber("+48 222 345 667")
        client.setEmail("magda@gezler.com")
        client.setHash("newHash")
        client.isAdmin(true)

        when:
        def resultId = client.getId()
        def resultName = client.getName()
        def resultSurname = client.getSurname()
        def resultNumber = client.getNumber()
        def resultEmail = client.getEmail()
        def resultHash = client.getHash()
        def resultIsAdmin = client.isAdmin()

        then:
        resultId == 11
        resultName == "Magda"
        resultSurname == "Gezler"
        resultNumber == "+48 222 345 667"
        resultEmail == "magda@gezler.com"
        resultHash == "newHash"
        resultIsAdmin == true
    }
}
