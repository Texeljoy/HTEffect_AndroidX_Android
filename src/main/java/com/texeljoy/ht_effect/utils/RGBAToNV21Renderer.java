/**
 * RGBAToNV12Renderer.java
 * HTEffect
 *
 * read rgba texture from gpu to nv21 byte array
 *
 * Created by HTLab on 2022/4/20
 * Copyright © 2023 TexelJoy Tech. All rights reserved.
 */

package com.texeljoy.ht_effect.utils;

import com.texeljoy.hteffect.egl.HTGLUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static android.opengl.GLES20.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES20.GL_COLOR_ATTACHMENT0;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAMEBUFFER;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_RGBA;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.GL_UNSIGNED_INT;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindFramebuffer;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDeleteFramebuffers;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glFramebufferTexture2D;
import static android.opengl.GLES20.glGenFramebuffers;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glReadPixels;
import static android.opengl.GLES20.glTexImage2D;
import static android.opengl.GLES20.glTexParameterf;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

//@Keep
public class RGBAToNV21Renderer {
    private String TAG = getClass().getSimpleName();

    private final String vertexShaderSource = "" +
            "               attribute vec4 aPosition;\n" +
            "               attribute vec4 aTextureCoord;\n" +
            "               varying vec2 vTextureCoord;\n" +
            "               void main() {\n" +
            "                   gl_Position = aPosition;\n" +
            "                   vTextureCoord = aTextureCoord.xy;\n" +
            "}\n";
    private final String fragmentShaderSource = "" +
            "                #version 100\n" +
            "\n" +
            "                precision highp float;\n" +
            "                precision highp int;\n" +
            "\n" +
            "                varying vec2 vTextureCoord;\n" +
            "                uniform sampler2D uTexture;\n" +
            "\n" +
            "                uniform float uOffset;\n" +
            "\n" +
            "                const vec3 COEF_Y = vec3( 0.299,  0.587,  0.114);\n" +
            "                const vec3 COEF_U = vec3(-0.147, -0.289,  0.436);\n" +
            "                const vec3 COEF_V = vec3( 0.615, -0.515, -0.100);\n" +
            "                const float UV_DIVIDE_LINE = 2.0 / 3.0;\n" +
            "                void main() {\n" +
            "                   vec2 texelOffset = vec2(uOffset, 0.0);\n" +
            "                   if(vTextureCoord.y <= UV_DIVIDE_LINE) {\n" +
            "                       vec2 texCoord = vec2(vTextureCoord.x, vTextureCoord.y * 3.0 / 2.0);\n" +
            "                       vec4 color0 = texture2D(uTexture, texCoord);\n" +
            "                       vec4 color1 = texture2D(uTexture, texCoord + texelOffset);\n" +
            "                       vec4 color2 = texture2D(uTexture, texCoord + texelOffset * 2.0);\n" +
            "                       vec4 color3 = texture2D(uTexture, texCoord + texelOffset * 3.0);\n" +
            "\n" +
            "                       float y0 = dot(color0.rgb, COEF_Y);\n" +
            "                       float y1 = dot(color1.rgb, COEF_Y);\n" +
            "                       float y2 = dot(color2.rgb, COEF_Y);\n" +
            "                       float y3 = dot(color3.rgb, COEF_Y);\n" +
            "                       gl_FragColor = vec4(y0, y1, y2, y3);\n" +
            "                   }\n" +
            "                   else {\n" +
            "                       vec2 texCoord = vec2(vTextureCoord.x, (vTextureCoord.y - UV_DIVIDE_LINE) * 3.0);\n" +
            "                       vec4 color0 = texture2D(uTexture, texCoord);\n" +
            "                       vec4 color1 = texture2D(uTexture, texCoord + texelOffset);\n" +
            "                       vec4 color2 = texture2D(uTexture, texCoord + texelOffset * 2.0);\n" +
            "                       vec4 color3 = texture2D(uTexture, texCoord + texelOffset * 3.0);\n" +
            "\n" +
            "                       float v0 = dot(color0.rgb, COEF_V) + 0.5;\n" +
            "                       float u0 = dot(color1.rgb, COEF_U) + 0.5;\n" +
            "                       float v1 = dot(color2.rgb, COEF_V) + 0.5;\n" +
            "                       float u1 = dot(color3.rgb, COEF_U) + 0.5;\n" +
            "                       gl_FragColor = vec4(v0, u0, v1, u1);\n" +
            "                   }\n" +
            "                }";

    private float[] vertex = {
            -1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f
    };

    private float[] texture = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    private int[] indices = {
            0, 1, 2,
            0, 2, 3
    };

    private FloatBuffer vertexBuffer, textureBuffer;
    private IntBuffer indicesBuffer;

    private int program;
    private int positionLocation;
    private int textureCoordLocation;
    private int textureLocation;
    private int imageWidth, imageHeight;
    private int[] frameBufferId = new int[1];
    private int[] frameBufferTextureId = new int[1];

    private boolean isCreate = false;

    private int uOffsetLocation;

    public RGBAToNV21Renderer(int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public void create() {
        vertexBuffer = ByteBuffer.allocateDirect(vertex.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexBuffer.put(vertex).position(0);

        textureBuffer = ByteBuffer.allocateDirect(texture.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureBuffer.put(texture).position(0);

        indicesBuffer = ByteBuffer.allocateDirect(indices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer();
        indicesBuffer.put(indices).position(0);

        program = HTGLUtils.loadProgram(vertexShaderSource, fragmentShaderSource);

        glUseProgram(program);
        positionLocation = glGetAttribLocation(program, "aPosition");
        textureCoordLocation = glGetAttribLocation(program, "aTextureCoord");
        textureLocation = glGetUniformLocation(program, "uTexture");

        uOffsetLocation = glGetUniformLocation(program, "uOffset");

        glGenFramebuffers(1, frameBufferId, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId[0]);
        glGenTextures(1, frameBufferTextureId, 0);
        glBindTexture(GL_TEXTURE_2D, frameBufferTextureId[0]);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageWidth / 4, imageHeight * 3 / 2, 0, GL_RGBA, GL_UNSIGNED_BYTE, null);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, frameBufferTextureId[0], 0);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        glUseProgram(0);

        isCreate = true;
    }

    public void render(int textureId, ByteBuffer pixels) {
        if (textureId == 0 || !isCreate) {
//            HTLog.e(TAG, "input valid textureId or create first");
        }

        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId[0]);

        glUseProgram(program);

        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        glViewport(0, 0, imageWidth / 4, imageHeight * 3 / 2);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(textureLocation, 0);

        glUniform1f(uOffsetLocation, (float) (1.0 / (float) imageWidth));

        vertexBuffer.position(0);
        glVertexAttribPointer(positionLocation, 2, GL_FLOAT, false, 0, vertexBuffer);
        glEnableVertexAttribArray(positionLocation);
        textureBuffer.position(0);
        glVertexAttribPointer(textureCoordLocation, 2, GL_FLOAT, false, 0, textureBuffer);
        glEnableVertexAttribArray(textureCoordLocation);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, indicesBuffer);

        glReadPixels(0, 0, imageWidth / 4, imageHeight * 3 / 2, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

        glDisableVertexAttribArray(positionLocation);
        glDisableVertexAttribArray(textureCoordLocation);

        glBindTexture(GL_TEXTURE_2D, 0);

        glUseProgram(0);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void destroy() {
        isCreate = false;
        glDeleteProgram(program);
        glDeleteFramebuffers(1, frameBufferId, 0);
    }
}

