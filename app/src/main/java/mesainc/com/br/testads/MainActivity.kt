package mesainc.com.br.testads

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Example of interstitial ads usage.
 *
 * -We need to LOAD the ad first, to then be able to show it...
 * -Note that you can only set the ad unit once per object... So thats why we are always instantiating a new object.
 *
 * Also, we are using observable delegate to enable/disable the show ad button.
 */
class MainActivity : AppCompatActivity() {
    var mShowButton: Boolean by Delegates.observable(false) { kProperty: KProperty<*>, old: Boolean, new: Boolean ->

        bt_show?.isEnabled = new

        if (new) {
            bt_show?.text = "Show Interstitial"
        } else {

            if (mInterstitial!!.isLoaded) {
                bt_show?.text = "Interstitial not ready"
            } else {
                bt_show?.text = "Loading intertitial"
            }
        }
    }


    var mInterstitial: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_load.setOnClickListener {

            mInterstitial = InterstitialAd(this)

            mShowButton = false

            mInterstitial!!.adUnitId = resources.getString(R.string.test_ad_unit_id_interstitial)
            val ar = AdRequest.Builder().build()
            mInterstitial!!.loadAd(ar)

            mInterstitial!!.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    mShowButton = true
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    mShowButton = false
                }
            }
        }

        bt_show.setOnClickListener {
            if (mInterstitial!!.isLoaded) {
                mInterstitial!!.show()
            }

            mShowButton = false
        }

    }
}
