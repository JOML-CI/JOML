/*
 * (C) Copyright 2016 JOML

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package org.joml;

/**
 * Will be thrown whenever a method is invoked on an immutable object that would modify that object.
 * 
 * @author Kai Burjack
 */
public class ImmutableException extends RuntimeException {

    private Object immutable;
    private Object target;

    public ImmutableException(Object immutable, Object target) {
        super("Object is immutable");
        this.immutable = immutable;
        this.target = target;
    }

    /**
     * @return the immutable object
     */
    public Object getImmutable() {
        return immutable;
    }

    /**
     * @return the target/delegate of the immutable object
     */
    public Object getTarget() {
        return target;
    }

}
