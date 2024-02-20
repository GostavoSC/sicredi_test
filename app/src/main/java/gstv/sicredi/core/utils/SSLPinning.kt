package gstv.sicredi.core.utils

import android.os.Build
import okhttp3.OkHttpClient
import java.io.BufferedInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class SSLPinning {

    private val certificateFactory = CertificateFactory.getInstance(X509_CERTIFICATE_TYPE)
    companion object {
        private const val PING_INTERVAL = 5L
        private const val SSL_PROTOCOL = "TLSv1.2"

        private const val CERTIFICATE_ALIAS_PREFIX = "ca"
        private const val X509_CERTIFICATE_TYPE = "X.509"
        private val CERTIFICATE_PATHS = listOf("/res/raw/backend_certificate.cer")
    }

    fun builderCallback(builder: OkHttpClient.Builder) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            setSSLSocketFactory(builder)
        }

        builder.pingInterval(PING_INTERVAL, TimeUnit.MINUTES)
    }

    private fun setSSLSocketFactory(builder: OkHttpClient.Builder) {
        val keyStore = getKeyStoreInstance()

        for (index in CERTIFICATE_PATHS.indices) {
            val certificate = getCertificate(CERTIFICATE_PATHS[index])
            keyStore.setCertificateEntry("${CERTIFICATE_ALIAS_PREFIX}_$index", certificate)
        }

        val trustManagerFactory = createTrustManagerFactory(keyStore)
        val sslContext = createSSLContext(trustManagerFactory)
        builder.sslSocketFactory(sslContext.socketFactory, trustManagerFactory.trustManagers[0] as X509TrustManager)
    }

    private fun getCertificate(path: String): X509Certificate {
        val resourceStream = this::class.java.getResourceAsStream(path)
        val caInput: InputStream = BufferedInputStream(resourceStream)
        return caInput.use {
            certificateFactory.generateCertificate(it) as X509Certificate
        }
    }

    private fun getKeyStoreInstance(): KeyStore {
        val keyStoreType = KeyStore.getDefaultType()
        return KeyStore.getInstance(keyStoreType).apply {
            load(null, null)
        }
    }

    private fun createTrustManagerFactory(keyStore: KeyStore): TrustManagerFactory {
        val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
        return TrustManagerFactory.getInstance(tmfAlgorithm).apply {
            init(keyStore)
        }
    }

    private fun createSSLContext(trustManagerFactory: TrustManagerFactory): SSLContext {
        return SSLContext.getInstance(SSL_PROTOCOL).apply {
            init(null, trustManagerFactory.trustManagers, null)
        }
    }
}