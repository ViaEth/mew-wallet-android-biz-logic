package com.myetherwallet.mewwalletbl.core.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.myetherwallet.mewwalletbl.data.EncryptedMessage
import java.lang.reflect.Type

/**
 * Created by BArtWell on 24.07.2019.
 */

class EncryptedMessageSerializer : JsonDeserializer<EncryptedMessage> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EncryptedMessage {
        val jsonObject = json.asJsonObject
        return EncryptedMessage(
            getObjectValue(jsonObject, "ciphertext"),
            getObjectValue(jsonObject, "ephemPublicKey"),
            getObjectValue(jsonObject, "iv"),
            getObjectValue(jsonObject, "mac")
        )
    }

    private fun getObjectValue(data: JsonObject, key: String): ByteArray {
        val jsonArray = data.getAsJsonObject(key).getAsJsonArray("data")
        return JsonParser.fromJson(jsonArray, ByteArray::class.java)
    }
}
