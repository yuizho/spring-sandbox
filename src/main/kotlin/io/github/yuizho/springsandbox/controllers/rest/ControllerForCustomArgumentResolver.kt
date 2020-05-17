package io.github.yuizho.springsandbox.controllers.rest

import com.sun.istack.NotNull
import io.github.yuizho.springsandbox.controllers.Custom
import io.github.yuizho.springsandbox.controllers.CustomBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api/argument-resolver")
class ControllerForCustomArgumentResolver {

    /**
     * @Custom handles the request having id and name
     *
     * @param customBean
     * @return
     */
    @GetMapping("")
    fun get(@Custom customBean: CustomBean): CustomBean {
        println("""parameter is : $customBean""")
        return customBean
    }
}