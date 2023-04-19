package test.android.cns.implementation.provider.logger

import android.util.Log
import test.android.cns.foundation.provider.logger.Logger
import test.android.cns.foundation.provider.logger.LoggerFactory

internal object FinalLoggerFactory : LoggerFactory {
    override fun newLogger(tag: String): Logger {
        return AndroidLogger(tag)
    }
}

private class AndroidLogger(private val tag: String) : Logger {
    override fun debug(message: String) {
        Log.d(tag, message)
    }
}
