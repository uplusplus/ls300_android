#include <android/log.h>

#define STDOUT __stdout__
#define __stdout__ stdout
#define EMS_FPRIINT fprintf
#define DMSG(__args__) EMS_FPRIINT __args__
#define NULL (0x0)
