#include <jni.h>
#include <string>
#include <dlfcn.h>
#include <android/log.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>


typedef void * (*MSGetImageByName)(const char *file);
typedef void * (*MSFindSymbol)(void *image, const char *name);
typedef void (*MSHookFunction)(void *symbol, void *hook, void **old);

typedef struct {
    MSGetImageByName msGetImageByNamePtr;
    MSFindSymbol msFindSymbolPtr;
    MSHookFunction msHookFunctionPtr;
} Substrate;

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG  , "MyHook", __VA_ARGS__)

typedef int (*fn_open)(const char *, int );

extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_myhook_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

static const char * SUBSTRATE = "/data/user/0/com.example.myhook/files/libsubstrate.so";

fn_open g_open;
int hooked_open(const char * path, int flag) {
    LOGD("open %s.", path);

    //g_open(path, flag);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_myhook_MainActivity_startHook(JNIEnv *env, jobject instance) {

    // TODO
    void * handle = dlopen(SUBSTRATE, 0);
    if (handle == NULL) {
        LOGD("Open substrate failed.");
        return ;
    }

    Substrate g_ms;
    if (!(g_ms.msGetImageByNamePtr = (MSGetImageByName)dlsym(handle, "MSGetImageByName"))) {
        LOGD("find MSGetImageByName failed: %s.", dlerror());
        return ;
    }

    if (!(g_ms.msFindSymbolPtr = (MSFindSymbol)dlsym(handle, "MSFindSymbol"))) {
        LOGD("find MSFindSymbol failed: %s.", dlerror());
        return ;
    }

    if (!(g_ms.msHookFunctionPtr = (MSHookFunction)dlsym(handle, "MSHookFunction"))) {
        LOGD("find MSHookFunction failed: %s.", dlerror());
        return ;
    }

    void *image = g_ms.msGetImageByNamePtr("/system/lib/libc.so");
    void *pf_open = g_ms.msFindSymbolPtr(image, "open");
    if (!pf_open) {
        LOGD("__open not found.");
        return ;
    }


    g_ms.msHookFunctionPtr(pf_open, (void *)hooked_open, (void **)&g_open);
    LOGD("start hook open.");
}


extern "C"
JNIEXPORT void JNICALL
Java_com_example_myhook_MainActivity_openMemmap(JNIEnv *env, jobject instance) {

    // TODO
    char buf[1024];
    sprintf(buf, "/proc/%d/maps", getpid());

    open(buf, 0);
}