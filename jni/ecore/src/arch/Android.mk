LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
	LOCAL_MODULE_TAGS := optional
	LOCAL_MODULE := arch

	LOCAL_C_INCLUDES := $(ROOT_PATH)/inc 
						

	LOCAL_SRC_FILES := $(call all-cpp-files-under,.) $(call all-c-files-under,.)  
	
	LOCAL_CFLAGS += -fexceptions -DANDROID_OS -DE_PRINT -D__ANDROID__ -DANDROID
	
 	LOCAL_LDLIBS := -llog

	LOCAL_PRELINK_MODULE := false
include $(BUILD_STATIC_LIBRARY)
