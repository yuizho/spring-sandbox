package io.github.yuizho.springsandbox

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class MvcConfig : WebMvcConfigurer {
    override fun configureAsyncSupport(configurer: AsyncSupportConfigurer) {
        configurer.setTaskExecutor(mvcAsyncExecutor())
    }

    @Bean
    fun mvcAsyncExecutor(): AsyncTaskExecutor {
        // Bean登録しておけば適切なタイミングで初期化やファイナライズが走るとのこと
        return ThreadPoolTaskExecutor().also {
            it.corePoolSize = 20
            it.maxPoolSize = 40
            it.setQueueCapacity(100)
        }
    }
}