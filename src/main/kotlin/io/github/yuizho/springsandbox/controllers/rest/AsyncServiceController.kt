package io.github.yuizho.springsandbox.controllers.rest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.scheduling.annotation.EnableAsync



@RestController
@RequestMapping("api/async")
class AsyncServiceController(
        val asyncService: AsyncService
) {
    @GetMapping("/do")
    fun doTask(): String {
        println("------- the request started on ${Thread.currentThread().id}")
        asyncService.doAsyncTask()
        println("------- the request was completed on ${Thread.currentThread().id}")
        return "ok"
    }
}

@Service
class AsyncService {
    @Async
    fun doAsyncTask() {
        println("------- async task started on ${Thread.currentThread().id}")
        Thread.sleep(5000)
        println("------- async task was completed on ${Thread.currentThread().id}")
    }
}


@Configuration
@EnableAsync
class AsyncConfig {
    /**
     * defaultでtaskExecutorという名前のBeanが使われるとのこと
     */
    @Bean
    fun taskExecutor(): AsyncTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 1
        executor.maxPoolSize = 1
        executor.setQueueCapacity(10)
        return executor
    }
}