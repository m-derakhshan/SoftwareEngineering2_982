package de.co.derakhshan.hamdad.event

interface MessageTransformer {

    fun transform(str: String, where: String = "")
}