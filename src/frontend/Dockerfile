FROM node:18-alpine

WORKDIR /usr/src/app

COPY package*.json ./
RUN npm install -g npm@10.1.0
RUN npm install -g @angular/cli
COPY . .
