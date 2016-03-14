/// <reference path="joml.d.ts"/>

var gl: WebGLRenderingContext;

var shaderProgram = (gl: WebGLRenderingContext, vs: string, fs: string): WebGLProgram => {
    var prog = gl.createProgram();
    var createAndAttachShader = function (type: number, source: string) {
        var s: WebGLShader = gl.createShader(type);
        gl.shaderSource(s, source);
        gl.compileShader(s);
        if (!gl.getShaderParameter(s, gl.COMPILE_STATUS)) {
            throw "Could not compile " + type + " shader:\n" + gl.getShaderInfoLog(s);
        }
        gl.attachShader(prog, s);
    };
    createAndAttachShader(gl.VERTEX_SHADER, vs);
    createAndAttachShader(gl.FRAGMENT_SHADER, fs);
    gl.linkProgram(prog);
    if (!gl.getProgramParameter(prog, gl.LINK_STATUS)) {
        throw "Could not link the shader program:\n" + gl.getProgramInfoLog(prog);
    }
    return prog;
}

var setAttributeBuffer = (gl: WebGLRenderingContext, prog: WebGLProgram, name: string, rsize: number, arr: number[]): void => {
    gl.bindBuffer(gl.ARRAY_BUFFER, gl.createBuffer());
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(arr), gl.STATIC_DRAW);
    var loc = gl.getAttribLocation(prog, name);
    gl.enableVertexAttribArray(loc);
    gl.vertexAttribPointer(loc, rsize, gl.FLOAT, false, 0, 0);
}

var initAndDraw = (): void => {
    try {
        var elem: HTMLCanvasElement = <HTMLCanvasElement>document.getElementById("webgl");
        gl = elem.getContext("experimental-webgl");
        if (!gl) { throw "x"; }
    } catch (err) {
        throw "Your web browser does not support WebGL";
    }

    gl.clearColor(0.2, 0.4, 0.6, 1.0);
    gl.clear(gl.COLOR_BUFFER_BIT);

    var prog = shaderProgram(gl,
        "attribute vec3 pos;" +
        "uniform mat4 m;" +
        "void main() {" +
        "	gl_Position = m * vec4(pos, 1.0);" +
        "}",
        "void main() {" +
        "	gl_FragColor = vec4(0.6, 0.7, 0.8, 1.0);" +
        "}"
    );
    gl.useProgram(prog);

    setAttributeBuffer(gl, prog, "pos", 3, [
        -1, -1, 0,
         1, -1, 0,
        -1,  1, 0,
         1,  1, 0
    ]);
    
    var arr = new Float32Array(16);
    var m = new JOML.Matrix4()
        .perspective(JOML.toRadians(45.0), 1.0, 0.1, 100.0, false)
        .lookAt(0, 2, 10,
                0, 0, 0,
                0, 1, 0)
        .rotateZ(JOML.toRadians(45));
    gl.uniformMatrix4fv(gl.getUniformLocation(prog, "m"), false, m.get(arr));
    gl.drawArrays(gl.TRIANGLE_STRIP, 0, 4);
}

window.onload = () => {
    initAndDraw();
}
