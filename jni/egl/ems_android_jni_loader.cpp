//------------------------------------------------------------------------------
//
//
//
//
//
//------------------------------------------------------------------------------
#include "ems_android_eglHook.h"
#include "base.h"
//------------------------------------------------------------------------------
typedef union
{
	JNIEnv* nativeEnvironment;
	void* venv;
}UnionJNIEnvToVoid;

//------------------------------------------------------------------------------
static const char *classPathName = "com/hd/egl/EglRenderer";

//------------------------------------------------------------------------------
static JNINativeMethod __jni_methods[] =
{
		{ "nativeInit", "(Ljava/lang/String;)V", (void *) Andr_JNI_EglRenderer_nativeInit },
		{ "nativeResize", "(II)V", (void *) Andr_JNI_EglRenderer_nativeResize },
		{ "nativeRender", "()V", (void *) Andr_JNI_EglRenderer_nativeRender },
		{ "nativeDone", "()V", (void *) Andr_JNI_EglRenderer_nativeDone }
};


/*
 * Register several native methods for one class.
 */
static int
__registerNativeMethods(JNIEnv* env, const char* className,
		JNINativeMethod* gMethods, int numMethods)
{
	jclass clazz;

	clazz = env->FindClass(className);
	if (clazz == NULL)
	{
		DMSG((STDOUT, "Native registration unable to find class '%s'", className));
		return JNI_FALSE;
	}

	// register jni native functions invoked from java
	if (env->RegisterNatives(clazz, gMethods, numMethods) < 0)
	{
		DMSG((STDOUT, "RegisterNatives failed for '%s'", className));
		return JNI_FALSE;
	}

	return JNI_TRUE;
}

/*
 * Register native methods for all classes we know about.
 */
static int
__registerNatives(JNIEnv* env)
{
	if (!__registerNativeMethods(env, classPathName, __jni_methods,  sizeof(__jni_methods) / sizeof(__jni_methods[0])))
		return JNI_FALSE;
	else
		return JNI_TRUE;
}

//------------------------------------------------------------------------------
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* /*reserved*/)
{
	DMSG((STDOUT, "egl load"));
	UnionJNIEnvToVoid uenv;
	uenv.venv = NULL;
	JNIEnv* env = NULL;

	if (vm->GetEnv(&uenv.venv, JNI_VERSION_1_4) != JNI_OK)
	{
		DMSG((STDOUT, "GetEnv failed"));
		return -1;
	}
	env = uenv.nativeEnvironment;
	if (!__registerNatives(env))
	{
		DMSG((STDOUT, "registerNatives failed"));
		return -1;
	}

	return JNI_VERSION_1_4;
}
