package org.joml

@JvmInline
public value class Angled private constructor(public val radians: Double) {

    public val degrees: Double get() = Math.toDegrees(this.radians)

    public companion object {
        public val Double.radians: Angled get() = Angled(this)

        public val Double.degrees: Angled get() = Angled(Math.toRadians(this))
    }
}