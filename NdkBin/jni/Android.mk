LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := testbin
LOCAL_LDLIBS := -llog 
LOCAL_CFLAGS += -fPIE
LOCAL_LDFLAGS := -fPIE -pie
LOCAL_SRC_FILES := main.c

include $(BUILD_EXECUTABLE)