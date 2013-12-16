LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
	LOCAL_MODULE := libcyassl
#	LOCAL_SRC_FILES := 	$(call all-subdir-c-files)
	
	YASSL      := .
	
	LOCAL_SRC_FILES := \
	  $(YASSL)/src/internal.c $(YASSL)/src/io.c $(YASSL)/src/keys.c \
	  $(YASSL)/src/ssl.c $(YASSL)/src/tls.c $(YASSL)/ctaocrypt/src/hmac.c \
	  $(YASSL)/ctaocrypt/src/random.c $(YASSL)/ctaocrypt/src/sha.c \
	  $(YASSL)/ctaocrypt/src/sha256.c $(YASSL)/ctaocrypt/src/logging.c \
	  $(YASSL)/ctaocrypt/src/error.c $(YASSL)/ctaocrypt/src/rsa.c \
	  $(YASSL)/ctaocrypt/src/des3.c $(YASSL)/ctaocrypt/src/asn.c \
	  $(YASSL)/ctaocrypt/src/coding.c $(YASSL)/ctaocrypt/src/arc4.c \
	  $(YASSL)/ctaocrypt/src/md4.c $(YASSL)/ctaocrypt/src/md5.c \
	  $(YASSL)/ctaocrypt/src/dh.c $(YASSL)/ctaocrypt/src/dsa.c \
	  $(YASSL)/ctaocrypt/src/pwdbased.c $(YASSL)/ctaocrypt/src/aes.c \
	  $(YASSL)/ctaocrypt/src/md2.c $(YASSL)/ctaocrypt/src/ripemd.c \
	  $(YASSL)/ctaocrypt/src/sha512.c $(YASSL)/src/sniffer.c \
	  $(YASSL)/ctaocrypt/src/rabbit.c $(YASSL)/ctaocrypt/src/misc.c \
	  $(YASSL)/ctaocrypt/src/tfm.c $(YASSL)/ctaocrypt/src/integer.c \
	  $(YASSL)/ctaocrypt/src/ecc.c $(YASSL)/src/ocsp.c $(YASSL)/src/crl.c \
	  $(YASSL)/ctaocrypt/src/hc128.c $(YASSL)/ctaocrypt/src/memory.c
  
	LOCAL_C_INCLUDES := $(YASSL) $(YASSL)/cyassl 

	LOCAL_CFLAGS 	+=  -DANDROID_OS -D _LIB -D OPENSSL_EXTRA -D HAVE_ERRNO_H \
              -D HAVE_GETHOSTBYNAME -D HAVE_INET_NTOA -D HAVE_LIMITS_H \
              -D HAVE_MEMSET -D HAVE_SOCKET -D HAVE_STDDEF_H -D HAVE_STDLIB_H \
              -D HAVE_STRING_H -D HAVE_SYS_STAT_H -D HAVE_SYS_TYPES_H
include $(BUILD_STATIC_LIBRARY)

