#include <jni.h>
#include <string.h>
#include <android/log.h>

int main(int argc, char const *argv[])
{
    __android_log_print(ANDROID_LOG_DEBUG, "TEST", "First elf test.");
    return 0;
}
