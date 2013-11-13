//file: ems_android_egl.h

#ifndef _EMS_ANDROID_EGL_H_
#define _EMS_ANDROID_EGL_H_

#ifdef __cplusplus
extern "C"
{
#endif

void egl_Draw();
void egl_Init();
void egl_Uninit();
void egl_Update();
void egl_Resize(int w, int h);

void egl_SetImage(unsigned char* pixel,int w, int h);
void egl_Swap(unsigned char* pixel);

int egl_GetMaxImageSize();

#ifdef __cplusplus
}
#endif

#endif /*_EMS_ANDROID_EGL_H_*/
