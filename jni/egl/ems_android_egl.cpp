/*
 * egl draw
 * author: uplusplus
 * date: 2010/8/3
 * */
#include <GLES/gl.h>
#include <malloc.h>
#include <string.h>
#include "base.h"
#include "hello_mesh.h"
#include "ems_android_egl.h"

static long floatToFixed(float value) {
	if (value < -32768)
		value = -32768;
	if (value > 32767)
		value = 32767;
	return (long) (value * 65536);
}

#define FIXED(value) floatToFixed(value)

//#define FIXED(value) ((long)(value * 65536))

static int isContextChanged = 0;
static int surface_width = 320, surface_height = 480;
static int image_width = 4, image_height = 4;
static int scaled_width = 4, scaled_height = 4;

static float dx = 0, dy = 0;

static unsigned char *image_pixel = NULL;
static unsigned char color_table[4 * 4];

GLfixed vertices[8] = { 0 };
const static GLfixed texture[8] = { 0x00000, 0x00000, 0x10000, 0x00000, 0x00000,
		0x10000, 0x10000, 0x10000 };
const static GLfixed colors_ligth[16] = { 0x10000, 0x10000, 0x10000, 0x10000,
		0x10000, 0x10000, 0x10000, 0x10000, 0x10000, 0x10000, 0x10000, 0x10000,
		0x10000, 0x10000, 0x10000, 0x10000 };

const static GLfixed colors_rgb[16] = { FIXED(0.5), 0x00000, 0x00000, 0x10000,
		0x00000, 0x00000, FIXED(0.5), 0x10000, 0x00000, FIXED(0.5), 0x00000,
		0x10000, 0x10000, 0x10000, 0x10000, 0x10000 };

static void ResetContext();

void egl_check_error(int line) {
	GLenum err;
	err = glGetError();
	if (GL_NO_ERROR != err) {
		DMSG((STDOUT,"line:%d GL ERROR 0x%5x\n",line,err));
	}
}

#define CHECK_ERROR() egl_check_error(__LINE__)


void draw_color_table() {
	glEnableClientState(GL_COLOR_ARRAY);

	glColorPointer(4, GL_FIXED, 0, colors_rgb);

	memset(color_table, 255, sizeof(color_table));
	glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, image_width, image_width, 0,
			GL_LUMINANCE, GL_UNSIGNED_BYTE, (GLvoid *) color_table);
	CHECK_ERROR();

	//拉伸全屏
	vertices[2] = vertices[6] = FIXED( surface_width );
	vertices[5] = vertices[7] = FIXED( surface_height );

	glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
	CHECK_ERROR();

	glDisableClientState(GL_COLOR_ARRAY);
}

void egl_Draw() {
//	DMSG((STDOUT,"enter draw egl"));
	if (!image_pixel) {
		draw_color_table();
		return;
	}
	if (isContextChanged) {
		ResetContext();
		return;
	}
//	DMSG((STDOUT,"draw egl image_width=%d, image_height=%d, image_pixel=%p value=%d", image_width, image_height, image_pixel,(int)*image_pixel));
	glTexSubImage2D(GL_TEXTURE_2D, 0, dx, dy, image_width, image_height,
			GL_LUMINANCE, GL_UNSIGNED_BYTE, (GLvoid *) image_pixel);
	CHECK_ERROR();
	glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
	CHECK_ERROR();

}

void egl_Update() {
	//glFinish();
	//Andr_JNI_UpdateScreen();
}

void egl_Resize(int w, int h) {
	if (!(w && h))
		return;
	DMSG(
			(STDOUT,"egl_Resize:\n\tview surface_width:%d surface_height:%d.\n", w, h));
	surface_width = w;
	surface_height = h;
	if (!egl_video_set_screen(w, h, 1, egl_SetImage))
		ResetContext();
}

void egl_Init() {
	DMSG((STDOUT,"GL_VENDOR:\t%s\n", glGetString(GL_VENDOR)));
	DMSG((STDOUT,"GL_RENDERER:\t%s\n", glGetString(GL_RENDERER)));
	DMSG((STDOUT,"GL_VERSION:\t%s\n", glGetString(GL_VERSION)));
	DMSG((STDOUT,"GL_MAX_TEXTURE_SIZE:\t%d\n",egl_GetMaxImageSize()));
	DMSG((STDOUT,"GL_EXTENSIONS:\t%s\n", glGetString(GL_EXTENSIONS)));

	ResetContext();
}

void egl_Uninit() {

}

int egl_GetMaxImageSize() {
	int size;
	glGetIntegerv(GL_MAX_TEXTURE_SIZE, &size);
	return size;
}

//注意：所有的opengles相关的函数必须位于java层的opengl上下文下，从另外的线程调用这些函数将没有效果
//这个函数是从我们的线程调用的，没有环境，所以这里只能标记，等到画窗体时再执行opengl函数
//这里是实际上的初始化的地方，没有设置image_pixel之前，一切都没有作用。
void egl_SetImage(unsigned char* pixel, int w, int h) {
	DMSG((STDOUT,"image width:%d  _height:%d.\n", w, h));
	image_width = w;
	image_height = h;
	image_pixel = pixel;
	isContextChanged = 1;
	//ResetContext();
}

//void egl_Swap(unsigned char* pixel) {
//	image_pixel = pixel;
//}

static void ResetContext() {
	unsigned char *data;
	int i;
	isContextChanged = 0;
	scaled_width = 2;
	//原本不用如些大的纹理，但如果纹理不够大，会自动重复
	while (scaled_width < image_width || scaled_width < surface_width)
		scaled_width <<= 1;
	scaled_height = 2;
	while (scaled_height < image_height || scaled_height < surface_height)
		scaled_height <<= 1;

	DMSG((STDOUT, "scaled_width %d scaled_height %d\n", scaled_width, scaled_height));

	data = (unsigned char *) malloc(
			scaled_width * scaled_height * sizeof(char));
	if (!data) {
		DMSG(
				(STDOUT,"Malloc %d MB memory failed.", scaled_width * scaled_height * sizeof(char) /(1024*1024)));
	}

	glViewport(0, 0, surface_width, surface_height);
	glClearColorx(FIXED(0.1f), FIXED(0.2f), FIXED(0.3f), 0x10000);
	glClear(GL_COLOR_BUFFER_BIT);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrthox(0, FIXED(surface_width), FIXED(surface_height), 0, 0x10000,
			-0x10000);

	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	glEnable(GL_TEXTURE_2D);
	glTexParameterx(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameterx(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameterx(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	glTexParameterx(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

	glBindTexture(GL_TEXTURE_2D, 0);

	glTexImage2D(GL_TEXTURE_2D, 0, GL_LUMINANCE, scaled_width, scaled_height, 0,
			GL_LUMINANCE, GL_UNSIGNED_BYTE, (GLvoid *) data);
	CHECK_ERROR();

	//保持原尺寸
	vertices[2] = vertices[6] = FIXED( scaled_width );
	vertices[5] = vertices[7] = FIXED( scaled_height );

	//居中
	dx = (surface_width - image_width) / 2.0;
	dy = (surface_height - image_height) / 2.0;

	DMSG((STDOUT,"surface_width=%d,surface_height=%d,image_width=%d,image_height=%d dx=%f dy=%f\r\n",
			surface_width,surface_height,image_width,image_height,dx,dy));
	glVertexPointer(2, GL_FIXED, 0, vertices);
	CHECK_ERROR();
	glTexCoordPointer(2, GL_FIXED, 0, texture);
	CHECK_ERROR();

	free(data);
}
