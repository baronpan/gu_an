#include <jni.h>
#include <string.h>
#include <android/log.h>

JNIEXPORT jstring JNICALL
Java_com_example_libtest_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject this) {
    char* hello = "Hello from C++";
    __android_log_print(ANDROID_LOG_DEBUG, "TEST", "First lib test.");
    return (*env)->NewStringUTF(env, hello);
}
