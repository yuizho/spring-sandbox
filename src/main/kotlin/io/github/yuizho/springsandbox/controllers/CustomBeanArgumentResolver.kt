package io.github.yuizho.springsandbox.controllers

import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

class CustomBeanArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(methodParameter: MethodParameter): Boolean {
        return methodParameter.hasParameterAnnotation(Custom::class.java)
    }

    override fun resolveArgument(
            methodParameter: MethodParameter,
            modelAndViewContainer: ModelAndViewContainer?,
            nativeWebRequest: NativeWebRequest,
            webDataBinderFactory: WebDataBinderFactory?): Any? {
        val request = nativeWebRequest.getNativeRequest(HttpServletRequest::class.java)
        val id = request?.getParameter("id")
        val name = request?.getParameter("name")

        return CustomBean(
                id = id ?: "",
                name = name ?: ""
        )
    }
}

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Custom

data class CustomBean(val id: String, val name: String)