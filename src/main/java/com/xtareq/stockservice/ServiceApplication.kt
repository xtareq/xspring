package com.xtareq.stockservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.ThreadLocalRandom


@SpringBootApplication
class StockServiceApplication

fun main(args: Array<String>){
    runApplication<StockServiceApplication>(*args)
}


@RestController
class RestController{

    @GetMapping(value = ["/stocks/{symbol}"],
            produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun pricesFor( @PathVariable symbol: String): Flux<StockPrice>{
        return Flux.interval(Duration.ofSeconds(1))
                .map { StockPrice(symbol, generateRandomPrice(), LocalDateTime.now()) }
                .share()
    }

    private fun generateRandomPrice(): Double {
        return ThreadLocalRandom.current().nextDouble(100.0)
    }
}

data class StockPrice (val symbol: String,
                       val price: Double,
                       val time: LocalDateTime)
