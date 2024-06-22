/*
 * The MIT License
 *
 * Copyright (c) 2022-2024 JOML
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.joml.jmh;

import org.openjdk.jmh.annotations.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.joml.*;

import static java.nio.ByteBuffer.allocateDirect;
import static java.nio.ByteOrder.nativeOrder;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;
import static org.openjdk.jmh.annotations.Scope.Benchmark;

@State(Benchmark)
@OutputTimeUnit(NANOSECONDS)
@Warmup(iterations = 10, time = 1000, timeUnit = MILLISECONDS)
@Measurement(iterations = 10, time = 1000, timeUnit = MILLISECONDS)
@BenchmarkMode(AverageTime)
@Fork(value = 1, jvmArgsAppend = {
        "-XX:UseAVX=3",
        "--enable-preview",
        "--add-modules", "jdk.incubator.vector",
        "-Djoml.useMathFma=true",
        "-Djoml.fastMath=true",
        "-XX:+UnlockExperimentalVMOptions",
        "-XX:+EnableJVMCI",
        "--add-exports", "jdk.internal.vm.ci/jdk.vm.ci.code=ALL-UNNAMED",
        "--add-exports", "jdk.internal.vm.ci/jdk.vm.ci.code.site=ALL-UNNAMED",
        "--add-exports", "jdk.internal.vm.ci/jdk.vm.ci.hotspot=ALL-UNNAMED",
        "--add-exports", "jdk.internal.vm.ci/jdk.vm.ci.meta=ALL-UNNAMED",
        "--add-exports", "jdk.internal.vm.ci/jdk.vm.ci.runtime=ALL-UNNAMED",
        "--add-exports", "jdk.internal.vm.ci/jdk.vm.ci.amd64=ALL-UNNAMED",
        "-Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=0",
        "--enable-native-access=ALL-UNNAMED"})
public class Bench {
    private final org.joml.Matrix4f m4a = new org.joml.Matrix4f();
    private final org.joml.Matrix4f m4b = new org.joml.Matrix4f();
    private final org.joml.Matrix4f m4c = new org.joml.Matrix4f();
    private final Matrix4fvBB m4vbb = new Matrix4fvBB();
    private final Matrix4fvArr m4varr = new Matrix4fvArr();
    private final ByteBuffer bb = allocateDirect(16<<2).order(nativeOrder());
    private final FloatBuffer fb = bb.asFloatBuffer();
    private final Quaternionf qa = new Quaternionf();
    private final Quaternionf qb = new Quaternionf();
    private final Quaternionf qc = new Quaternionf();

    @Benchmark
    public void mul_Quaternionf() {
        qa.mul(qb, qc);
    }

//
//    @Benchmark
//    public void mul_Matrix4f_Jvmci_AVX2() {
//        WithJvmci.mulAvx2(m4a, m4b, m4c);
//    }
//
//    @Benchmark
//    public void invert_Matrix4f_Jvmci() {
//        WithJvmci.invert(m4a, m4b);
//    }
//
//    @Benchmark
//    public void transpose_Matrix4f_Jvmci() {
//        WithJvmci.transpose(m4a, m4b);
//    }
//
//    @Benchmark
//    public void noop_Jvmci_2args() {
//        WithJvmci.noop_2_args(m4a, 0L);
//    }
//
//    @Benchmark
//    public void noop_Panama_2args() throws Throwable {
//        Matrix4fn.noop2ForPanama.invokeExact(1L, 2L);
//    }
//
//    @Benchmark
//    public void transpose_Matrix4f() {
//        m4a.transpose(m4b);
//    }
//
//    @Benchmark
//    public void transpose_Matrix4fvArr_128() {
//        m4varr.transpose(m4varr);
//    }
//
//    @Benchmark
//    public void invert_Matrix4f() {
//        m4a.invert(m4b);
//    }
//
//    @Benchmark
//    public void invert_Matrix4fvArr_128() {
//        m4varr.invert128(m4varr);
//    }
//
//    @Benchmark
//    public void set_Matrix4f_Jvmci_AVX2() {
//        WithJvmci.setAvx2(m4a, m4a);
//    }
//
//    @Benchmark
//    public void set_Matrix4f() {
//        m4a.set(m4b);
//    }
//
//    @Benchmark
//    public void store_Matrix4f_FloatBuffer_put() {
//        m4a.storePutFB(fb);
//    }
//
//    @Benchmark
//    public void store_Matrix4f_Jvmci_AVX2() {
//        WithJvmci.storeAvx2(m4a, bb_addr);
//    }
//
//    @Benchmark
//    public void store_Matrix4f_ByteBuffer_putFloat() {
//        m4a.storePutBB(bb);
//    }
//
//    @Benchmark
//    public void store_Matrix4fvArr_FloatBuffer_put() {
//        m4varr.storePut(fb);
//    }
//
//    @Benchmark
//    public void store_Matrix4fvArr_Unsafe() {
//        m4varr.storeU(bb);
//    }
//
//    @Benchmark
//    public void store_Matrix4fvArr_256() {
//        m4varr.storeV256(bb);
//    }
//
//    @Benchmark
//    public void store_Matrix4fvArr_512() {
//        m4varr.storeV512(bb);
//    }
//
//    @Benchmark
//    public void store_Matrix4f_Unsafe() {
//        m4a.storeU(bb);
//    }
//
//    @Benchmark
//    public Object mul_Matrix4f() {
//        return m4a.mul(m4b);
//    }
//
//    @Benchmark
//    public Object mul_Matrix4f_FMA() {
//        return m4a.mulFma(m4b);
//    }
//
//    @Benchmark
//    public Object mulAffine_Matrix4f_FMA() {
//        return m4a.mulAffineFma(m4b);
//    }
//
//    @Benchmark
//    public Object mul_Matrix4fvArr_256() {
//        return m4varr.mul256(m4varr);
//    }
//
//    @Benchmark
//    public Object mul_Matrix4fvArr_128_Unrolled() {
//        return m4varr.mul128Unrolled(m4varr);
//    }
//
//    @Benchmark
//    public Object mul_Matrix4fvArr_128_Loop() {
//        return m4varr.mul128Loop(m4varr);
//    }
//
//    @Benchmark
//    public Object mul_Matrix4fvBB_256() {
//        return m4vbb.mul256(m4vbb);
//    }
//
//    @Benchmark
//    public Object mul_Matrix4fvBB_128_Unrolled() {
//        return m4vbb.mul128Unrolled(m4vbb);
//    }
//
//    @Benchmark
//    public Object mul_Matrix4fvBB_128_Loop() {
//        return m4vbb.mul128Loop(m4vbb);
//    }
}
