FROM debian:stable

ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=GMT
RUN apt -y update \
 && apt -y dist-upgrade
RUN apt -y install software-properties-common build-essential net-tools
RUN apt -y install libpthread-stubs0-dev openjdk-11-jdk openjdk-11-jre maven
RUN apt -y update
RUN apt -y install vim lsof strace htop iftop curl tmux
RUN apt -y upgrade
RUN mkdir -p /opt/service
WORKDIR /opt/service/
EXPOSE 8080
ADD . /opt/service
RUN /opt/service/mvnw compile
RUN make test
