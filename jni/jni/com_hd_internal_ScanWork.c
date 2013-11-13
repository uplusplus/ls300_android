/* DO NOT EDIT THIS FILE - it is machine generated */
#include "com_hd_internal_ScanWork.h"
#include <ls300/hd_laser_scan.h>



/*
 * Class:     com_hd_internal_Scan
 * Method:    sj_create
 * Signature: (JLjava/lang/String;I)J
 */
JNIEXPORT jlong JNICALL Java_com_hd_internal_ScanWork_sj_1create
(JNIEnv *env, jclass clazz, jstring jsdev, jint baud, jstring jsip, jint port) {
	int ret;
	char *ip,*dev;
	struct scan_job_t* ptr;
	ip = (*env)->GetStringUTFChars(env, jsip, 0);
	dev = (*env)->GetStringUTFChars(env, jsdev, 0);
	ret = sj_create(&ptr, dev,baud,ip, port);
	(*env)->ReleaseStringUTFChars(env, jsip, ip);
	(*env)->ReleaseStringUTFChars(env, jsdev, dev);
	e_assert(ret>0, 0);
	return (jlong) ptr;
}

/*
 * Class:     com_hd_internal_Scan
 * Method:    sj_destroy
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_hd_internal_ScanWork_sj_1destroy
(JNIEnv *env, jclass clazz, jlong priv) {

	int ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	ret = sj_destroy(scan);
	e_assert(ret>0, 0);
	return 1;
}


/*
 * Class:     com_hd_internal_ScanWork
 * Method:    sj_cancel
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_hd_internal_ScanWork_sj_1cancel
(JNIEnv *env, jclass clazz, jlong priv) {
	int ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	ret = sj_cancel(scan);
	e_assert(ret>0, 0);
	return 1;
}
/*
 * Class:     com_hd_internal_ScanWork
 * Method:    sj_check_devices
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_hd_internal_ScanWork_sj_1check_1devices
(JNIEnv *env, jclass clazz, jlong priv) {
	int ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	ret = sj_check_devices(scan);
	e_assert(ret>0, 0);
	return 1;
}

/*
 * Class:     com_hd_internal_ScanWork
 * Method:    sj_scan_photo
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_hd_internal_ScanWork_sj_1scan_1photo
(JNIEnv *env, jclass clazz, jlong priv) {
	int ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	ret = sj_scan_photo(scan);
	e_assert(ret>0, 0);
	return 1;
}

/*
 * Class:     com_hd_internal_ScanWork
 * Method:    sj_scan_point
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_hd_internal_ScanWork_sj_1scan_1point
(JNIEnv *env, jclass clazz, jlong priv) {
	int ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	ret = sj_scan_point(scan);
	e_assert(ret>0, 0);
	return 1;
}

/*
 * Class:     com_hd_internal_Scan
 * Method:    sj_config
 * Signature: (JIDDIDDD)I
 */
JNIEXPORT jint JNICALL Java_com_hd_internal_ScanWork_sj_1config
(JNIEnv *env, jclass clazz, jlong priv, jint p1, jdouble p2, jdouble p3, jfloat p4,
		jdouble p5, jdouble p6, jdouble p7) {
	int ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	ret = sj_config(scan, p1, p2, p3, p4, p5, p6, p7);
	e_assert(ret>0, 0);
	return 1;
}


/*
 * Class:     com_hd_internal_ScanWork
 * Method:    sj_set_data_dir
 * Signature: (JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
 */

JNIEXPORT jint JNICALL Java_com_hd_internal_ScanWork_sj_1set_1data_1dir
(JNIEnv *env, jclass clazz, jlong priv, jstring str, jstring str1, jstring str2) {
	int ret;
	char *dir,*dir1,*dir2;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	dir = (*env)->GetStringUTFChars(env, str, 0);
	dir1 = (*env)->GetStringUTFChars(env, str1, 0);
	dir2 = (*env)->GetStringUTFChars(env, str2, 0);
	ret = sj_set_data_dir(scan, dir,dir1,dir2);
	(*env)->ReleaseStringUTFChars(env, str, dir);
	(*env)->ReleaseStringUTFChars(env, str1, dir1);
	(*env)->ReleaseStringUTFChars(env, str2, dir2);
	e_assert(ret>0, 0);
	return 1;
}

/*
 * Class:     com_hd_internal_ScanWork
 * Method:    sj_get_state
 * Signature: (J)D
 */
JNIEXPORT jint JNICALL Java_com_hd_internal_ScanWork_sj_1get_1state
(JNIEnv *env, jclass clazz, jlong priv) {
	int ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	return sj_get_state(scan);
}

/*
 * Class:     com_hd_internal_ScanWork
 * Method:    sj_get_angle
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_hd_internal_ScanWork_sj_1get_1angle
(JNIEnv *env, jclass clazz, jlong priv) {
	int ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	return sj_get_angle(scan);
}


/*
 * Class:     com_hd_internal_ScanWork
 * Method:    sj_get_temperature
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_hd_internal_ScanWork_sj_1get_1temperature
(JNIEnv *env, jclass clazz, jlong priv) {
	int ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	return sj_get_temperature(scan);
}

/*
 * Class:     com_hd_internal_ScanWork
 * Method:    sj_get_tilt
 * Signature: (J[D)I
 */
JNIEXPORT jint JNICALL Java_com_hd_internal_ScanWork_sj_1get_1tilt
(JNIEnv *env, jclass clazz, jlong priv,jdoubleArray jda)
{
	jdouble dangle[2];
	angle_t angle;
	int ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	ret = sj_get_tilt(scan,&angle);
	e_assert(ret>0,0);
	dangle[0] = angle.dX;dangle[1] = angle.dY;
	(*env)->SetDoubleArrayRegion(env,jda,0,2,dangle);
	return 1;
}

/*
 * Class:     com_hd_internal_Control
 * Method:    sj_get_battery
 * Signature: (J)D
 */
JNIEXPORT jdouble JNICALL Java_com_hd_internal_ScanWork_sj_1get_1battery
(JNIEnv *env, jclass clazz, jlong priv)
{
	jdouble ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	ret = sj_get_battery(scan);
	return ret;
}


/*
 * Class:     com_hd_internal_ScanWork
 * Method:    sj_led
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_hd_internal_ScanWork_sj_1led
(JNIEnv *env, jclass clazz, jlong priv,jint status)
{
	jint ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	ret = sj_led(scan,status);
	e_assert(ret>0, 0);
	return 1;
}

/*
 * Class:     com_hd_internal_Control
 * Method:    sj_search_zero
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_hd_internal_ScanWork_sj_1search_1zero
(JNIEnv *evn, jclass c, jlong priv)
{
	jint ret;
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	ret = sj_search_zero(scan);
	e_assert(ret>0, 0);
	return 1;
}

/*
 * Class:     com_hd_internal_Control
 * Method:    sj_get_info
 * Signature: (JILjava/lang/String;I)I
 */
JNIEXPORT jstring JNICALL Java_com_hd_internal_ScanWork_sj_1get_1info
(JNIEnv *env, jclass c, jlong priv, jint idx,jint len)
{
	jint ret;
	jstring info;
	char *buf = (char*)malloc(len);
	struct scan_job_t* scan = (struct scan_job_t*) priv;
	e_assert(scan!=NULL, 0);
	ret = sj_get_info(scan,idx,buf,len);
	e_assert(ret>0, 0);
	//TODO:GBK2UTF8
	info = (*env)->NewStringUTF(env, buf);
	free(buf);
	return info;
}
