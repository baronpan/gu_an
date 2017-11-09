#include <jni.h>
#include <string>
#include <android/log.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_libtest_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    __android_log_print(ANDROID_LOG_DEBUG, "TEST", "First lib test.");
    return env->NewStringUTF(hello.c_str());
}
