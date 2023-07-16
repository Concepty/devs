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
    int server_socket = 0;
    int exit_code = 0, ret = 0;
    int enable = 1;
    int c = sizeof(struct sockaddr_in);

    server_socket = socket(AF_INET , SOCK_STREAM , 0);
    if (server_socket < 0) { perror("socket"); exit_code = errno; goto cleanup; }

    setsockopt(server_socket, SOL_SOCKET, SO_REUSEPORT, &enable, sizeof(int));

    {
        struct sockaddr_in sa;
        sa.sin_family = AF_INET;
        sa.sin_addr.s_addr = INADDR_ANY;
        sa.sin_port = htons(8000);

        if (bind(server_socket, (struct sockaddr *)&sa, sizeof(sa)) < 0)
        {
            perror("bind");
            exit_code = errno;
            goto cleanup;
        }
    }

    listen(server_socket, 5);

    puts("accepting for incoming connections...");

    while (1)
    {
        struct sockaddr_in ca = {0};
        int conn = accept(server_socket, (struct sockaddr *)&ca, (socklen_t*)&c);
        if (conn == -1) { perror("accept"); exit_code = errno; goto cleanup; }

        ret = send(conn, "hello world!", strlen("hello world!"), 0);
        if (ret == -1) { perror("send"); exit_code = errno; goto cleanup; }

        sleep(1); // To avoid close before client sleep
        close(conn);
    }

cleanup:
    if (server_socket > 0) close(server_socket);

    printf("properly cleaned\n");
    return exit_code;
}