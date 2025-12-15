#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_dev_aravindraj_composerecipeapp_utils_AppConstants_getAPIKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string apiKey = "YOUR_API_KEY";
    return env->NewStringUTF(apiKey.c_str());
}