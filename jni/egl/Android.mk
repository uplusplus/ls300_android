LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# android custom headers and libs path which depends on specific platform
MY_CUSTOM_INC := $(SYSROOT)/custom/include 
MY_CUSTOM_LD_PATH := $(SYSROOT)/custom/lib

## ems platform headers and prebuilt libs path
#MY_ECORE_INC := $(ROOT_PATH)/inc
#MY_EMS_LD_PATH := $(LOCAL_PATH)/../../../../../lib/armeabi

LOCAL_CFLAGS :=					\
				-I$(MY_CUSTOM_INC)				\
				-I$(MY_ECORE_INC)					\
				-DLINUX_ANDROID					\
				-DMSG_PRINT					\
				-DLINUX_CONSOLE					\
				-DMSGPRT

LOCAL_LDLIBS := -llog \
				-lGLESv1_CM \
				-L$(MY_CUSTOM_LD_PATH)		\
				-L$(MY_EMS_LD_PATH)


LOCAL_SRC_FILES :=				\
				fifo.cpp				\
				ems_android_egl.cpp 	\
				ems_android_jni_loader.cpp \
				ems_android_eglHook.cpp \
				namesocket.cpp \
				egl_video.c

LOCAL_MODULE := egl

LOCAL_STATIC_LIBRARIES := libecore_s


include $(BUILD_SHARED_LIBRARY)
