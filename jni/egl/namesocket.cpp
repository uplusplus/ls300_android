/*
 * namesocket.cpp
 *
 *  Created on: Aug 8, 2013
 *      Author: uplusplus
 */

#include "namesocket.h"
#include "base.h"
#include <stdlib.h>

/*
 * Create a UNIX-domain socket address in the Linux "abstract namespace".
 *
 * The socket code doesn't require null termination on the filename, but
 * we do it anyway so string functions work.
 */
int makeAddr(const char* name, struct sockaddr_un* pAddr, socklen_t* pSockLen) {
	if (strlen(name) >= (int) sizeof(pAddr->sun_path)) /* too long? */
		return -1;
	pAddr->sun_family = AF_LOCAL;
	strcpy(pAddr->sun_path, name);
	*pSockLen = sizeof(struct sockaddr_un);
	return 0;
}

static void* thread_read(void *arg) {
	name_socket *reader = (name_socket*) arg;
	reader->open_socket();
	while (reader->state) {
		reader->read_data();
		reader->update();
	}
}

static int count = 0;
int name_socket::open_socket() {
	char tmp[100] = { 0 }, *buf_last;
	float h_w;

	if (clientSock != -1) {
		close(clientSock);
		clientSock = -1;
	}

	clientSock = accept(lfd, NULL, NULL);
	if (clientSock < 0) {
		perror("server accept");
		return 0;
	}

	retry: while (!(nread = read(clientSock, tmp, 26)))
		sleep(1);

	if (26 != nread && strncmp(tmp, "ABCD", 4)) {
		DMSG((STDOUT, "failed to receive!\n"));
		return 0;
	}

	sscanf(tmp, "ABCD%05u%05u%08fEFGH", &width, &height, &h_w);
	DMSG(
			(STDOUT, "open_name_socket:width is %u, height is %u\n", width, height));
	if (!width || !height)
		goto retry;

	buf_last = buf;
	buf = (char*) malloc(width * height);
	DMSG((STDOUT, "open_name_socket:pixel address %p\n", (void*) buf));
	egl_video_set_src((uint8_t*) buf, width, height, h_w);
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
				DMSG((STDOUT, " %c[%d]", l == 0 ? '0' : l, count));
			l = c;
			count = 1;
		}
	}
	if (count > 0)
		DMSG((STDOUT, " %c[%d]", l == 0 ? '0' : l, count));
	DMSG((STDOUT, "\n"));
}

int name_socket::read_data() {
	unsigned int readed_row = 0,readed_in_row;
	char *cursor = buf;

	while (readed_row < height) {
		for(readed_in_row=0; readed_in_row < width; ){
			nread = read(clientSock, cursor, width-readed_in_row);
			if (nread < 0 && (errno == EINTR || errno == EWOULDBLOCK || errno == EAGAIN)) {
				DMSG((STDOUT, "there is no data, try again!\n"));
				usleep(200000);
				continue;
			} else if (nread <= 0) { //但如果发生网络严重错误，比如网络断开
				break;
			}
			readed_in_row += nread;
			cursor += nread;
		} //
		if (readed_in_row != width) { //就从新开始初始化
			DMSG((STDOUT, "name socket is closed! 0  \n"));
			open_socket();
			readed_row = 0;
			readed_in_row = 0;
			cursor = buf;
			continue;
		}

		readed_row++;
	}
	return buf_size;
}

name_socket::name_socket()
{
	struct sockaddr_un sockAddr;
	socklen_t sockLen;
	int result = 1;

	state = 0;
	lfd = clientSock = -1;

	if (makeAddr(EGL_NODE, &sockAddr, &sockLen) < 0)
		return;
	lfd = socket(AF_LOCAL, SOCK_STREAM, 0);
	if (lfd < 0) {
		perror("client socket()");
		return;
	}

	remove(EGL_NODE);

	if (bind(lfd, (const struct sockaddr*) &sockAddr, sockLen) < 0) {
		perror("server bind()");
		return;
	}
	if (listen(lfd, 5) < 0) {
		perror("server listen()");
		return;
	}
	state = 1;

	if (pthread_create(&read_thread, NULL, thread_read, this) != 0) {
		perror("read thread creation failed");
		return;
	}

	buf = 0;
	DMSG((STDOUT,"prepare to read name_socket : %s\n",EGL_NODE));
}

name_socket::name_socket(char *dev) {
	struct sockaddr_un sockAddr;
	socklen_t sockLen;
	int result = 1;

	state = 0;
	lfd = clientSock = -1;

	if (makeAddr(dev, &sockAddr, &sockLen) < 0)
		return;
	lfd = socket(AF_LOCAL, SOCK_STREAM, 0);
	if (lfd < 0) {
		perror("client socket()");
		return;
	}

	remove(dev);

	if (bind(lfd, (const struct sockaddr*) &sockAddr, sockLen) < 0) {
		perror("server bind()");
		return;
	}
	if (listen(lfd, 5) < 0) {
		perror("server listen()");
		return;
	}
	state = 1;

	if (pthread_create(&read_thread, NULL, thread_read, this) != 0) {
		perror("read thread creation failed");
		return;
	}

	buf = 0;
	DMSG((STDOUT,"prepare to read name_socket : %s\n",dev));
}

name_socket::~name_socket() {

}

void name_socket::update() {
	egl_video_upate_screen();
}

