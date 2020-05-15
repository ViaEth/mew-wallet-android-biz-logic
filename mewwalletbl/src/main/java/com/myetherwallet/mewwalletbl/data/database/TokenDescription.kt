package com.myetherwallet.mewwalletbl.data.database

import android.os.Parcelable
import com.myetherwallet.mewwalletkit.bip.bip44.Address
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TokenDescription(
    val id: Long,
    val decimals: Int,
    val name: String,
    val symbol: String,
    val logo: String?,
    val contract: Address
) : Parcelable {
    constructor(entity: EntityTokenDescription) : this(
        entity.id,
        entity.decimals,
        entity.name,
        entity.symbol,
        entity.logo,
        entity.address
    )
}