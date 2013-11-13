/*
 * =====================================================================================
 *
 *       Filename:  fifo.cpp
 *
 *    Description:  
 *
 *        Version:  1.0
 *        Created:  2013年07月17日 09时07分10秒
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  Mei Kang (), meikang9527@163.com
 *        Company:  College of Information Engineering of CDUT
 *
 * =====================================================================================
 */

#include "fifo.h"
#include <errno.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "base.h"
#include "ems_android_egl.h"

static int count = 0;

static void* thread_read(void *arg) {
	fifo_read *reader = (fifo_read*) arg;
	reader->open_fifo();
	while (reader->state) {
		reader->read_fifo();
		reader->swap();
	}
}

fifo_read::fifo_read() {
	if ((mkfifo(EGL_NODE, 0777) < 0) && (errno != EEXIST)) {
		DMSG((STDOUT,"can not create FIFO:%d!\n",errno));
		exit(1);
	}

	DMSG((STDOUT,"create fifo!\n"));
	fd = open(EGL_NODE, O_RDONLY | O_NONBLOCK, 0);

	if (-1 == fd) {
		DMSG((STDOUT,"failed to open fifo!,error=%d\n", errno));
		exit(1);
	}
	DMSG((STDOUT,"open fifo!\n"));
	state = 1;

	if (pthread_create(&read_thread, NULL, thread_read, this) != 0) {
		perror("read thread creation failed");
		exit(1);
	}

	buf = 0;
	DMSG((STDOUT,"prepare to read fifo!\n"));
}

fifo_read::~fifo_read() {
	state = 0;
}


char* fifo_read::private_buf(){
	return buf + (width*height) ;
}

void fifo_read::swap(){
	memcpy(buf,private_buf(),buf_size);
}

int fifo_read::open_fifo() {
	char tmp[19] = { 0 }, *buf_last;
	retry:
	while (!(nread = read(fd, tmp, 18)))
		sleep(1);

	if (18 != nread && strncmp(tmp, "ABCD", 4)) {
		DMSG((STDOUT,"failed to receive!\n"));
		exit(1);
	}

	sscanf(tmp, "ABCD%05u%05uEFGH", &width, &height);
	DMSG((STDOUT,"open_fifo:width is %u, height is %u\n", width, height));
	if (!width || !height)
		goto retry;

	row_width = width;
	width  = (width + 3) & ~3;

	buf_last = buf;
	buf_swap = 0;
	buf = (char*) malloc(width * height *2);
	DMSG((STDOUT,"open_fifo:pixel address %p\n", (void*) buf));
	egl_SetImage((unsigned char*) buf, width, height);
	if (buf_last)
		free(buf_last);
	buf_size = width * height;
	count = 0;
	return 1;
}
static void printfv(char* msg, int len) {
	char c, l = '0';
	int count = 0;

	while (len--) {
		c = *msg++;
		if (c == l) {
			count++;
		} else {
			if (count > 0)
				DMSG((STDOUT," %c[%d]", l == 0 ? '0' : l, count));
			l = c;
			count = 1;
		}
	}
	if (count > 0)
		DMSG((STDOUT," %c[%d]", l == 0 ? '0' : l, count));
	DMSG((STDOUT,"\n"));
}

int fifo_read::read_fifo() {
	unsigned int readed = 0;
	char *cursor = private_buf();

	while (readed < height) {
		nread = read(fd, cursor, row_width);
		if (-1 == nread) {
			if (EAGAIN == errno) {
				DMSG((STDOUT,"there is no data, try again!\n"));
				sleep(1);
				continue;
			} else {
				DMSG((STDOUT,"fifo is closed!  -1  \n"));
				break;
			}
		} else if (0 == nread) {
			DMSG((STDOUT,"fifo is closed! 0  \n"));
			open_fifo();
			readed = 0;
			cursor = private_buf();
			continue;
		} else if (nread != row_width) {
			while (nread < row_width) {
				nread += read(fd, cursor + nread, row_width - nread);
			}
		}
		cursor += width;
		readed++;
		//DMSG((STDOUT,"."));
	}
	DMSG((STDOUT,"%d", count++));
	return 1;
}

