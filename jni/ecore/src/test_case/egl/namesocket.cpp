/*
 * namesocket.cpp
 *
 *  Created on: Aug 8, 2013
 *      Author: uplusplus
 */

#if 1

#include "namesocket.h"
#include "base.h"

/*
 * Create a UNIX-domain socket address in the Linux "abstract namespace".
 *
 * The socket code doesn't require null termination on the filename, but
 * we do it anyway so string functions work.
 */
int makeAddr(const char* name, struct sockaddr_un* pAddr, socklen_t* pSockLen) {
	int nameLen = strlen(name);
	if (nameLen >= (int) sizeof(pAddr->sun_path)) /* too long? */
		return -1;
	strcpy(pAddr->sun_path, name);
	pAddr->sun_family = AF_LOCAL;
	*pSockLen = sizeof(struct sockaddr_un);
	return 0;
}

static void* thread_read(void *arg) {
	name_socket *reader = (name_socket*) arg;
	reader->open_socket();
	while (reader->state) {
		reader->read_data();
	}
}

static int count = 0;
int name_socket::open_socket() {
	char tmp[19] = { 0 }, *buf_last;

	if (clientSock != -1) {
		close(clientSock);
		clientSock = -1;
	}

	clientSock = accept(lfd, NULL, NULL);
	if (clientSock < 0) {
		perror("server accept");
		return 0;
	}

	return 1;
}

int name_socket::read_data() {
	unsigned int readed_row = 0, readed_in_row;
	char buf;

	while (readed_row < height) {
		for (readed_in_row = 0; readed_in_row < row_width; readed_in_row +=
				nread) {//可以多次读
			nread = read(clientSock, &buf, 1);
			if (nread < 0
					&& (errno == EINTR || errno == EWOULDBLOCK
							|| errno == EAGAIN)) {
				DMSG((STDOUT, "there is no data, try again!\n"));
				sleep(1);
				continue;
			} else if (nread <= 0) {//但如果发生网络严重错误，比如网络断开
				break;
			}
		}
		if (readed_in_row != row_width) {//就从新开始初始化
			DMSG((STDOUT, "name socket is closed! 0  \n"));
			open_socket();
			readed_row = 0;
			readed_in_row = 0;
			continue;
		}
		readed_row++;
	}
	return buf_size;
}

name_socket::name_socket(char *dev) {
	struct sockaddr_un sockAddr;
	socklen_t sockLen;
	int result = 1;

	state = 0;
	lfd = clientSock = -1;

	if (makeAddr(dev, &sockAddr, &sockLen) < 0)
		return;
	lfd = socket(AF_LOCAL, SOCK_STREAM, PF_UNIX);
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
	close(clientSock);
	close(lfd);
}

#endif
