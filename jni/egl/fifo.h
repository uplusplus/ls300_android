/*
 * =====================================================================================
 *
 *       Filename:  fifo.h
 *
 *    Description:  
 *
 *        Version:  1.0
 *        Created:  2013年07月17日 09时01分18秒
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  Mei Kang (), meikang9527@163.com
 *        Company:  College of Information Engineering of CDUT
 *
 * =====================================================================================
 */
#include <pthread.h>



class fifo_read {

public:
	fifo_read();
	~fifo_read();
    int open_fifo();
	int read_fifo();
	void swap();
	char* private_buf();
		
public:
	int width,row_width,height;
	unsigned int buf_size;
	pthread_t read_thread;
	int fd;
	int readed;
	int nread;
	int state;
	int buf_swap;
 	char *buf;
};
