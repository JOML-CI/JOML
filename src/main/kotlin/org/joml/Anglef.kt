package org.joml

@JvmInline
public value class Anglef private constructor(public val radians: Float) {

    public val degrees: Float get() = Math.toDegrees(this.radians.toDouble()).toFloat()

    public companion object {
        public val Float.radians: Anglef get() = Anglef(this)

        public val Float.degrees: Anglef get() = Anglef(Math.toRadians(this.toDouble()).toFloat())
    }
}