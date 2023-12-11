import spock.lang.Specification
import inz23.stolmel.sha.*

class SHA512Spec extends Specification {

    def "hash test"() {
        given:
        SHA512 hash = new SHA512()

        when:
        def result = hash.hash("ALaMaKotA")

        then:
        result == "f33ea60eb9e3841b99bd579b883f5cc01758b0ac051e34d9416fcedca8fd10c8286a72e07fcefdc45b033f30678c1934914d650c69db08302a9921416edd5e2f"
    }

    def "hash test"() {
        given:
        SHA512 hash = new SHA512()

        when:
        def result = hash.hash("Karol&Szymon")

        then:
        result == "fd1fb97075ceea40627b7b7ec7618726a49ad72f58779c032a4275c7fbcb85fde5833e2a1d81a82d369986fcc07a5a43a5574166725079b5a127de2611af4bd0"
    }
}
