package com.myetherwallet.mewwalletbl.key

import android.content.Context
import com.myetherwallet.mewwalletbl.key.storage.EncryptStorage
import com.myetherwallet.mewwalletbl.key.util.AES
import com.myetherwallet.mewwalletbl.key.util.HKDF
import com.myetherwallet.mewwalletbl.key.util.KeystoreHelper
import com.myetherwallet.mewwalletbl.key.util.Utils

/**
 * Created by BArtWell on 05.08.2019.
 */

abstract class BaseEncryptHelper(protected val context: Context, private val keyType: KeyType, val encryptStorage: EncryptStorage) {

    protected val keystoreHelper = KeystoreHelper(context, keyType, encryptStorage)
    internal var accessKey: ByteArray = ByteArray(0)
        get() {
            if (field.isEmpty()) {
                field = retrieveAccessKey()
            }
            return field
        }

    protected open fun retrieveAccessKey(): ByteArray {
        val encryptedAccessKey = encryptStorage.getAccessKey(keyType)
        return keystoreHelper.decrypt(encryptedAccessKey)
    }

    fun encrypt(decryptedData: String): ByteArray {
        encryptStorage.save()
        return AES.encrypt(decryptedData.toByteArray(), retrieveMasterKey(accessKey))
    }

    fun decrypt(encryptedData: ByteArray) = String(AES.decrypt(encryptedData, retrieveMasterKey(accessKey)))

    internal fun retrieveMasterKey(accessKey: ByteArray): ByteArray {
        val encryptedMasterKey = encryptStorage.getMasterKey(keyType)
        return AES.decrypt(encryptedMasterKey, accessKey)
    }

    internal open fun generateAccessKey(salt: ByteArray): ByteArray {
        accessKey = HKDF.calculate(Utils.getRandomBytes(64), salt)
        return accessKey
    }

    fun destroy() {
        accessKey = ByteArray(0)
    }
}
