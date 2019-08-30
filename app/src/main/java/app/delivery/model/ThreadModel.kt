package app.delivery.model

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * contains thread for performing different operations on different threads
 */
class ThreadModel {
    val dbThread: ExecutorService = Executors.newSingleThreadExecutor()
    val networkThread: ExecutorService = Executors.newFixedThreadPool(3)
}