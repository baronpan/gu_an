LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := native-lib
LOCAL_LDLIBS := -llog 
LOCAL_SRC_FILES := native-lib.c

include $(BUILD_SHARED_LIBRARY)