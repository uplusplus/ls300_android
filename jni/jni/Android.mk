LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
	LOCAL_MODULE := ecorejni
	
	LOCAL_C_INCLUDES := $(ROOT_PATH)/inc


	LOCAL_SRC_FILES := $(call all-subdir-c-files)
	
	LOCAL_CFLAGS += -fexceptions -DANDROID_OS -DE_PRINT
	LOCAL_CPPFLAGS += -fexceptions -frtti
	
	LOCAL_LDFLAGS += -llog -lc

	LOCAL_STATIC_LIBRARIES :=	  libecore_s
	LOCAL_SHARED_LIBRARIES :=  libcutils libutils libstlport libstdc++
	
	
include $(BUILD_SHARED_LIBRARY)
#include $(BUILD_STATIC_LIBRARY)