#include <android/log.h>

#define STDOUT __stdout__
#define __stdout__ ANDROID_LOG_INFO,__func__
#define EMS_FPRIINT __android_log_print
#define DMSG(__args__) EMS_FPRIINT __args__
#define NULL (0x0)

#define e_assert(_arg_,...) \
do{ \
	if(!(_arg_)){ \
		DMSG((STDOUT,"%s:%d:%s",__FILE__,__LINE__,#_arg_ " is false. Error " #__VA_ARGS__ "\r\n")); \
		return __VA_ARGS__;} \
}while(0)

extern char EGL_NODE[100];

typedef	unsigned char uint8_t;
typedef	short		int16_t;
typedef	unsigned short	uint16_t;
typedef	int			int32_t;
typedef	unsigned int		uint32_t;

/*接口定义*/
#ifdef __cplusplus
extern "C" {
#endif

int egl_video_set_src(uint8_t *src,int s_w,int s_h,float s_h_w);
int egl_video_set_screen(int screen_w,int screen_h,int bpp,void (*set_image)(unsigned char* pixel,int w, int h));
int egl_video_upate_screen();

#ifdef __cplusplus
}
#endif

