/*
 * main.cpp
 *
 *  Created on: Jun 14, 2013
 *      Author: uplusplus
 */
#include "../hd_test_config.h"
#if TEST_EGL
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdio.h>
#include <strings.h>
#include <unistd.h>
#include <stdlib.h>

#define HEAD "ABCD%05d%05d%08.2fEFGH"
#define NAME "/data/data/com.hdsy.ls300/files/egl.sprite"

static int make_unix_domain_addr(const char* name, struct sockaddr_un* pAddr,
		socklen_t* pSockLen) {
	int nameLen = strlen(name);
	pAddr->sun_family = AF_LOCAL;
	if (nameLen >= (int) sizeof(pAddr->sun_path)) /* too long? */
		return -1;
	strcpy(pAddr->sun_path, name);
	*pSockLen = sizeof(struct sockaddr_un);
	return 0;
}

int color[] = { 0, 64, 128, 192, 255 };

void create_image(char* buf, int i, int w) {
	int j;
	for (j = 0; j < w * w; j += w) {
//		memset(buf + j, color[(j + i) % 4], w / 2);
//		memset(buf + j + w / 2, color[(j + i + 2) % 4], w / 2);
		memset(buf+j,color[i%4],w);
	}
}

void EGL_SEND(int size, int frames) {
	int sock, i, j, s;
	struct sockaddr_un server;
	socklen_t slen;
	char buf[100], *sbuf;

	printf("-> Start...\n");

	sock = socket(AF_UNIX, SOCK_STREAM, 0);
	if (sock < 0) {
		perror("opening stream socket");
		return;
	}

	make_unix_domain_addr(NAME, &server, &slen);

	if (connect(sock, (struct sockaddr *) &server, slen) < 0) {
		close(sock);
		perror("connecting stream socket");
		return;
	}

	printf("-> Connected...\n");

	sprintf(buf, HEAD, size, size,1.0);
	if (send(sock, buf, 26, 0) < 0)
		perror("writing on stream socket");

	printf("-> Send size done...\n");

	printf("-> Send frame\n");
	if (frames == -1)
		frames = 65535;

	sbuf = (char*) malloc(size * size);
	for (i = 0; i < frames; i++) {
		create_image(sbuf, i, size);
		s = send(sock, sbuf, size * size, 0);
		if (s < 0)
			break;
		sleep(1);
	}
	free(sbuf);

	close(sock);
}

int main(int argc, char **argv) {
	if (argc < 3) {
		printf("Usage: %s size frames\n", argv[0]);
		return -1;
	}
	EGL_SEND(atoi(argv[1]), atoi(argv[2]));
	return 0;
}

#endif
