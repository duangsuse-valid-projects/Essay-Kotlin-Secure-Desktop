package org.duangsuse.securedesktop

import kotlin.native.concurrent.*

class App: Secured() {
    fun start() {
        enterProtection()
        newworker().executeSafely(identity(null)) {
            protectNewThread()
            MessageBoxA(null, "Hello from protected thread", (MB_OK or MB_ICONINFORMATION))
        }.consume {
            leaveProtection()
            close()
        }
    }

    // where
    private fun newworker() = Worker.start()
    private inline fun <T, R> Worker
      .executeSafely(noinline producer: () -> T, noinline job: (T) -> R ): Future<R> = this.execute<T,R>(TransferMode.SAFE, producer, job)
    private inline fun <A> identity(x: A): () -> A = {x}
}