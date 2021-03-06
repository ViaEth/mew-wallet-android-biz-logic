package com.myetherwallet.mewwalletbl.preference

import android.content.Context

/**
 * Created by BArtWell on 17.07.2019.
 */

class Preferences private constructor(context: Context) {

    private val main = MainPreferences(context)
    private val wallet = WalletPreferences(context)
    private val persistent = PersistentPreferences(context)

    companion object {

        private lateinit var instance: Preferences
        val main: MainPreferences by lazy { instance.main }
        val wallet: WalletPreferences by lazy { instance.wallet }
        val persistent: PersistentPreferences by lazy { instance.persistent }

        fun init(context: Context) {
            instance = Preferences(context)
        }
    }
}