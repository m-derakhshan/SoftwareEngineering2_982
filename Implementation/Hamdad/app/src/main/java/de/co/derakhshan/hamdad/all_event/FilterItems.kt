package de.co.derakhshan.hamdad.all_event

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class FilterItems(
    val name: String? ,
    val justOpen: Boolean?,
    val justParticipated: Boolean?
) : Parcelable