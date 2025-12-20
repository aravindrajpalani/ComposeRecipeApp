#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_dev_aravindraj_composerecipeapp_utils_AppConstants_getAPIKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string apiKey = "edc5004401894f89bb0d837c23525b9a";
    return env->NewStringUTF(apiKey.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_dev_aravindraj_composerecipeapp_utils_AppConstants_getUserName(
        JNIEnv *env,
        jobject /* this */) {
    std::string userName = "YOUR_USER_NAME";
    return env->NewStringUTF(userName.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_dev_aravindraj_composerecipeapp_utils_AppConstants_getUserNameHash(
        JNIEnv *env,
        jobject /* this */) {
    std::string userNameHash = "YOUR_USER_NAME_HASH";
    return env->NewStringUTF(userNameHash.c_str());
}