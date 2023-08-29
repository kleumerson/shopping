FROM ubuntu:latest
LABEL authors="kleum"

ENTRYPOINT ["top", "-b"]