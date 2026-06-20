package org.joml

@JvmInline
public value class Angle private constructor(public val radians: Float) {

    public val degrees: Float get() = Math.toDegrees(this.radians.toDouble()).toFloat()

    public companion object {
        public val Float.radians: Angle get() = Angle(this)

        public val Float.degrees: Angle get() = Angle(Math.toRadians(this.toDouble()).toFloat())
    }
}