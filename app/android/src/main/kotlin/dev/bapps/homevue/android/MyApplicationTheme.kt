package dev.bapps.homevue.android

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Build
import android.util.Log
import android.util.LogPrinter
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private class DiscoveryListener(
    private val stopDiscovery: (NsdManager.DiscoveryListener) -> Unit,
    private val resolveService: (NsdServiceInfo) -> Unit
) : NsdManager.DiscoveryListener {

    private val LogTag = "DiscoveryListener"

    // Called as soon as service discovery begins.
    override fun onDiscoveryStarted(regType: String) {
        Log.d(LogTag, "Service discovery started")
    }

    override fun onServiceFound(service: NsdServiceInfo) {
        // A service was found! Do something with it.
        Log.d(LogTag, "Service discovery success: $service")
        resolveService(service)

//        when {
//            service.serviceType != SERVICE_TYPE -> // Service type is the string containing the protocol and
//                // transport layer for this service.
//                Log.d(LogTag, "Unknown Service Type: ${service.serviceType}")
//
//            service.serviceName == mServiceName -> // The name of the service tells the user what they'd be
//                // connecting to. It could be "Bob's Chat App".
//                Log.d(LogTag, "Same machine: $mServiceName")
//
//            service.serviceName.contains("NsdChat") -> nsdManager.resolveService(service, resolveListener)
//        }
    }

    override fun onServiceLost(service: NsdServiceInfo) {
        // When the network service is no longer available.
        // Internal bookkeeping code goes here.
        Log.e(LogTag, "service lost: $service")
    }

    override fun onDiscoveryStopped(serviceType: String) {
        Log.i(LogTag, "Discovery stopped: $serviceType")
    }

    override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
        Log.e(LogTag, "Discovery failed: Error code:$errorCode")
        stopDiscovery(this)
    }

    override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
        Log.e(LogTag, "Discovery failed: Error code:$errorCode")
        stopDiscovery(this)
    }
}

private class HomeAssistantServiceDiscovery(
    private val nsdManager: NsdManager
) {
    fun start() {
        nsdManager.discoverServices(
            "_home-assistant._tcp",
            NsdManager.PROTOCOL_DNS_SD,
            DiscoveryListener(
                stopDiscovery = { }, // nsdManager::stopServiceDiscovery
                resolveService = { serviceInfo ->
                    nsdManager.resolveService(
                        serviceInfo,
                        object : NsdManager.ResolveListener {
                            override fun onResolveFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {

                            }

                            override fun onServiceResolved(serviceInfo: NsdServiceInfo?) {

                                /**
                                 * https://github.com/home-assistant/android/blob/master/app/src/main/java/io/homeassistant/companion/android/onboarding/discovery/HomeAssistantSearcher.kt#L43-L47
                                 */
                                Log.d("DiscoveryListener", "$serviceInfo")
                                if(serviceInfo == null) return
                                val log = buildString {
                                    appendLine("Service type: ${serviceInfo.serviceType}")
                                    appendLine("Service name: ${serviceInfo.serviceName}")
                                    appendLine("Host addresses:")
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                                        serviceInfo.hostAddresses.forEach {
                                            appendLine("\t - $it")
                                        }
                                    }else {
                                        appendLine("\t - ${serviceInfo.host}")
                                    }
                                    appendLine("Service port: ${serviceInfo.port}")


                                    val baseUrl: String? = serviceInfo.attributes?.get("base_url")?.let { String(it) }
                                    val version: String? = serviceInfo.attributes?.get("version")?.let { String(it) }

                                    appendLine("Base url : $baseUrl")
                                    appendLine("Version : $version")


                                }

                                val attributes = serviceInfo.attributes.keys
                                    .associateWith { key -> String(serviceInfo.attributes[key] ?: byteArrayOf()) }
                                    .map { (key, value) -> "$key: $value" }
                                    .joinToString(separator = "\n")

                                println("****************************")
                                println(log)
                                println()
                                println(attributes)
                                println("****************************")
                            }

                        }
                    )
                }
            ) as NsdManager.DiscoveryListener
        )
    }

    companion object {
        private var instance: HomeAssistantServiceDiscovery? = null

        fun start(context: Context) {
            synchronized(this) {
                if (instance != null)
                    return

                val nsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager
                instance = HomeAssistantServiceDiscovery(nsdManager)
                    .apply { start() }
            }
        }
    }
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        HomeAssistantServiceDiscovery.start(context)
    }


    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            secondary = Color(0xFF03DAC5),
            tertiary = Color(0xFF3700B3)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            tertiary = Color(0xFF3700B3)
        )
    }
    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
