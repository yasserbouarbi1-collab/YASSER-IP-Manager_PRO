package com.yasser.ipmanager

import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var txtPublicIp: TextView
    private lateinit var txtLocalIp: TextView
    private lateinit var btnVpn: Button
    private lateinit var btnRefresh: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtPublicIp = findViewById(R.id.txtPublicIp)
        txtLocalIp = findViewById(R.id.txtLocalIp)
        btnVpn = findViewById(R.id.btnVpn)
        btnRefresh = findViewById(R.id.btnRefresh)

        btnVpn.setOnClickListener {
            try {
                startActivity(Intent("android.net.vpn.SETTINGS"))
            } catch (e: Exception) {
                startActivity(Intent(Settings.ACTION_VPN_SETTINGS))
            }
        }

        btnRefresh.setOnClickListener {
            checkIps()
        }

        checkIps()
    }

    private fun checkIps() {
        txtPublicIp.text = "جاري الفحص..."
        try {
            val wifi = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
            val ipInt = wifi.connectionInfo.ipAddress
            val localIp = "${ipInt and 0xff}.${ipInt shr 8 and 0xff}.${ipInt shr 16 and 0xff}.${ipInt shr 24 and 0xff}"
            txtLocalIp.text = "IP المحلي: $localIp"
        } catch (e: Exception) {
            txtLocalIp.text = "IP المحلي: غير متوفر"
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val ip = URL("https://api.ipify.org").readText()
                withContext(Dispatchers.Main) {
                    txtPublicIp.text = "IP العام: $ip"
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    txtPublicIp.text = "IP العام: فشل الاتصال"
                }
            }
        }
    }
}
