package com.oracle.truffle.sl.builtins

import com.oracle.truffle.api.dsl.*
import com.oracle.truffle.api.nodes.*
import software.amazon.ion.*

@NodeInfo(shortName = "parseIon")
abstract class SLParseIonBuiltin : SLBuiltinNode() {

    @Specialization
    fun parseIon(ionText: String): Any {
        // TODO:  don't use DOM for this.
        val value = context.ion.singleValue(ionText)
        return when (value.type) {
            IonType.INT -> {
                val intValue = value as IonInt
                when (intValue.integerSize) {
                    IntegerSize.INT         -> intValue.intValue()
                    IntegerSize.LONG        -> intValue.longValue()
                    IntegerSize.BIG_INTEGER -> intValue.bigIntegerValue()
                    else -> TODO("Not implemented: " + value.getType())

                }
            }
            else -> throw IllegalStateException("Not implemented: " + value.type)
        }
    }
}
