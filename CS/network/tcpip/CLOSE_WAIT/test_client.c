#include<unistd.h>
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<errno.h>
#include<arpa/inet.h>

#define SA_SIZE sizeof(struct sockaddr_in)
#define BUF_SIZE 1024

int main(int argc, char *argv[])
{
    int s = 0;
    int exit_code = 0;
    int ret = 0;

    s = socket(AF_INET , SOCK_STREAM , 0);
    if (s < 0) { perror("socket"); exit_code = errno; goto cleanup; }

    {
        struct sockaddr_in ca;
        ca.sin_family = AF_INET;
        ca.sin_addr.s_addr = INADDR_ANY;
        ca.sin_port = htons(8000);

        ret = connect(s, (struct sockaddr *)&ca, SA_SIZE);
        if (ret == -1) { perror("connect"); exit_code = errno; goto cleanup; }
    }

    {
        char buf[BUF_SIZE];
        int ret_size = -1;

        ret_size = recv(s, buf, BUF_SIZE, 0);
        if (ret_size == -1) { perror("recv"); exit_code = errno; goto cleanup; }
        if (ret_size >= BUF_SIZE) ret_size = BUF_SIZE - 1;
        buf[ret_size] = '\0';

        printf("got %s\n", buf);
    }

    // Don't close socket when received FIN from server
    // check socket in CLOSE_WAIT state with
    // $ netstat -p tcp -n -a (MacOS)
    sleep(100000);

cleanup:
    if (s > 0) close(s);

    printf("properly cleaned\n");
    return exit_code;
}