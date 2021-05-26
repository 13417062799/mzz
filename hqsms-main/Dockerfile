#### 因为涉及交互式操作，以下注释部分已提交到zomco/ubuntu:hqsms镜像
#FROM ubuntu
#MAINTAINER zomco
#RUN apt-get update && apt-get install -y --fix-missing\
#    maven \
#    iputils-ping \
#    net-tools \
#    openjdk-8-jdk \
#    ffmpeg \
#    build-essential \
#    cmake \
#    gfortran \
#    git \
#    wget \
#    curl \
#    graphicsmagick \
#    libgraphicsmagick1-dev \
#    libatlas-base-dev \
#    libavcodec-dev \
#    libavformat-dev \
#    libgtk2.0-dev \
#    libjpeg-dev \
#    liblapack-dev \
#    libswscale-dev \
#    pkg-config \
#    python3-dev \
#    python3-numpy \
#    python3-setuptools \
#    software-properties-common \
#    zip \
#    && apt-get clean && rm -rf /tmp/* /var/tmp/* && \
#    cd ~ && \
#    mkdir -p dlib && \
#    git clone -b 'v19.9' --single-branch https://github.com/davisking/dlib.git dlib/ && \
#    cd  dlib/ && \
#    python3 setup.py install --yes USE_AVX_INSTRUCTIONS && \
#    curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py && \
#    python3 get-pip.py && \
#    pip3 install face_recognition && \
#    pip3 install scipy && \
#    pip3 install sqlalchemy && \
#    cd ~ && \
#    mkdir -p hqsms && \
#    cd hqsms


#### 分阶段打包
FROM zomco/ubuntu:hqsms as base
WORKDIR /home

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY libs libs
RUN ./mvnw install -DskipTests -f libs/leaf-starter/pom.xml
#RUN ./mvnw install:install-file -Dfile="./libs/leaf-core.jar" -DgroupId="com.sankuai.inf.leaf" -DartifactId="leaf-core" -Dversion="1.0.1-RELEASE" -Dpackaging="jar"
#RUN ./mvnw install:install-file -Dfile="./libs/leaf-server.jar" -DgroupId="com.sankuai.inf.leaf" -DartifactId="leaf-server" -Dversion="1.0.1-RELEASE" -Dpackaging="jar"
#RUN ./mvnw install:install-file -Dfile="./libs/leaf-boot-starter.jar" -DgroupId="com.sankuai.inf.leaf" -DartifactId="leaf-boot-starter" -Dversion="1.0.1-RELEASE" -Dpackaging="jar"
RUN ./mvnw install:install-file -Dfile="./libs/jna.jar" -DgroupId="com.sun.jna" -DartifactId="hikvision-jna" -Dversion="0.0.1" -Dpackaging="jar"
RUN ./mvnw install:install-file -Dfile="./libs/examples.jar" -DgroupId="com.sun.jna.examples" -DartifactId="hikvision-jna-examples" -Dversion="0.0.1" -Dpackaging="jar"
RUN ./mvnw dependency:go-offline
COPY src ./src
COPY sdk sdk
COPY conf/wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

FROM base as test
RUN ["./mvnw", "test"]

#FROM base as development
#RUN ["./mvnw", "spring-boot:repackage"]

FROM base as build
RUN ["./mvnw", "package"]

FROM zomco/ubuntu:hqsms as production
WORKDIR /home
VOLUME ["/home/tmp", "/home/file", "/home/log"]
COPY --from=build /home/target/*.jar app.jar
COPY sdk sdk
COPY conf/wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh