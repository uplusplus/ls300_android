LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
	LOCAL_MODULE := liblua
#	LOCAL_SRC_FILES := 	$(call all-subdir-c-files)
	
	LUA         := src
	LOCAL_C_INCLUDES := $(LUA) 
	LOCAL_SRC_FILES := $(LUA)/lapi.c $(LUA)/lcode.c $(LUA)/lctype.c \
              $(LUA)/ldebug.c $(LUA)/ldo.c $(LUA)/ldump.c \
              $(LUA)/lfunc.c $(LUA)/lgc.c $(LUA)/llex.c \
              $(LUA)/lmem.c $(LUA)/lobject.c $(LUA)/lopcodes.c \
              $(LUA)/lparser.c $(LUA)/lstate.c $(LUA)/lstring.c \
              $(LUA)/ltable.c $(LUA)/ltm.c $(LUA)/lundump.c \
              $(LUA)/lvm.c $(LUA)/lzio.c $(LUA)/lauxlib.c \
              $(LUA)/lbaselib.c $(LUA)/lbitlib.c $(LUA)/lcorolib.c \
              $(LUA)/ldblib.c $(LUA)/liolib.c $(LUA)/lmathlib.c \
              $(LUA)/loslib.c $(LUA)/lstrlib.c $(LUA)/ltablib.c \
              $(LUA)/loadlib.c $(LUA)/linit.c
	
	LOCAL_CFLAGS 	+=  -DANDROID_OS -DLUA_COMPAT_ALL
include $(BUILD_STATIC_LIBRARY)

