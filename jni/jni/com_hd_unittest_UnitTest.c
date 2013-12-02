
#include "com_hd_unittest_UnitTest.h"
#include <ls300/hd_laser_sick.h>

int test_main() {
	int ret;
	laser_control_t control;
	sickld_t sick;
	laser_sick_t *ls;
	DMSG((STDOUT,"scan test config.\r\n"));
	ret = ls_init(&ls, (void*)1, (void*)1, NULL, NULL);

	ret = ls_phrase_config(ls, 50, 0, 360, 5, 0.5, 1, -45, 90);
	ret = ls_phrase_config(ls, 50, 0, 360, 7, 0.375, 1, -45, 90);
	ret = ls_phrase_config(ls, 100, 0, 360, 5, 0.25, 1, -45, 90);
	ret = ls_phrase_config(ls, 200, 0, 360, 5, 0.125, 1, -45, 90);
	ret = ls_phrase_config(ls, 850, 0, 360, 10, 0.0625, 4, -45, 90);

	e_assert(ret>0, ret);

	ls_uninit(ls);
}


/*
 * Class:     com_hd_unittest_UnitTest
 * Method:    native_main
 * Signature: ()V
 */

JNIEXPORT void JNICALL Java_com_hd_unittest_UnitTest_native_1main
  (JNIEnv *env, jclass clazz)
{
	test_main();
}
