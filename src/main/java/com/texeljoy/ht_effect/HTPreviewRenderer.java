package com.texeljoy.ht_effect;

import android.annotation.SuppressLint;
import android.opengl.Matrix;
import androidx.annotation.Keep;
import android.util.Log;
import com.texeljoy.hteffect.egl.HTGLUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

@Keep
public class HTPreviewRenderer {

    private String TAG = getClass().getSimpleName();

    private int rotation = 0;
    private boolean mirror = false;

    private String vertexShaderSource = "#version 100\n" +
            "\n" +
            "                attribute vec4 aPosition;\n" +
            "                attribute vec4 aTextureCoord;\n" +
            "                uniform mat4 uMvpMatrix;\n" +
            "                uniform mat4 uTextureMatrix;\n" +
            "\n" +
            "                varying vec2 vTextureCoord;\n" +
            "                void main() {\n" +
            "                    gl_Position = uMvpMatrix * aPosition;\n" +
            "                    vTextureCoord = (uTextureMatrix * aTextureCoord).xy;\n" +
            "                }";

    private String fragmentShaderSource = "#version 100\n" +
            "\n" +
            "                precision mediump float;\n" +
            "                varying vec2 vTextureCoord;\n" +
            "                uniform sampler2D uTexture;\n" +
            "                void main() {\n" +
            "                    gl_FragColor = texture2D(uTexture, vTextureCoord);\n" +
            "                }";

    private float[] vertex0 = {

            -1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f


    };
    private float[] vertex90 = {

        1.0f, -1.0f,
        -1.0f, -1.0f,
        -1.0f, 1.0f,
        1.0f, 1.0f

    };

    private float[] vertex180 = {

        1.0f, 1.0f,
        1.0f, -1.0f,
        -1.0f, -1.0f,
        -1.0f, 1.0f

    };

    private float[] vertex270 = {

        -1.0f, 1.0f,
        1.0f, 1.0f,
        1.0f, -1.0f,
        -1.0f, -1.0f

    };

    private float[] frontTexture = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f,
    };
    private float[] backTexture = {
            1.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
    };

    private byte[] indices = {
            0, 1, 2,
            0, 2, 3
    };

    private FloatBuffer vertexBuffer, textureBuffer;
    private ByteBuffer indicesBuffer;

    private int program;
    private int positionLocation;
    private int textureCoordLocation;
    private int textureLocation;
    private int mvpMatrixLocation;
    private int textureMatrixLocation;
    private int imageWidth, imageHeight;
    private IntBuffer surfaceWidth, surfaceHeight;

    private float[] mvpMatrix = new float[16];
    private float[] textureMatrix = new float[16];

    private boolean isCreate = false;

    public HTPreviewRenderer(int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        Log.e("screenWidth preview: ", String.valueOf(imageWidth));
        Log.e("screenHeight preview: " , String.valueOf(imageHeight));
    }


    @SuppressLint("LongLogTag")
    public void create(boolean isFront) {
        float[] vertex = new float[8];
        switch (rotation){
            case 0:
                System.arraycopy(vertex270, 0, vertex, 0, 8);

                break;
            case 90:
                System.arraycopy(vertex180, 0, vertex, 0, 8);

                break;
            case 180:
                System.arraycopy(vertex90, 0, vertex, 0, 8);

                break;
            default:
                System.arraycopy(vertex0, 0, vertex, 0, 8);

                break;
        }
        vertexBuffer = ByteBuffer.allocateDirect(vertex.length * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();
        vertexBuffer.put(vertex).position(0);

        indicesBuffer = ByteBuffer.allocateDirect(indices.length * 4)
            .order(ByteOrder.nativeOrder());
        indicesBuffer.put(indices).position(0);

        if (isFront) {
            textureBuffer = ByteBuffer.allocateDirect(frontTexture.length * 4)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            textureBuffer.put(frontTexture).position(0);
        } else {
            textureBuffer = ByteBuffer.allocateDirect(backTexture.length * 4)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            textureBuffer.put(backTexture).position(0);
        }



        program = HTGLUtils.loadProgram(vertexShaderSource, fragmentShaderSource);

        glUseProgram(program);
        positionLocation = glGetAttribLocation(program, "aPosition");
        textureCoordLocation = glGetAttribLocation(program, "aTextureCoord");
        mvpMatrixLocation = glGetUniformLocation(program, "uMvpMatrix");
        textureMatrixLocation = glGetUniformLocation(program, "uTextureMatrix");
        textureLocation = glGetUniformLocation(program, "uTexture");

        Matrix.setIdentityM(mvpMatrix, 0);
        glUniformMatrix4fv(mvpMatrixLocation, 1, false, mvpMatrix, 0);

        Matrix.setIdentityM(textureMatrix, 0);
        glUniformMatrix4fv(textureMatrixLocation, 1, false, textureMatrix, 0);

        glUseProgram(0);

        isCreate = true;
    }

    public void setPreviewRotation(int rotation){
        this.rotation = rotation;
    }
    public void setPreviewMirror(boolean mirror){
        this.mirror = mirror;
    }

    public void setTextureMatrix(float[] textureMatrix) {
        glUseProgram(program);
        this.textureMatrix = textureMatrix;
        glUseProgram(0);
    }

    public void render(int textureId) {
        if (textureId == 0 || !isCreate) {
            // HTLog.e(TAG, "input valid textureId or create first");
        }

        glUseProgram(program);

        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        glViewport(0, 0, imageWidth, imageHeight);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glUniform1i(textureLocation, 0);
        vertexBuffer.position(0);
        glVertexAttribPointer(positionLocation, 2, GL_FLOAT, false, 0, vertexBuffer);
        glEnableVertexAttribArray(positionLocation);
        textureBuffer.position(0);
        glVertexAttribPointer(textureCoordLocation, 2, GL_FLOAT, false, 0, textureBuffer);
        glEnableVertexAttribArray(textureCoordLocation);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, indicesBuffer);

        glDisableVertexAttribArray(positionLocation);
        glDisableVertexAttribArray(textureCoordLocation);

        glBindTexture(GL_TEXTURE_2D, 0);

        glUseProgram(0);
    }

    public void destroy() {
        isCreate = false;
        glDeleteProgram(program);
    }
}
