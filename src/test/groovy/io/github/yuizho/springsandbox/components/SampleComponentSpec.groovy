package io.github.yuizho.springsandbox.components

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest;
import spock.lang.Specification

@SpringBootTest
class SampleComponentSpec extends Specification {
    @Autowired
    SampleComponent sampleComponent

    def 'isWorking returns true'() {
        when:
        def actual = sampleComponent.isWorking();

        then:
        actual == true
    }
}