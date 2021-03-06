LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
	LOCAL_MODULE_TAGS := optional
	LOCAL_MODULE := ecore_test

	LOCAL_C_INCLUDES := $(ROOT_PATH)/inc \
						$(ROOT_PATH)/external/pcl_android \
						$(ROOT_PATH)/external/ \
						$(ROOT_PATH)/external/laslib/inc
						
	LOCAL_SRC_FILES := $(call all-cpp-files-under,.) $(call all-c-files-under,.)  
	
	LOCAL_CFLAGS += -fexceptions -DANDROID_OS -DE_PRINT -D__ANDROID__ -DANDROID
	LOCAL_CPPFLAGS += -fexceptions -frtti
	
	LOCAL_LDFLAGS += $(ROOT_PATH)/libs/arm/std/libstdc++.a -Wl,-v
 	LOCAL_LDLIBS :=	 -L$(ROOT_PATH)/libs/arm/pcl -lpcl_common -lpcl_io \
			 		 -L$(ROOT_PATH)/libs/arm/gif -lgif \
			 		 -L$(ROOT_PATH)/libs/arm/boost \
			 		 -lboost_thread -lboost_system \
			 		 -lboost_filesystem -llog 
	LOCAL_STATIC_LIBRARIES := libarch liblas libminini libjpeg libecore_s
	LOCAL_SHARED_LIBRARIES := 

	LOCAL_PRELINK_MODULE := false
include $(BUILD_EXECUTABLE)

 
