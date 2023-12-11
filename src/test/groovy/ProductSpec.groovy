import spock.lang.Specification
import inz23.stolmel.dataTypeClasses.*

class ProductSpec extends Specification {

    def "init test"() {
        given:
        Product product = new Product()

        when:
        def resultId = product.getId()
        def resultName = product.getName()
        def resultPrice = product.getPrice()

        then:
        resultId == -1
        resultName == ""
        resultPrice == ""
    }

    def "init2 test"() {
        given:
        Product product = new Product(512, "Kanapa", "1800")

        when:
        def resultId = product.getId()
        def resultName = product.getName()
        def resultPrice = product.getPrice()

        then:
        resultId == 512
        resultName == "Kanapa"
        resultPrice == "1800"
    }

    def "setters test"() {
        given:
        Product product = new Product()
        product.setId(15)
        product.setName("Fotel")
        product.setPrice("3800")

        when:
        def resultId = product.getId()
        def resultName = product.getName()
        def resultPrice = product.getPrice()

        then:
        resultId == 15
        resultName == "Fotel"
        resultPrice == "3800"
    }
}
