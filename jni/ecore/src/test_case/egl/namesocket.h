/*
 * namesocket.h
 *
 *  Created on: Aug 8, 2013
 *      Author: uplusplus
 */

#ifndef NAMESOCKET_H_
#define NAMESOCKET_H_
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stddef.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <pthread.h>
#include <errno.h>


class name_socket {
public:
	int open_socket();
	int read_data();

	name_socket();
	name_socket(char *dev);
	virtual ~name_socket();

	int state;
private:
	int lfd,clientSock;

	int width,row_width,height;
	unsigned int buf_size;
	pthread_t read_thread;
	int readed;
	int nread;

	int buf_swap;
 	char *buf;
};

#endif /* NAMESOCKET_H_ */
