//----------------------------------------------------------------------------------
//
//
//
//
//
//----------------------------------------------------------------------------------

#ifndef EMS_ANDROID_EGLHOOK_H
#define EMS_ANDROID_EGLHOOK_H

//----------------------------------------------------------------------------------

#include <jni.h>

//----------------------------------------------------------------------------------

void 	Andr_JNI_EglRenderer_nativeInit(JNIEnv* env, jobject object,jstring js);
void 		Andr_JNI_EglRenderer_nativeDone(JNIEnv *env, jobject object );
void 		Andr_JNI_EglRenderer_nativeRender(JNIEnv *env, jobject object );
void 		Andr_JNI_EglRenderer_nativeResize(JNIEnv* env, jobject object, jint w, jint h);

//----------------------------------------------------------------------------------
#endif	//	EMS_ANDROID_EGLHOOK_H

