//------------------------------------------------------------------------------
//
//
//
//
//
//------------------------------------------------------------------------------

#include <jni.h>
#include "ems_android_eglHook.h"
#include "ems_android_egl.h"
#include "namesocket.h"

//------------------------------------------------------------------------------
name_socket *fifo;
//------------------------------------------------------------------------------
void Andr_JNI_EglRenderer_nativeInit(JNIEnv* env, jobject object, jstring js) {
	char *dev;
	dev = (char*)env->GetStringUTFChars(js, 0);
	fifo = new name_socket(dev);
	env->ReleaseStringUTFChars(js, dev);
	egl_Init();
}

void Andr_JNI_EglRenderer_nativeDone(JNIEnv *env, jobject object) {
	delete fifo;
	egl_Uninit();
}

void Andr_JNI_EglRenderer_nativeRender(JNIEnv* env, jobject object) {
	egl_Draw();
}

void Andr_JNI_EglRenderer_nativeResize(JNIEnv* env, jobject object, jint w,
		jint h) {
	egl_Resize(w, h);
}
